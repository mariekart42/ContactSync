package org.example;

import com.microsoft.graph.models.Contact;
import org.example.ContactData.ParseContact;

import java.sql.ResultSet;

public class App {

    private static final String USER = "mensing@kieback-peter.de";

    public static void main(String[] args)
    {
        try
        {
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
}
