package org.example;

import com.microsoft.graph.models.Contact;
import com.microsoft.graph.models.ContactFolder;
import com.microsoft.graph.models.ContactFolderCollectionResponse;
import com.microsoft.graph.models.odataerrors.ODataError;
import com.microsoft.graph.users.item.UserItemRequestBuilder;
import com.microsoft.graph.users.item.contacts.ContactsRequestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.example.AditoRequests.CONTACT_STATUS;
import org.example.ContactData.ParseContact;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Slf4j
public class OutlookContactUpdater {

    // TODO:
    //  - update `SYNCABONNEMENT.synced`, `SYNCABONNEMENT.changed`, `SYNCABONNEMENT.abostart` and `SYNCABONNEMENT.aboende`
    public static void updateContact(Map<String, String> contactMetaData)
    {
        String luid = contactMetaData.get(App.LUID);
        String syncabonnementid = contactMetaData.get(App.SYNCABONNEMENTID);
        String device = contactMetaData.get(App.DEVICE);
        String userId = extractUserId(device); // user later with real data
        // rn always use mine so we're not messing up other peoples contacts lol
        userId = App.USER;


        AppConfig config = new AppConfig();
        try
        {
            var graphClient = config.getGraphServiceClient()
                    .users()
                    .byUserId(userId);

            String contactFolderId = ensureFolderExists(graphClient, null, extractFolderTree(device));

            Contact contact;
            ParseContact parse = new ParseContact();

            CONTACT_STATUS status = AditoRequests.getContactStatus(contactMetaData);

            boolean contactFolderChanged = hasContactFolderChanged(graphClient, status, contactFolderId, luid);
            if (contactFolderChanged)
                contact = parse.getContact(AditoRequests.getResultFromMockSQLFunction(contactMetaData), contactMetaData, false);
            else
                contact = parse.getContact(AditoRequests.getResultFromMockSQLFunction(contactMetaData), contactMetaData, true);

            if (contact == null)
                return;

            boolean categoryUpdate = addAditoCategoryToContact(graphClient.contacts(), contact, luid, status);

            if (categoryUpdate && status == CONTACT_STATUS.UNCHANGED)
                status = CONTACT_STATUS.TO_CHANGE;
            if (contactFolderChanged && status == CONTACT_STATUS.TO_CHANGE)
                status = CONTACT_STATUS.TO_MOVE;

            updateContactToGraph(status, graphClient, contactMetaData, contact, contactFolderId);

            DBConnector.updateSyncabonnementEntry(contactMetaData, App.SYNCRESULT, syncabonnementid, "ok");
        }
        catch (Exception e)
        {
            if (e.getMessage().contains("The specified object was not found in the store."))
                log.error("\033[0;31mcom.microsoft.graph.models.odataerrors.ODataError: The specified object was not found in the store. LUID: {}\033[0m", contactMetaData.get(App.LUID));
            else
                log.error("\033[0;31mAn error occurred while updating a contact in Outlook: \033[0m", e);
            try
            {
                DBConnector.updateSyncabonnementEntry(contactMetaData, App.SYNCRESULT, syncabonnementid, String.valueOf(e));
                DBConnector.updateSyncprincipalEntry(contactMetaData, App.SYNCRESULT, contactMetaData.get(App.SYNCPRINCIPALID), String.valueOf(e));
            }
            catch (SQLException se)
            {
                log.error("\033[0;31mAn SQLException occurred while updating a contact in Database: \033[0m", se);
            }
            catch (Exception ex)
            {
                log.error("\033[0;31mAn error occurred while updating a contact in Database, check if Database is up and running: \033[0m", ex);
            }
        }
    }

    private static void updateContactToGraph(CONTACT_STATUS status, UserItemRequestBuilder graphClient, Map<String, String> contactMetaData, Contact contact, String folderId) throws SQLException
    {
        String luid = contactMetaData.get(App.LUID);
        Contact updatedContact;
        switch (status)
        {
            case TO_CHANGE ->
            {
                updatedContact = patchContact(graphClient, contactMetaData, contact, luid);
                log.info("\033[0;33mContact (id:{}) updated in Outlook\033[0m", updatedContact.getId());
            }
            case TO_MOVE ->
            {
                updatedContact = moveContact(graphClient, contactMetaData, contact, folderId);
                log.info("\033[0;33mContact (old id:{}|new id:{}) got recreated in new Folder (id:{})\033[0m", luid, updatedContact.getId(), updatedContact.getParentFolderId());
            }
            case TO_CREATE ->
            {
                updatedContact = postContact(graphClient, contactMetaData, contact, folderId);
                log.info("\033[0;33mContact (new id:{}) created in Outlook\033[0m", updatedContact.getId());
            }
            case TO_DELETE ->
            {
                deleteContact(graphClient, contactMetaData, luid);
                log.info("\033[0;33mContact (old id:{}) deleted in Outlook\\033[0m", luid);
            }
            case UNCHANGED -> log.info("\033[0;33mContact (id:{}) no changes\033[0m", luid);
        }
    }


    private static boolean hasContactFolderChanged(UserItemRequestBuilder graphClient, CONTACT_STATUS status, String folderId, String luid) {
        if (status != CONTACT_STATUS.TO_CHANGE) // keep if statements separated to reduce calls to isContactInFolder() (overhead)
            return false;
        return !isContactInFolder(graphClient, folderId, luid);
    }


    // Checks whether the specified contactId exists in the given contactFolderId
    // If contactFolderId is null or empty, the check is performed in the default Outlook contact folder
    private static boolean isContactInFolder(UserItemRequestBuilder graphClient, String contactFolderId, String contactId) throws ODataError {

        if (contactFolderId == null || contactFolderId.isEmpty())
        { // contact is perhaps inside the default outlook folder
            var contactsInDefaultFolder = graphClient.contacts().get();
            if (contactsInDefaultFolder == null)
                throw new IllegalStateException("Unable to retrieve Contact Data from MSGraph.");

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
            throw new IllegalStateException("Unable to retrieve Contact Data from MSGraph.");
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
    private static Contact postContact(UserItemRequestBuilder graphClient, Map<String, String> contactMetaData,  Contact contact, String folderId) throws SQLException {
        Contact updatedContact;
        if (folderId == null || folderId.isEmpty())// happens if device has no folder specified, eg device: mensing@kieback-peter.de/
            updatedContact = graphClient.contacts().post(contact);
        else
            updatedContact = graphClient.contactFolders().byContactFolderId(folderId).contacts().post(contact);

        DBConnector.updateSyncabonnementEntry(contactMetaData, App.LUID, contactMetaData.get(App.SYNCABONNEMENTID), updatedContact.getId());// TODO updatedContact.getId() might cause NullPointerException, read in variable before and check for null
        DBConnector.updateSyncabonnementEntry(contactMetaData, App.SYNCED, contactMetaData.get(App.SYNCABONNEMENTID), contactMetaData.get(App.ABOSTART));

        return updatedContact;
    }


    private static void deleteContact(UserItemRequestBuilder graphClient, Map<String, String> contactMetaData, String luid) throws SQLException {
        graphClient.contacts().byContactId(luid).delete();

        DBConnector.updateSyncabonnementEntry(contactMetaData, App.LUID, contactMetaData.get(App.SYNCABONNEMENTID), null);
        DBConnector.updateSyncabonnementEntry(contactMetaData, App.SYNCED, contactMetaData.get(App.SYNCABONNEMENTID), contactMetaData.get(App.ABOENDE));
    }


    private static Contact patchContact(UserItemRequestBuilder graphClient, Map<String, String> contactMetaData,  Contact contact, String luid) throws SQLException
    {
        Contact updatedContact = graphClient.contacts().byContactId(luid).patch(contact);

        DBConnector.updateSyncabonnementEntry(contactMetaData, App.SYNCED, contactMetaData.get(App.SYNCABONNEMENTID), contactMetaData.get(App.CHANGED));

        return updatedContact;
    }



    private static Contact moveContact(UserItemRequestBuilder graphClient, Map<String, String> contactMetaData, Contact contact, String folderId) throws SQLException
    {
        Contact updatedContact;
        if (folderId == null || folderId.isEmpty())// happens if device has no folder specified, eg device: mensing@kieback-peter.de/
            updatedContact = graphClient.contacts().post(contact);
        else
            updatedContact = graphClient.contactFolders().byContactFolderId(folderId).contacts().post(contact);

        graphClient.contacts().byContactId(contactMetaData.get(App.LUID)).delete();

        DBConnector.updateSyncabonnementEntry(contactMetaData, App.LUID, contactMetaData.get(App.SYNCABONNEMENTID), updatedContact.getId());// TODO updatedContact.getId() might cause NullPointerException, read in variable before and check for null
        DBConnector.updateSyncabonnementEntry(contactMetaData, App.SYNCED, contactMetaData.get(App.SYNCABONNEMENTID), contactMetaData.get(App.CHANGED));

        return contact;
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
    private static String ensureFolderExists(UserItemRequestBuilder graphClient, String parentFolderId, String device) throws RuntimeException {

        device = trimChar(device, '/');

        if (device == null || device.isEmpty())
            return parentFolderId;

        String folderName;
        int nextSlash = device.indexOf('/');
        if (nextSlash == -1)
        {
            folderName = device;
            device = "";
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


    private static String findFolderId(UserItemRequestBuilder graphClient, String parentFolderId, String folderName) throws IllegalStateException {
        // if parentFolderId is null or invalid/does not exist, function will throw error, caught by updateContact catch-block
        // TODO catch this error message and put it in the syncupdate
        ContactFolderCollectionResponse folderCollection;

        if (parentFolderId == null || parentFolderId.isEmpty())
            folderCollection = graphClient.contactFolders().get();
        else
            folderCollection = graphClient.contactFolders().byContactFolderId(parentFolderId).childFolders().get();

        if (folderCollection == null || folderCollection.getValue() == null)
            throw new IllegalStateException("Unable to retrieve contactFolders from MSGraph.");
        for (var folder : folderCollection.getValue()) // no null/empty check needed, see comment above
        {
            if (folder.getDisplayName() != null && folder.getDisplayName().equalsIgnoreCase(folderName))
                return folder.getId();
        }
        return null;
    }


    protected static String createFolders(UserItemRequestBuilder graphClient, String parentFolderId, String device) throws RuntimeException  {
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
                    throw new RuntimeException("Failed to create new folder in MSGraph: " + e);
                }
            }
            else
                newFolderResponse = graphClient.contactFolders().byContactFolderId(parentFolderId).childFolders().post(newFolder);

            if (newFolderResponse == null)
                throw new IllegalStateException("Failed to retrieve Folder Collection from MSGraph.");
            parentFolderId = newFolderResponse.getId();
        }
        return parentFolderId;
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


    // functions validates that contact has category "AditoKontakte"
    private static boolean addAditoCategoryToContact(ContactsRequestBuilder contacts, Contact contact, String contactID, CONTACT_STATUS status)
    {
        if (status == CONTACT_STATUS.TO_DELETE)
            return false;
        if (status == CONTACT_STATUS.TO_CREATE)
        {
            contact.setCategories(List.of(App.ADITO_CONTACTS_FOLDERNAME));
            return true;
        }

        Contact contactData;
        try
        {
            contactData = contacts.byContactId(contactID).get();
        }
        catch (Exception e)
        {
            log.error("\033[0;31mNo contact found in Outlook for LUID: {}\033[0m", contactID);
            throw new IllegalArgumentException(e);
        }

        if (contactData != null)
        {
            List<String> categories = contactData.getCategories();
            if (categories != null && !categories.contains(App.ADITO_CONTACTS_FOLDERNAME))
            {
                categories.add(App.ADITO_CONTACTS_FOLDERNAME);
                contact.setCategories(categories);
                return true;
            }
        }
        return false;
    }
}
