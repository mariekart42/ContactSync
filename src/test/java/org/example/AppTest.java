package org.example;

import com.microsoft.graph.models.Contact;
import org.example.ContactData.ParseContact;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testContact()
    {
        String toExternal = "keymapversion=6\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String newestAditoData = "keymapversion=6\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String toAvoid = "";
        ParseContact parse = new ParseContact();

        Contact contact = parse.getContact(newestAditoData, toExternal, toAvoid);
        assertEquals(contact, null);
    }

    @Test
    void testContactChange()
    {
        String toExternal = "keymapversion=6\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String newestAditoData = "keymapversion=7\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String toAvoid = "";
        ParseContact parse = new ParseContact();
        Contact contact = parse.getContact(newestAditoData, toExternal, toAvoid);
        assertNotEquals(contact, null);
    }

    @Test
    void testContactIgnoreTabsNoChanges()
    {
        String toExternal = "keymapversion=6\n" +
                "        anrede=\n" +
                "        funktion=Inhaber/in\n" +
                "        name=Lüthen\n" +
                "        geb=20150924\n" +
                "        vorname=Wilfried\n" +
                "        geb=20150924\n" +
                "        p_strasse=\n" +
                "        p_plz=\n" +
                "        p_ort=\n" +
                "        p_lkz=\n" +
                "        bphone1=\n" +
                "        bphone2=+49 3834 817444\n" +
                "        mobile=+49 1738029528\n" +
                "        carphone=\n" +
                "        otherfax=+49 3834 514954\n" +
                "        bfax=\n" +
                "        companyphone=+49 3834 817444\n" +
                "        web=\n" +
                "        firma=Ing.-Büro Wilfried Lüthen\n" +
                "        strasse=Andreas-Mayer-Str. 23\n" +
                "        ort=Greifswald\n" +
                "        plz=17491\n" +
                "        email1=\n" +
                "        email2=w.luethen@t-online.de\n" +
                "        email3=W.Luethen@t-online.de\n" +
                "        info=";
        String newestAditoData = "keymapversion=6\n" +
                "anrede=\n" +
                "funktion=Inhaber/in\n" +
                "name=Lüthen\n" +
                "geb=20150924\n" +
                "vorname=Wilfried\n" +
                "geb=20150924\n" +
                "p_strasse=\n" +
                "p_plz=\n" +
                "p_ort=\n" +
                "p_lkz=\n" +
                "bphone1=\n" +
                "bphone2=+49 3834 817444\n" +
                "mobile=+49 1738029528\n" +
                "carphone=\n" +
                "otherfax=+49 3834 514954\n" +
                "bfax=\n" +
                "companyphone=+49 3834 817444\n" +
                "web=\n" +
                "firma=Ing.-Büro Wilfried Lüthen\n" +
                "strasse=Andreas-Mayer-Str. 23\n" +
                "ort=Greifswald\n" +
                "plz=17491\n" +
                "email1=\n" +
                "email2=w.luethen@t-online.de\n" +
                "email3=W.Luethen@t-online.de\n" +
                "info=";
        String toAvoid = "";
        ParseContact parse = new ParseContact();
        Contact contact = parse.getContact(newestAditoData, toExternal, toAvoid);
        assertEquals(contact, null);
    }

    @Test
    void testContactNoChanges()
    {
        String toExternal = "keymapversion=6\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String newestAditoData = "keymapversion=6\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String toAvoid = "";
        ParseContact parse = new ParseContact();
        Contact contact = parse.getContact(newestAditoData, toExternal, toAvoid);
        assertEquals(contact, null);
    }


    @Test
    void main()
    {
        try {
            // get data via sql
            ResultSet aboData = DBConnector.executeQuery("SELECT * FROM syncabonnement s where s.guid = '5500'");
            ArrayList<Map<String, String>> contactMetaData = new ArrayList<>();
            while (aboData.next()) {
                Map<String, String> entry = new HashMap<>();
                entry.put("luid", aboData.getString("luid"));
                entry.put("guid", aboData.getString("guid"));
                entry.put("abostart", aboData.getString("abostart"));
                entry.put("aboende", aboData.getString("aboende"));
                entry.put("changed", aboData.getString("changed"));
                entry.put("synced", aboData.getString("synced"));
                entry.put("to_external", aboData.getString("to_external"));


                ResultSet deviceData = DBConnector.executeQuery("select avoidfields from syncabonnement abo join syncprincipal p ON abo.principal = p.syncprincipalid JOIN syncdevice de ON p.syncdevice_id = de.syncdeviceid where abo.luid = '"+aboData.getString("luid")+"'");
                if (deviceData.next())
                {
                    entry.put("avoid", deviceData.getString("avoidfields"));
                }
                contactMetaData.add(entry);
            }

            // contactMetaData is now a list of Maps containing all contacts syncabonnement data and avoid fields

            ParseContact parse = new ParseContact();
            for (var contactMeta : contactMetaData) {

                //var status = AditoRequests.getContactStatus(contactMeta);

                var test = contactMeta.get("luid");
                // TODO get newest adito data
                String newestAditoData = AditoRequests.getResultFromMockSQLFunction(contactMeta.get("guid"), contactMeta.get("avoid"));
                Contact contact = parse.getContact(newestAditoData, contactMeta.get("to_external"), contactMeta.get("avoid"));




                //OutlookContactUpdater.updateContact(contact, USER, contactMeta.get("luid"), status);

            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}