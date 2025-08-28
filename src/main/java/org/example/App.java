package org.example;

import com.microsoft.graph.models.Contact;
import org.example.ContactData.ParseContact;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class App {

    protected static final String USER = "mensing@kieback-peter.de"; // now for testing. TODO delete later

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Debug UI");
        JTextArea textArea = new JTextArea(30, 80);
        textArea.setEditable(false); // nur Anzeige


        try
        {
            // get data via sql
            ResultSet aboData = DBConnector.executeQuery("select a.guid,a.luid,a.abostart,a.aboende,a.changed,a.synced,a.to_external,d.device,d.devicespecifics,d.avoidfields from syncabonnement a join syncprincipal p ON a.principal=p.syncprincipalid JOIN syncdevice d ON p.syncdevice_id=d.syncdeviceid");

            while (aboData.next())
            {
                ParseContact parse = new ParseContact();
                Map<String, String> contactMetaData = getContactMetaData(aboData);

                var status = AditoRequests.getContactStatus(contactMetaData);

                contactMetaData.put("guid", "5500"); // TODO delete this

                Contact contact = parse.getContact(AditoRequests.getResultFromMockSQLFunction(contactMetaData), contactMetaData);
                OutlookContactUpdater.updateContact(contact, contactMetaData, status);

                lol(textArea, frame, contact, status, contactMetaData);// TODO delete this
                break;// TODO and this
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

    private static Map<String, String> getContactMetaData(ResultSet aboData) throws SQLException {
        Map<String, String> map = new HashMap<>();
        map.put("luid", aboData.getString("luid"));
        map.put("guid", aboData.getString("guid"));
        map.put("abostart", aboData.getString("abostart"));
        map.put("aboende", aboData.getString("aboende"));
        map.put("changed", aboData.getString("changed"));
        map.put("synced", aboData.getString("synced"));
        map.put("to_external", aboData.getString("to_external"));
        map.put("avoid", aboData.getString("avoidfields"));
        map.put("device", aboData.getString("device"));
        map.put("devicespecifics", aboData.getString("devicespecifics"));
        return map;
    }










    private static void lol(JTextArea textArea, JFrame frame, Contact contact, AditoRequests.CONTACT_STATUS status, Map<String, String> contactMeta) {
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
            if (!contact.getBusinessPhones().isEmpty())
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
    }
}
