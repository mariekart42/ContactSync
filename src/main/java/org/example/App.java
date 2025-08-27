package org.example;

import com.microsoft.graph.models.Contact;
import org.example.ContactData.ParseContact;
//import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class App {

    protected static final String USER = "mensing@kieback-peter.de";

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Debug UI");
        JTextArea textArea = new JTextArea(30, 80);
        textArea.setEditable(false); // nur Anzeige
        try {
            // get data via sql
            ResultSet aboData = DBConnector.executeQuery("SELECT * FROM syncabonnement");
            ArrayList<Map<String, String>> contactMetaData = new ArrayList<>();
            while (aboData.next())
            {
                Map<String, String> entry = new HashMap<>();
                entry.put("luid", aboData.getString("luid"));
                entry.put("guid", aboData.getString("guid"));
                entry.put("abostart", aboData.getString("abostart"));
                entry.put("aboende", aboData.getString("aboende"));
                entry.put("changed", aboData.getString("changed"));
                entry.put("synced", aboData.getString("synced"));
                entry.put("to_external", aboData.getString("to_external"));


                ResultSet deviceData = DBConnector.executeQuery("select avoidfields, device from syncabonnement abo join syncprincipal p ON abo.principal = p.syncprincipalid JOIN syncdevice de ON p.syncdevice_id = de.syncdeviceid where abo.luid = '"+aboData.getString("luid")+"'");
                if (deviceData.next())
                {
                    entry.put("avoid", deviceData.getString("avoidfields"));
                    entry.put("device", deviceData.getString("device"));
                }

                contactMetaData.add(entry);
            }

            // contactMetaData is now a list of Maps containing all contacts syncabonnement data and avoid fields

            ParseContact parse = new ParseContact();
            for (Map<String, String> contactMeta : contactMetaData) {

                var status = AditoRequests.getContactStatus(contactMeta);

                //var test = contactMeta.get("luid");

                String newestAditoData = AditoRequests.getResultFromMockSQLFunction(contactMeta.get("guid"), contactMeta.get("avoid"));
                Contact contact = parse.getContact(newestAditoData, contactMeta.get("to_external"), contactMeta.get("avoid"));

                lol(textArea, frame, contact, status, contactMeta);
                // add contact to aditoCategory
//                OutlookContactUpdater.updateContactsCategory(USER, contactMeta.get("luid"));

                OutlookContactUpdater.updateContact(contact, contactMeta, USER,  status);
            }


            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // Fenster zentrieren
            frame.setVisible(true);
            System.out.println();
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private static void lol(JTextArea textArea, JFrame frame, Contact contact, AditoRequests.CONTACT_STATUS status, Map<String, String> contactMeta) {
//        JFrame frame = new JFrame("Debug UI");
//        JTextArea textArea = new JTextArea(30, 80);
//        textArea.setEditable(false); // nur Anzeige
//        for (String line : data) {
//            textArea.append(line + "\n");
//        }

        if (contact == null)
        {
            textArea.append("\n\n\n\n");
            textArea.append("CONTACT:\n");
            textArea.append("[contact is null, nothing to update]");
            return;
        }
        textArea.append("\n\n\n\n");
        textArea.append("CONTACT:\n");
        textArea.append("STATUS: " + status + "\n");
        textArea.append("GUID: " + contactMeta.get("guid") + "\n");
        textArea.append("LUID: " + contactMeta.get("luid") + "\n\n");

        textArea.append("Name  :" +  contact.getDisplayName() + "\n");
        textArea.append("Name Title  :" + contact.getTitle() + "\n");
        textArea.append("Job Title  :" + contact.getJobTitle() + "\n");
        textArea.append("Surname  :" + contact.getSurname() + "\n");
        textArea.append("Birthday  :" + contact.getBirthday() + "\n");
        textArea.append("First Name  :" + contact.getGivenName() + "\n");
        if (contact.getHomeAddress() != null){
            textArea.append("Physical Addresses (Home)" + "\n");
            textArea.append("Street  :" + contact.getHomeAddress().getStreet() + "\n");
            textArea.append("Postal Code  :" + contact.getHomeAddress().getPostalCode() + "\n");
            textArea.append("City  :"  + contact.getHomeAddress().getCity() + "\n");
            textArea.append("Country/Region:"  + contact.getHomeAddress().getCountryOrRegion() + "\n");
        } else
            textArea.append("No home addresses\n");
        textArea.append("Phone Numbers");
        if (contact.getBusinessPhones() != null && !contact.getBusinessPhones().isEmpty()) {
            if (contact.getBusinessPhones().size() > 0)
                textArea.append("Business 1  :" + contact.getBusinessPhones().get(0));
            if (contact.getBusinessPhones().size() > 1)
                textArea.append("Business 2  :" + contact.getBusinessPhones().get(1));
        }
        else
            textArea.append("Business 2  :[empty]\n");
        textArea.append("Mobile  :" + contact.getMobilePhone() + "\n");
//        textArea.append("Car  :" + contact.get);
        textArea.append("Other Fax  :\n");
        textArea.append("Business Fax  :\n");
        textArea.append("Primary  :\n");
        textArea.append("Company  :\n");
        textArea.append("Business Homepage :" + contact.getBusinessHomePage() + "\n");
        textArea.append("Company Name  :" + contact.getCompanyName() + "\n");
        if (contact.getBusinessAddress() != null)
        {
            textArea.append("Physical Addresses (Business)\n");
            textArea.append("Street  :" + contact.getBusinessAddress().getStreet() + "\n");
            textArea.append("City  :" + contact.getBusinessAddress().getCity() + "\n");
            textArea.append("Postal Code  :" + contact.getBusinessAddress().getPostalCode() + "\n");
        }
        textArea.append("Info/Body  :" + contact.getPersonalNotes() + "\n");
//        if (contact.getEmailAddresses() != null)
//        {
//            if (contact.getEmailAddresses().get(0) != null)
//                textArea.append("E-Mail Address 1  :" + contact.getEmailAddresses().get(0).getAddress() + "\n");
//            if (contact.getEmailAddresses().get(1) != null)
//                textArea.append("E-Mail Address 2  :" + contact.getEmailAddresses().get(1).getAddress() + "\n");
//            if (contact.getEmailAddresses().get(2) != null)
//                textArea.append("E-Mail Address 3  :" + contact.getEmailAddresses().get(2).getAddress() + "\n");
//        }

         frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

//        frame.pack();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null); // Fenster zentrieren
//        frame.setVisible(true);
    }
}
