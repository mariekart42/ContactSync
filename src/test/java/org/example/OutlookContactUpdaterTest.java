package org.example;

import com.microsoft.graph.models.Contact;
import com.microsoft.graph.users.item.UserItemRequestBuilder;
import org.example.AditoRequests.CONTACT_STATUS;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OutlookContactUpdaterTest {

//    @Test
//    void updateContact_createNewContactInMainFolder() throws Exception {
//        Contact contact = new Contact();
//        String testName = "oweihfpqieh837918yogiYR^Reyo2igifqgo";
//        contact.setDisplayName(testName);
//
//        Map<String, String> contactMetaData = new HashMap<>();
//        contactMetaData.put("luid", null);
//        contactMetaData.put("device", "mensing@kieback-peter.de/");
//
//        OutlookContactUpdater.updateContact(contact, contactMetaData, CONTACT_STATUS.TO_CREATE);
//
//        AppConfig config = new AppConfig();
//        var graphClient = config.getGraphServiceClient().users().byUserId(App.USER);
//        var response = graphClient.contacts().get();
//        for (var contactItem : response.getValue())
//        {
//            if (contactItem.getDisplayName().equalsIgnoreCase(testName))
//            {
//                assertTrue(true);
//                return;
//            }
//        }
//        fail();
//    }

//
//    @Test
//    void updateContact_changeContactInMainFolder() throws Exception {
//        Contact contact = new Contact();
//        String newName = "owwwwwwwwwwwewfweqfqefo";
//        contact.setDisplayName(newName);
//
//        Map<String, String> contactMetaData = new HashMap<>();
//        contactMetaData.put("luid", "AAMkADg4NWM4MjMxLTc0ZjQtNGU4ZC05ZDc2LTgwMDJkNjAyNWE0YwBGAAAAAABb4L58lBuvQaM7Snxz45CZBwDKuT-HM9gZQIxqxJFFZgGEAAAAAAEOAADKuT-HM9gZQIxqxJFFZgGEAACPGe0-AAA=");
//
//        OutlookContactUpdater.updateContact(contact, contactMetaData, CONTACT_STATUS.TO_CHANGE);
//
//        AppConfig config = new AppConfig();
//        var graphClient = config.getGraphServiceClient().users().byUserId(App.USER);
//        var response = graphClient.contacts().get();
//        for (var contactItem : response.getValue())
//        {
//            if (contactItem.getDisplayName().equalsIgnoreCase(newName))
//            {
//                assertTrue(true);
//                return;
//            }
//        }
//        fail();
//    }




//    @Test
//    void createFolders_createOneNewFolder()
//    {
//        try
//        {
//            var graphClient = getUserGraphClient();
//            String parentFolderId = null;
//            String device = "/doesNotExist/";
//            OutlookContactUpdater.createFolders(graphClient, parentFolderId, device);
//
//            var response = graphClient.contactFolders().get();
//            for (var folderItem : response.getValue())
//            {
//                if (folderItem.getDisplayName().equalsIgnoreCase("doesNotExist"))
//                {
//                    assertTrue(true);
//                    OutlookContactUpdater.deleteFolder(graphClient, folderItem.getId());
//                    return;
//                }
//            }
//            fail();
//
//        } catch (Exception e) {
//            fail();
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void createFolders_createTwoNewNestedFolders()
//    {
//        System.out.println("MAKE SURE folder /doesNotExist/doesAlsoNotExist/ does not exist.");
//        try
//        {
//            var graphClient = getUserGraphClient();
//            String parentFolderId = null;
//            String device = "/doesNotExist/doesAlsoNotExist/";
//            try
//            {
//                OutlookContactUpdater.createFolders(graphClient, parentFolderId, device);
//            }
//            catch (Exception e)
//            {
//                fail(e);
//                return;
//            }
//
//            var response = graphClient.contactFolders().get();
//            for (var folderItem : response.getValue())
//            {
//                if (folderItem.getDisplayName().equalsIgnoreCase("doesNotExist"))
//                {
//                    response = graphClient.contactFolders().byContactFolderId(folderItem.getId()).childFolders().get();
//
//                    for (var folderItem2 : response.getValue())
//                    {
//                        if (folderItem2.getDisplayName().equalsIgnoreCase("doesAlsoNotExist"))
//                        {
//                            assertTrue(true);
//                            return;
//                        }
//                    }
//                    return;
//                }
//            }
//            fail();
//        } catch (Exception e) {
//            fail();
//            throw new RuntimeException(e);
//        }
//    }







    UserItemRequestBuilder getUserGraphClient() throws Exception
    {
        AppConfig config = new AppConfig();
        try {
            return config.getGraphServiceClient().users().byUserId(App.USER);
        } catch (Exception e) {
            throw new Exception("Unable to retrieve Graph Credentials: " + e);
        }
    }
}