package org.example;

import com.microsoft.graph.models.Contact;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.example.ContactData.ParseContact;

import java.sql.ResultSet;

public class App {

    private static final String USER = "mensing@kieback-peter.de";

    public static void main(String[] args)
    {
        AppConfig config = new AppConfig();
        try
        {
            GraphServiceClient graphClient = config.getGraphServiceClient();
//-----------------------------------------------------------------------------------------------------------------
// * *  GET list of all users
//            UserCollectionResponse users = graphClient.users().get();
//            for (var user : users.getValue()) {
//                System.out.println(user.getDisplayName());
//                System.out.println(user.getMail());
//                System.out.println();
//            }
//-----------------------------------------------------------------------------------------------------------------
// * *  GET user with id = email
//            ParseContact specificUser = TestCRUDRequests.getSpecificUser(graphClient, USER);
//            System.out.println(specificUser.getDisplayName());
//-----------------------------------------------------------------------------------------------------------------
// * *  GET contacts of a specific user with id = email
//            ContactCollectionResponse usersContacts = TestCRUDRequests.getUsersContacts(graphClient, USER);
//            for (var user : usersContacts.getValue())
//            {
//                System.out.println("Display Name: " + user.getDisplayName());
//                for (var email : user.getEmailAddresses())
//                {
//                    System.out.println("E-Mails:");
//                    System.out.println("\t- "+email.getAddress());
//                }
//                if (user.getCategories() != null && !user.getCategories().isEmpty())
//                {
//                    for (var cat : user.getCategories())
//                    {
//                        System.out.println("contact is in category: " + cat);
//                    }
//                }
//                System.out.println("Company Name: " + user.getCompanyName());
//                System.out.println("ID: " + user.getId());
//                System.out.println();
//            }
//-----------------------------------------------------------------------------------------------------------------
// * *  CREATE new contact for a specific user
//            TestMyContact newContact = new TestMyContact("Ulf", "Mensing", "ubm@shit.de", "some name", "1256903467");
//            TestCRUDRequests.createNewContactForUser(graphClient, USER, newContact);
//-----------------------------------------------------------------------------------------------------------------
// * *  DELETE a specific contact from a specific user
//            ContactCollectionResponse contacts = graphClient.users().byUserId(USER).contacts().get(requestConfiguration -> {
//                requestConfiguration.queryParameters.filter = "(displayName eq 'Ulf Mensing')";
//                requestConfiguration.headers.add("ConsistencyLevel", "eventual");
//            });
//            String ulfID = contacts.getValue().getFirst().getId();
//            TestCRUDRequests.deleteSpecificContactFromSpecificUser(graphClient, USER, ulfID);
//-----------------------------------------------------------------------------------------------------------------
// * *  DELETE a specific contact from a specific user in a specific folder
//            String contactFolder = "AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwAuAAAAAABb4L58lBuvQaM7Snxz45CZAQDKuT-HM9gZQIxqxJFFZgGEAABulsmtAAA=";
//            ContactCollectionResponse contacts = graphClient.users().byUserId(USER).contactFolders().byContactFolderId(contactFolder).contacts().get(requestConfiguration -> {
//                requestConfiguration.queryParameters.filter = "startswith(displayName, 'in someNewFolder/childFolder')";
//                requestConfiguration.headers.add("ConsistencyLevel", "eventual");
//            });
//            String id = contacts.getValue().getFirst().getId();
//            TestCRUDRequests.deleteSpecificContactFromSpecificUser(graphClient, USER, id);
//-----------------------------------------------------------------------------------------------------------------
// * *  POST a new Folder with Display Name
//            TestCRUDRequests.createNewFolder(graphClient, USER, "some new folder");
//-----------------------------------------------------------------------------------------------------------------
// * *  GET all Folders of a specific user
//            ContactFolder tmp_SomeNewFolderObject = null;
//            ContactFolderCollectionResponse contactFolders = TestCRUDRequests.getAllFolderOfUser(graphClient, USER);
//            String folderID = "";
//            String childFolderID = "";
//            if (contactFolders.getValue().isEmpty())
//                System.out.println("No folders found");
//            for (var folder : contactFolders.getValue())
//            {
//                if (Objects.requireNonNull(folder.getDisplayName()).equalsIgnoreCase("some new folder"))
//                {
//                    ContactFolderCollectionResponse kidsFolders = graphClient.users().byUserId(USER).contactFolders().byContactFolderId(folder.getId()).childFolders().get();
//                    tmp_SomeNewFolderObject = folder;
//                    folderID = folder.getId();
////                    System.out.println("HERE: " + folder.getChildFolders());
////                    System.out.println("HERE: " + folder.getChildFolders().getFirst());
//
//                    if (kidsFolders != null)
//                    {
//                        System.out.println("Child Folders:");
//                        for (var child : kidsFolders.getValue())
//                        {
//                            System.out.println("\t- NAME: " + child.getDisplayName());
//                            System.out.println("\t- ID  : " + child.getId());
//                        }
////                        childFolderID = folder.getChildFolders().getFirst().getId();
//                    }
//                }
//                System.out.println("Folder: " + folder.getDisplayName());
//                System.out.println("Parent Folder ID: " + folder.getParentFolderId());
//                System.out.println("Folder ID: " + folder.getId());
////                System.out.println(folder.getChildFolders());
//                System.out.println();
//            }
//-----------------------------------------------------------------------------------------------------------------
// * *  DELETE a specific folder
//            System.out.println("Folder Id to delete: " + folderID);
//            TestCRUDRequests.deleteFolderFromSpecificUser(graphClient, USER, folderID);
//-----------------------------------------------------------------------------------------------------------------
// * *  POST ParseContact in a specific folder
//            TestMyContact contact = new TestMyContact("in someNewFolder/childFolder", "Nomnom", "sa@gmail.lol", "sasa", "999888");
//            TestCRUDRequests.createNewUserInSpecificFolder(graphClient, USER, folderID, contact);
//-----------------------------------------------------------------------------------------------------------------
// * *  POST new folder inside a specific folder
//            ContactFolder contactFolder = new ContactFolder();
//            contactFolder.setDisplayName("bums\\kram");
//            graphClient.users().byUserId(USER).contactFolders().byContactFolderId(folderID).childFolders().post(contactFolder);
//
//            ContactFolder contactFolder2 = new ContactFolder();
//            contactFolder2.setDisplayName("bums2/kram");
//            graphClient.users().byUserId(USER).contactFolders().byContactFolderId(folderID).childFolders().post(contactFolder2);
//-----------------------------------------------------------------------------------------------------------------
// * *  POST new user in folder in a folder

            // GET a specific folder based on the display name
            // it's not possible to get a child folder like this
//            ContactFolderCollectionResponse lol = graphClient.users().byUserId(USER).contactFolders().get(
//                    requestConfiguration -> {
//                        requestConfiguration.queryParameters.filter = "(displayName eq 'some new folder')";
//                        requestConfiguration.headers.add("ConsistencyLevel", "eventual");
//                    });
//            System.out.println("Folder Name: " + lol.getValue().getFirst().getDisplayName());



            // id of someNewFolder:
//            String id_someNewFolder = "AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwAuAAAAAABb4L58lBuvQaM7Snxz45CZAQDKuT-HM9gZQIxqxJFFZgGEAABulsmtAAA=";
//            System.out.println(tmp_SomeNewFolderObject);
//            ContactFolderCollectionResponse kids = graphClient.users().byUserId(USER).contactFolders().byContactFolderId(id_someNewFolder).childFolders().get();
//            String kidsFolderID = "";
//            for (var folders : kids.getValue())
//            {
//                if (Objects.requireNonNull(folders.getDisplayName()).equalsIgnoreCase("child folder"))
//                {
//                    kidsFolderID = folders.getId();
//                }
//                System.out.println("Kids folders: " + folders.getId());
//            }
//
//            if (kidsFolderID != "")
//            {
//                System.out.println("Kids folder id: " + kidsFolderID);
//                TestMyContact contact = new TestMyContact("in someNewFolder/childFolder", "Nomnom", "sa@gmail.lol", "sasa", "999888");
//                TestCRUDRequests.createNewUserInSpecificFolder(graphClient, USER, kidsFolderID, contact);
//
//            }
//-----------------------------------------------------------------------------------------------------------------
// * *  UPDATE folder name
//            String contactFolderID = TestCRUDRequests.getSpecificContactFolderIdFromDisplayName(graphClient, USER, "NEW DISPLAY NAME");
//            TestCRUDRequests.updateContactFolderName(graphClient, USER, contactFolderID ,"some even newer folder");
//-----------------------------------------------------------------------------------------------------------------
// * *  UPDATE some info in contact
            String contactID = TestCRUDRequests.getSpecificContactIdFromDisplayName(graphClient, USER, "TESTTESTvorname TESTTESTname");
//            TestCRUDRequests.updateInfoInContact(graphClient, USER, contactID);
//-----------------------------------------------------------------------------------------------------------------
// * *  UPDATE contact name
//            TestCRUDRequests.updateContactName(graphClient, USER, contactID, "NEW NAME");

//
//            TestCRUDRequests.printAllContactDataWithAllFoldersAndContacts(graphClient, USER, id);
//
//
//            ExtensionsRequestBuilder hehe = graphClient.users().byUserId(USER).contacts().byContactId(id).extensions();
//
//
//


            // TEST GUY
            // ID: AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwBGAAAAAABb4L58lBuvQaM7Snxz45CZBwDKuT-HM9gZQIxqxJFFZgGEAAAAAAEOAADKuT-HM9gZQIxqxJFFZgGEAAAbPIQyAAA=
            // NAME: TESTTESTvorname TESTTESTname
            String testLUID = "AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwBGAAAAAABb4L58lBuvQaM7Snxz45CZBwDKuT-HM9gZQIxqxJFFZgGEAAAAAAEOAADKuT-HM9gZQIxqxJFFZgGEAAAbPIQyAAA=";

            String avoidFields = "*web*";
            String newestAditoData = AditoRequests.getResultFromMockSQLFunction("", avoidFields);

            String luid = testLUID; // now hardcoded, later extract from syncabonnement.luid


            ResultSet toExternalData = DBConnector.executeQuery("SELECT s.to_external FROM SYNCABONNEMENT s where s.luid='"+luid+"'");
            toExternalData.next();


            Contact contact = ParseContact.getContact(newestAditoData, toExternalData.getString("to_external"), avoidFields);
            OutlookContactUpdater.updateContact(contact, USER, luid);


            System.out.println();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }



    static void printAsBytes(String str)
    {
        // Convert the string to a byte array using the default charset (UTF-8)
        byte[] bytes = str.getBytes();

        // Print the byte values
        System.out.println("Byte values of the string:");
        for (byte b : bytes) {
            System.out.print(b + " ");
        }

        System.out.println();

        // To visualize characters and hidden control characters
        System.out.println("\nVisual representation of the string:");
        for (byte b : bytes) {
            // If it's a printable character, print it, otherwise show the byte value
            if (b >= 32 && b <= 126) {
                System.out.print((char) b);
            } else {
                System.out.print("[" + b + "] ");
            }
        }
    }
}
