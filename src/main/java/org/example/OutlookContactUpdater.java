package org.example;

import com.microsoft.graph.models.Contact;
import com.microsoft.graph.serviceclient.GraphServiceClient;


public class OutlookContactUpdater {

    public static void updateContact(Contact contact, String user, String contactID)
    {
        if (contact == null)
            return;

        AppConfig config = new AppConfig();
        try
        {
            GraphServiceClient graphClient = config.getGraphServiceClient();

            graphClient.users().byUserId(user).contacts().byContactId(contactID).patch(contact);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
