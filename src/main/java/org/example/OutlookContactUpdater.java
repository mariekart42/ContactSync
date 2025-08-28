package org.example;

import com.microsoft.graph.models.Contact;
import com.microsoft.graph.models.ContactFolder;
import com.microsoft.graph.models.ContactFolderCollectionResponse;
import com.microsoft.graph.users.item.UserItemRequestBuilder;
import com.microsoft.graph.users.item.contacts.ContactsRequestBuilder;
import org.example.AditoRequests.CONTACT_STATUS;

import java.util.List;
import java.util.Map;
import java.util.Objects;

//import static org.example.AditoRequests.CONTACT_STATUS.TO_CREATE;
//import static org.example.AditoRequests.CONTACT_STATUS.TO_DELETE;


public class OutlookContactUpdater {

    // TODO:
    //  - catch syncresult, store it in `SYNCABONNEMENT.syncresult`
    //  - update `SYNCABONNEMENT.synced`, `SYNCABONNEMENT.changed`, `SYNCABONNEMENT.abostart` and `SYNCABONNEMENT.aboende`
    //  - create folder and put contact inside with information of 'device' -> later: move contact to new folder if it changed
    public static void updateContact(Contact contact, Map<String, String> contactMetaData, String user, CONTACT_STATUS status)
    {
        // TODO ask about 'user'. should i retrieve user from device? if yes, user here is redundant
        String luid = contactMetaData.get("luid");
        String device = contactMetaData.get("device");

        if (contact == null)
            return;

        AppConfig config = new AppConfig();
        try
        {
            var graphClient = config.getGraphServiceClient()
                    .users()
                    .byUserId(user);

            boolean categoryUpdate = addAditoCategoryToContact(graphClient.contacts(), contact, luid, status);

            String deepestId = ensureFolderExists(graphClient, null, extractFolderTree(device));

            // question: van i update any user without knowing his folder or do i need to figure out where he is in order to change him
            // answer: no i do not need the folder id

            switch (status)
            {
                case TO_CHANGE -> {
                    graphClient.contacts().byContactId(luid).patch(contact);
                    System.out.println("\033[0;33mCONTACT:\n\tID: " + luid + "\n\tNAME: " + contact.getDisplayName() + "\nhas status TO_CHANGE -> PATCH to Outlook\033[0m");
                }
                case TO_CREATE -> {
                    Contact createContact = createContactInFolder(graphClient, contact, deepestId);
                    System.out.println("\033[0;32mCONTACT:\n\tID: " + createContact.getId() + "\n\tNAME: " + createContact.getDisplayName() + "\nhas status TO_CREATE -> POST to Outlook\033[0m");
                }
                case TO_DELETE -> {
                    graphClient.contacts().byContactId(luid).delete();
                    System.out.println("\033[0;31mCONTACT:\n\tID: " + luid + "\n\tNAME: " + contact.getDisplayName() + "\nhas status TO_DELETE -> DELETE to Outlook\033[0m");
                }
                case UNCHANGED -> {
                    if (categoryUpdate)
                        graphClient.contacts().byContactId(luid).patch(contact);
                    System.out.println("\033[0;36mCONTACT:\n\tID: " + luid + "\n\tNAME: " + contact.getDisplayName() + "\nhas status UNCHANGED -> nothing to Outlook\033[0m");
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


    // creates provided contact in the specified folderId
    // if folderId is null or empty, contact will be created in the default Outlook contact folder
    private static Contact createContactInFolder(UserItemRequestBuilder graphClient, Contact contact, String folderId) {
        if (folderId == null || folderId.isEmpty())// happens if device has no folder specified, eg device: mensing@kieback-peter.de/
            return graphClient.contacts().post(contact);
        else
            return graphClient.contactFolders().byContactFolderId(folderId).contacts().post(contact);
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
        if (device == null || device.isEmpty() || device.equalsIgnoreCase("/"))
            return parentFolderId;

        String folderName;
        device = trimChar(device, '/');
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
        // TODO maybe catch this error message and put it in the syncupdate
        ContactFolderCollectionResponse folderCollection;

        if (parentFolderId == null || parentFolderId.isEmpty())
            folderCollection = graphClient.contactFolders().get();
        else
            folderCollection = graphClient.contactFolders().byContactFolderId(parentFolderId).childFolders().get();

        if (folderCollection == null)
            throw new Exception("Unable to retrieve contactFolders from MSGraph.");
        for (var folder : Objects.requireNonNull(folderCollection.getValue())) // no null/empty check needed, see comment above
        {
            if (Objects.requireNonNull(folder.getDisplayName()).equalsIgnoreCase(folderName))
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
