package org.example;

import com.microsoft.graph.models.Contact;
import com.microsoft.graph.models.ContactFolder;
import com.microsoft.graph.models.ContactFolderCollectionResponse;
import com.microsoft.graph.users.item.UserItemRequestBuilder;
import com.microsoft.graph.users.item.contactfolders.ContactFoldersRequestBuilder;
import com.microsoft.graph.users.item.contacts.ContactsRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.example.AditoRequests.CONTACT_STATUS.TO_CREATE;
import static org.example.AditoRequests.CONTACT_STATUS.TO_DELETE;


public class OutlookContactUpdater {

    ContactFolderCollectionResponse _contactFolders;

    // TODO:
    //  - catch syncresult, store it in `SYNCABONNEMENT.syncresult`
    //  - update `SYNCABONNEMENT.synced`, `SYNCABONNEMENT.changed`, `SYNCABONNEMENT.abostart` and `SYNCABONNEMENT.aboende`
    //  - create folder and put contact inside with information of 'device' -> later: move contact to new folder if it changed
    public static void updateContact(Contact contact, String user, String contactLuid, AditoRequests.CONTACT_STATUS status)
    {
        if (contact == null)
            return;


        AppConfig config = new AppConfig();
        try
        {
            ContactsRequestBuilder contacts = config.getGraphServiceClient()
                    .users()
                    .byUserId(user)
                    .contacts();

            var graphClient = config.getGraphServiceClient()
                    .users()
                    .byUserId(user);

            boolean categoryUpdate = addAditoCategoryToContact(contacts, contact, contactLuid, status);
            moveContactToCorrectFolder(graphClient, contact, contactLuid);

//            switch (status)
//            {
//                case TO_CHANGE -> {
//                    System.out.println("\033[0;33mCONTACT:\n\tID: " + contactLuid + "\n\tNAME: " + contact.getDisplayName() + "\nhas status TO_CHANGE -> PATCH to Outlook\033[0m");
//                    contacts.byContactId(contactLuid).patch(contact);
//                }
//                case TO_CREATE -> {
//                    contacts.post(contact);
//                    System.out.println("\033[0;32mCONTACT:\n\tID: " + contactLuid + "\n\tNAME: " + contact.getDisplayName() + "\nhas status TO_CREATE -> POST to Outlook\033[0m");
//                }
//                case TO_DELETE -> {
//                    //contacts.byContactId(contactID).delete();
//                    System.out.println("\033[0;31mCONTACT:\n\tID: " + contactLuid + "\n\tNAME: " + contact.getDisplayName() + "\nhas status TO_DELETE -> DELETE to Outlook\033[0m");
//                    System.out.println("We skip deleting for now, easier to test program rn");// TODO: delete later, uncomment prev line
//                }
//                case UNCHANGED -> {
//                    if (categoryUpdate)
//                        contacts.byContactId(contactLuid).patch(contact);
//                    System.out.println("\033[0;36mCONTACT:\n\tID: " + contactLuid + "\n\tNAME: " + contact.getDisplayName() + "\nhas status UNCHANGED -> nothing to Outlook\033[0m");
//                }
//                default -> throw new Exception("CONTACT_STATUS has unexpected value: " + status);
//            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred while updating a contact in Outlook: " + e);
            System.out.println("Debug stack Trace:");
            e.printStackTrace();
        }
    }

    private static void moveContactToCorrectFolder(UserItemRequestBuilder graphClient, Contact contactData, String contactLuid) {
        System.out.println();
//        var contactsFolders = graphClient.contactFolders().get();
        System.out.println();

        // create folder, best another folder inside

             // get parent folder id
//        var contactInstance = graphClient.contacts().byContactId(contactLuid).get();
//        assert contactInstance != null;
//        var parentFolderId = contactInstance.getParentFolderId();





        /**
         * {
         *   "@odata.context": "https://graph.microsoft.com/v1.0/$metadata#users('mensing%40kieback-peter.de')/contactFolders",
         *   "value": [
         *     {
         *       "id": "AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwAuAAAAAABb4L58lBuvQaM7Snxz45CZAQDKuT-HM9gZQIxqxJFFZgGEAABulsmlAAA=",
         *       "parentFolderId": "AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwAuAAAAAABb4L58lBuvQaM7Snxz45CZAQDKuT-HM9gZQIxqxJFFZgGEAAAAAAEOAAA=",
         *       "displayName": "silly contacts"
         *     },
         *   ]
         * }
         *
         * AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwAuAAAAAABb4L58lBuvQaM7Snxz45CZAQDKuT-HM9gZQIxqxJFFZgGEAAAAAAEOAAA=
         */









        ContactFolder contactFolder = new ContactFolder();
        contactFolder.setDisplayName("AditoKontakte");
        var motherFolder = graphClient.contactFolders().post(contactFolder);

        // create child folder inside
        ContactFolder childFolderData = new ContactFolder();
        childFolderData.setDisplayName("child1");
        var childFolder = graphClient.contactFolders().byContactFolderId(motherFolder.getId()).childFolders().post(childFolderData);

        // create another child folder in child folder
        ContactFolder nestedChildFolderData = new ContactFolder();
        nestedChildFolderData.setDisplayName("child2");
        var nestedChildFolder = graphClient.contactFolders().byContactFolderId(childFolder.getId()).childFolders().post(nestedChildFolderData);



//        // create contact there and try to get him
//        Contact motherContact = new Contact();
//        motherContact.setDisplayName("Mother3");
//
//        Contact childContact = new Contact();
//        childContact.setDisplayName("Child3");
//
//        // create contact in mother folder:
//        graphClient.contactFolders().byContactFolderId(motherFolder.getId()).contacts().post(motherContact);
//
//        // create contact in child folder:
//        var childInfo = graphClient.contactFolders().byContactFolderId(childFolder.getId()).contacts().post(childContact);
//
//
//        var tmp = graphClient.contacts().byContactId(childInfo.getId()).get();
//
//
//        var allContactsFolders = graphClient.contactFolders().get();



        // device:
        // mensing@kieback-peter.de/
        // mensing@kieback-peter.de/AditoKontakt
        // mensing@kieback-peter.de/AditoKontakt/lol

        String deviceFolder1 = "AditoKontakte";
//        String deviceFolder2 = "lol";
//        String deviceFolder3 = "lol2";


        // can we from there see in what folder he is? cause later we want to figure out if hes in the right folder or if we have to move him
        // get first layer folders
        var layer1folder = graphClient.contactFolders().get();
//        var tmp3 = layer1folder.getValue();


        String device = "mensing@kieback-peter.de/AditoKontakte/child1/child2/child3/";
        boolean folderExists = false;
        String parentFolderId = "";
        for (var folder1 : layer1folder.getValue())
        {
            if (folder1.getDisplayName().equalsIgnoreCase(deviceFolder1))
            {
                folderExists = true;
                parentFolderId = folder1.getId();
                break;
            }
        }

        if (folderExists)
        {
            device = "nope/nope/child3/";
            assert parentFolderId != null;
            String deepestId = ensureFolderExists(graphClient, parentFolderId, device);
            System.out.println();
        }
        else
        {
            device = "AditoKontakte/child1/child2/child3/";
            // logic to create AditoKontakte folder and all others
            // create mother folder
            // give id to next function
            String motherFolderId = "";
            createFolders(graphClient, motherFolderId, device);
        }

        //AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwAuAAAAAABb4L58lBuvQaM7Snxz45CZAQDKuT-HM9gZQIxqxJFFZgGEAACNcn22AAA=
        //AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwAuAAAAAABb4L58lBuvQaM7Snxz45CZAQDKuT-HM9gZQIxqxJFFZgGEAACNcn22AAA=


//
//
//
//        if (folderExists)
//        {
//            folderExists = false;
//            var layer2folder = graphClient.contactFolders().byContactFolderId(parentFolderId).childFolders().get();
//
//
//            for (var folder2 : layer2folder.getValue())
//            {
//                if (folder2.getDisplayName().equalsIgnoreCase(deviceFolder2))
//                {
//                    folderExists = true;
//                    parentFolderId = folder2.getId();
//                }
//            }
//
//            if (folderExists)
//            {
//                folderExists = false;
//
//                var layer3folder = graphClient.contactFolders().byContactFolderId(parentFolderId).childFolders().get();
//                for (var folder3 : layer3folder.getValue())
//                {
//                    if (folder3.getDisplayName().equalsIgnoreCase(deviceFolder3))
//                    {
//                        folderExists = true;
//                        parentFolderId = folder3.getId();
//                    }
//                }
//
//                if (folderExists)
//                {
//                    // same as before
//                }
//                else {
//                    // logic to create all folder after second one
//                }
//            }
//            else {
//                // logic to create all folder after first one
//            }
//        }
//        else
//        {
//            // logic to create all folder
//        }




        System.out.println();
    }

    private static String ensureFolderExists(UserItemRequestBuilder graphClient, String parentFolderId, String device) {
        if (device.isEmpty())
            return parentFolderId;

        String newParentFolderId = "";
        boolean folderExists = false;

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
                return ensureFolderExists(graphClient, null, "");
        }
        else
        {
            folderName = device.substring(0, nextSlash);
            device = device.substring(nextSlash + 1);
        }

        var folderCollection = graphClient.contactFolders().byContactFolderId(parentFolderId).childFolders().get();

        for (var folder : folderCollection.getValue())
        {
            if (folder.getDisplayName().equalsIgnoreCase(folderName))
            {
                folderExists = true;
                newParentFolderId = folder.getId();
            }
        }

        if (folderExists)
        {
            return ensureFolderExists(graphClient, newParentFolderId, device);
        }
        else
        {
            createFolders(graphClient, parentFolderId, device);
            return parentFolderId;
        }
    }

    private static void createFolders(UserItemRequestBuilder graphClient, String parentFolderId, String device) {
        // TODO logic to create all missing folders
        // loop through device till empty

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


    private static boolean addAditoCategoryToContact(ContactsRequestBuilder contacts, Contact contact, String contactID, AditoRequests.CONTACT_STATUS status)
    {
        if (status == TO_DELETE)
            return false;
        if (status == TO_CREATE)
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
