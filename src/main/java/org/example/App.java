package org.example;

import com.microsoft.graph.models.*;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.microsoft.graph.users.item.contacts.item.extensions.ExtensionsRequestBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
//            User specificUser = TestCRUDRequests.getSpecificUser(graphClient, USER);
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
// * *  POST Contact in a specific folder
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
//


//
//            String id = TestCRUDRequests.getSpecificContactIdFromDisplayName(graphClient, USER, "Anrede Name Namenszusatz");
//
////            Contact oo = graphClient.users().byUserId(USER).contacts().byContactId(id).get();
////            System.out.println(oo.getDisplayName());
//
//            TestCRUDRequests.printAllContactDataWithAllFoldersAndContacts(graphClient, USER, id);
//
//
//            ExtensionsRequestBuilder hehe = graphClient.users().byUserId(USER).contacts().byContactId(id).extensions();
//
//
//

//            for (String s : AditoRequests.getSomething())
//            {
//                System.out.println("Something: " + s);
//            }

        AditoRequests.printAllYouGot();

            System.out.println();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
