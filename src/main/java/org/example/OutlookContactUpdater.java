package org.example;

import com.microsoft.graph.models.Contact;
import com.microsoft.graph.models.ContactFolder;
import com.microsoft.graph.models.ContactFolderCollectionResponse;
import com.microsoft.graph.users.item.UserItemRequestBuilder;
import com.microsoft.graph.users.item.contacts.ContactsRequestBuilder;
import com.microsoft.graph.users.item.contacts.item.ContactItemRequestBuilder;
import org.example.AditoRequests.CONTACT_STATUS;
import org.example.ContactData.ParseContact;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class OutlookContactUpdater {

    // TODO:
    //  - catch syncresult, store it in `SYNCABONNEMENT.syncresult`
    //  - update `SYNCABONNEMENT.synced`, `SYNCABONNEMENT.changed`, `SYNCABONNEMENT.abostart` and `SYNCABONNEMENT.aboende`
    //  - create folder and put contact inside with information of 'device' -> later: move contact to new folder if it changed
    public static void updateContact(Map<String, String> contactMetaData, CONTACT_STATUS status)
    {
        String luid = contactMetaData.get("luid");
        String device = contactMetaData.get("device");
        String deviceSpecifics = contactMetaData.get("devicespecifics");
        String userId = extractUserId(device); // user later with real data
        // rn always use mine so we're not messing up other peoples contacts lol
        userId = App.USER;


        AppConfig config = new AppConfig();
        try
        {
            var graphClient = config.getGraphServiceClient()
                    .users()
                    .byUserId(userId);

            String deepestId = ensureFolderExists(graphClient, null, extractFolderTree(device));


            Contact contact;
            ParseContact parse = new ParseContact();
            if (status == CONTACT_STATUS.TO_CHANGE && !isContactInFolder(graphClient, deepestId, luid))
            {
                contact = parse.getContact(AditoRequests.getResultFromMockSQLFunction(contactMetaData), contactMetaData, false);
            }
            else
            {
                contact = parse.getContact(AditoRequests.getResultFromMockSQLFunction(contactMetaData), contactMetaData, true);
            }


            if (contact == null)
                return;

            boolean categoryUpdate = addAditoCategoryToContact(graphClient.contacts(), contact, luid, status);

            Contact updatedContact = null; // TODO delete later, just for info/debug
            switch (status)
            {
                case TO_CHANGE -> {
                    // TODO move contact to new folder if device changed
                    //  if contacts device folder tree changed and we want to move him
                    //  new folder got created already
                    //  now we have to check if contact with id is already in that folder
// if deepest id equal to contacts parentfolder id, if deepest id is null or empty, contact is in the default outlook folder
                    if (!isContactInFolder(graphClient, deepestId, luid)) // more performant would be to somehow actually know if device changed, but dunno how
                    {
                    //  if no  -> grr
                    //            get all data from outlook contact
                    //            update all new data from contact on top (that way all old data is also there)
                    //            create this contact in the devive folder
                    //       BUT  this would mean he gets a new luid -> needs to get updated in adito
                    //  do when real adito data is available
//                        ParseContact parse = new ParseContact();
                        //contact = parse.mergeContacts(graphClient.contacts().byContactId(luid).get(), contact, deviceSpecifics);
                        updatedContact = postContact(graphClient, contact, deepestId);
                        deleteContact(graphClient, luid);
                        System.out.println("\033[0;32mMOVED CONTACT:\nCONTACT:\n\tID: " + updatedContact.getId() + "\n\tNAME: " + updatedContact.getDisplayName() + "\nhas status TO_CREATE -> POST to Outlook\033[0m");
                        // TODO test this and later put new luid into adito db
                    }
                    else
                    { //  if yes -> great, do nothing
                        updatedContact = patchContact(graphClient, contact, luid);
                        System.out.println("\033[0;33mCONTACT:\n\tID: " + updatedContact.getId() + "\n\tNAME: " + updatedContact.getDisplayName() + "\nhas status TO_CHANGE -> PATCH to Outlook\033[0m");
                    }
                }
                case TO_CREATE -> {
                    updatedContact = postContact(graphClient, contact, deepestId);
                    System.out.println("\033[0;32mCONTACT:\n\tID: " + updatedContact.getId() + "\n\tNAME: " + updatedContact.getDisplayName() + "\nhas status TO_CREATE -> POST to Outlook\033[0m");
                }
                case TO_DELETE -> {
                    deleteContact(graphClient, luid);
                    System.out.println("\033[0;31mCONTACT:\n\tID: " + luid + "\n\tNAME: " + contact.getDisplayName() + "\nhas status TO_DELETE -> DELETE to Outlook\033[0m");
                }
                case UNCHANGED -> {
                    if (categoryUpdate)
                        updatedContact = patchContact(graphClient, contact, luid);
                    System.out.println("\033[0;36mCONTACT:\n\tID: " + updatedContact.getId() + "\n\tNAME: " + updatedContact.getDisplayName() + "\nhas status UNCHANGED -> nothing to Outlook\033[0m");
                }
                default -> throw new Exception("CONTACT_STATUS has unexpected value: " + status);
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred while updating a contact in Outlook: " + e);
            e.printStackTrace();
        }
    }


    // Checks whether the specified contactId exists in the given contactFolderId
    // If contactFolderId is null or empty, the check is performed in the default Outlook contact folder
    private static boolean isContactInFolder(UserItemRequestBuilder graphClient, String contactFolderId, String contactId) throws Exception {

        if (contactFolderId == null || contactFolderId.isEmpty())
        { // contact is perhaps inside the default outlook folder
            var contactsInDefaultFolder = graphClient.contacts().get();
            if (contactsInDefaultFolder == null)
                throw new Exception("Unable to retrieve Contact Data from MSGraph.");

            if (contactsInDefaultFolder.getValue() == null || contactsInDefaultFolder.getValue().isEmpty()) // means there are no contacts in the default folder -> contact is obviously not there
                return false;

            for (var contact : contactsInDefaultFolder.getValue())
            {
                if (contact.getId() != null && contact.getId().equalsIgnoreCase(contactId))
                    return true;
            }
            return false;
        }

        Contact contactData = graphClient.contacts().byContactId(contactId).get();
        if (contactData == null)
            throw new Exception("Unable to retrieve Contact Data from MSGraph.");
        else if (contactData.getParentFolderId() == null || contactData.getParentFolderId().isEmpty())
            return false;
        return contactData.getParentFolderId().equalsIgnoreCase(contactFolderId);
    }











    public static String extractUserId(String device) {
        if (device == null || device.isEmpty())
            return null;

        int slashIndex = device.indexOf('/');
        if (slashIndex == -1)
            return device;
        else
            return device.substring(0, slashIndex);
    }


    // creates provided contact in the specified folderId
    // if folderId is null or empty, contact will be created in the default Outlook contact folder
    private static Contact postContact(UserItemRequestBuilder graphClient, Contact contact, String folderId) {
        if (folderId == null || folderId.isEmpty())// happens if device has no folder specified, eg device: mensing@kieback-peter.de/
            return graphClient.contacts().post(contact);
        else
            return graphClient.contactFolders().byContactFolderId(folderId).contacts().post(contact);
    }


    private static void deleteContact(UserItemRequestBuilder graphClient, String luid) {
        graphClient.contacts().byContactId(luid).delete();
    }


    private static Contact patchContact(UserItemRequestBuilder graphClient, Contact contact, String luid) {
        return graphClient.contacts().byContactId(luid).patch(contact);
    }


    // Removes the user prefix from the device string and returns only the folder path
    private static String extractFolderTree(String device)
    {
        if (device == null || device.isEmpty())
            return null;

        int slashIndex = device.indexOf('/');
        if (slashIndex == -1)
            return null; // no folders present

        return device.substring(slashIndex + 1);
    }


    // function recursively traverses through the folder tree based on 'device'
    // Creates missing folders if they do not exist
    // Handles cases where all, some, or none of the folders already exist
    private static String ensureFolderExists(UserItemRequestBuilder graphClient, String parentFolderId, String device) throws Exception {

        device = trimChar(device, '/');

        if (device == null || device.isEmpty())
            return parentFolderId;

        String folderName;
        int nextSlash = device.indexOf('/');
        if (nextSlash == -1)
        {
            if (!device.isEmpty())
            {
                folderName = device;
                device = "";
            }
            else
                return parentFolderId;
        }
        else
        {
            folderName = device.substring(0, nextSlash);
            device = device.substring(nextSlash + 1);
        }

        String folderId = findFolderId(graphClient, parentFolderId, folderName);
        if (folderId == null)
            return createFolders(graphClient, parentFolderId, folderName + "/" + device);// no folder with specified name found, so we create it
        else
            return ensureFolderExists(graphClient, folderId, device);
    }


    private static String findFolderId(UserItemRequestBuilder graphClient, String parentFolderId, String folderName) throws Exception {
        // if parentFolderId is null or invalid/does not exist, function will throw error, caught by updateContact catch-block
        // TODO catch this error message and put it in the syncupdate
        ContactFolderCollectionResponse folderCollection;

        if (parentFolderId == null || parentFolderId.isEmpty())
            folderCollection = graphClient.contactFolders().get();
        else
            folderCollection = graphClient.contactFolders().byContactFolderId(parentFolderId).childFolders().get();

        if (folderCollection == null || folderCollection.getValue() == null)
            throw new Exception("Unable to retrieve contactFolders from MSGraph.");
        for (var folder : folderCollection.getValue()) // no null/empty check needed, see comment above
        {
            if (folder.getDisplayName() != null && folder.getDisplayName().equalsIgnoreCase(folderName))
                return folder.getId();
        }
        return null;
    }


    protected static String createFolders(UserItemRequestBuilder graphClient, String parentFolderId, String device) throws Exception {
        if (device == null || device.isEmpty() || device.equalsIgnoreCase("/"))
            return parentFolderId;

        device = trimChar(device, '/');
        String[] folderArray = device.split("/");
        if (folderArray.length < 1)
            return parentFolderId;

        for (String folder : folderArray)
        {
            if (folder.isEmpty())
                continue;

            ContactFolder newFolder = new ContactFolder();
            newFolder.setDisplayName(folder);
            ContactFolder newFolderResponse;
            if (parentFolderId == null || parentFolderId.isEmpty())
            {
                try {
                    newFolderResponse = graphClient.contactFolders().post(newFolder);
                } catch (Exception e) {
                    throw new Exception("Failed to create new Folder: " + e); // TODO catch this error message with stack trace later and put in syncresult
                }
            }
            else
                newFolderResponse = graphClient.contactFolders().byContactFolderId(parentFolderId).childFolders().post(newFolder);

            if (newFolderResponse == null)
                throw new Exception("Failed to retrieve Folder Collection from MSGraph."); // TODO catch this error message with stack trace later and put in syncresult
            parentFolderId = newFolderResponse.getId();
        }
        return parentFolderId;
    }


    protected static void deleteFolder(UserItemRequestBuilder graphClient, String folderId)
    {
        graphClient.contactFolders().byContactFolderId(folderId).delete();
    }


    private static String trimChar(String str, Character toRemove)
    {
        if (str == null || str.isEmpty())
            return null;

        while (!str.isEmpty() && str.charAt(0) == toRemove) {
            str = str.substring(1);
        }

        while (!str.isEmpty() && str.charAt(str.length() - 1) == toRemove) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }


    private static boolean addAditoCategoryToContact(ContactsRequestBuilder contacts, Contact contact, String contactID, CONTACT_STATUS status)
    {
        if (status == CONTACT_STATUS.TO_DELETE)
            return false;
        if (status == CONTACT_STATUS.TO_CREATE)
        {
            contact.setCategories(List.of("AditoKontakt"));
            return true;
        }

        Contact contactData;
        try
        {
            contactData = contacts.byContactId(contactID).get();
        }
        catch (Exception e)
        {
            System.out.println("No contact found in Outlook for LUID: " + contactID);
            return false;
        }

        if (contactData != null)
        {
            List<String> categories = contactData.getCategories();
            assert categories != null;
            if (!categories.contains("AditoKontakt"))
            {
                categories.add("AditoKontakt");
                contact.setCategories(categories);
                return true;
            }
        }
        return false;
    }
}
