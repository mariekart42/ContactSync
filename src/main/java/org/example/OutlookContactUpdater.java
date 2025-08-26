package org.example;

import com.microsoft.graph.models.Contact;
import com.microsoft.graph.users.item.contacts.ContactsRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.example.AditoRequests.CONTACT_STATUS.TO_CREATE;
import static org.example.AditoRequests.CONTACT_STATUS.TO_DELETE;


public class OutlookContactUpdater {

    // TODO:
    //  - catch syncresult, store it in `SYNCABONNEMENT.syncresult`
    //  - update `SYNCABONNEMENT.synced`, `SYNCABONNEMENT.changed`, `SYNCABONNEMENT.abostart` and `SYNCABONNEMENT.aboende`
    public static void updateContact(Contact contact, String user, String contactID, AditoRequests.CONTACT_STATUS status)
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

            boolean categoryUpdate = addAditoCategoryToContact(contacts, contact, contactID, status);

            switch (status)
            {
                case TO_CHANGE -> {
                    System.out.println("\033[0;33mCONTACT:\n\tID: " + contactID + "\n\tNAME: " + contact.getDisplayName() + "\nhas status TO_CHANGE -> PATCH to Outlook\033[0m");
                    contacts.byContactId(contactID).patch(contact);
                }
                case TO_CREATE -> {
                    contacts.post(contact);
                    System.out.println("\033[0;32mCONTACT:\n\tID: " + contactID + "\n\tNAME: " + contact.getDisplayName() + "\nhas status TO_CREATE -> POST to Outlook\033[0m");
                }
                case TO_DELETE -> {
                    //contacts.byContactId(contactID).delete();
                    System.out.println("\033[0;31mCONTACT:\n\tID: " + contactID + "\n\tNAME: " + contact.getDisplayName() + "\nhas status TO_DELETE -> DELETE to Outlook\033[0m");
                    System.out.println("We skip deleting for now, easier to test program rn");// TODO: delete later, uncomment prev line
                }
                case UNCHANGED -> {
                    if (categoryUpdate)
                        contacts.byContactId(contactID).patch(contact);
                    System.out.println("\033[0;36mCONTACT:\n\tID: " + contactID + "\n\tNAME: " + contact.getDisplayName() + "\nhas status UNCHANGED -> nothing to Outlook\033[0m");
                }
                default -> throw new Exception("CONTACT_STATUS has unexpected value: " + status);
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred while updating a contact in Outlook: " + e);
            System.out.println("Debug stack Trace:");
            e.printStackTrace();
        }
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
