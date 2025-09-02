package org.example;

import com.microsoft.graph.models.Contact;
import org.example.ContactData.ParseContact;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testContact() throws Exception {
        String toExternal = "keymapversion=6\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String newestAditoData = "keymapversion=6\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String toAvoid = "";

        Map<String, String> contactMetaData = new HashMap<>();
        contactMetaData.put("avoidfields", toAvoid);
        contactMetaData.put("to_external", toExternal);
        ParseContact parse = new ParseContact();

        Contact contact = parse.getContact(newestAditoData,contactMetaData, true);
        assertNull(contact);
    }

    @Test
    void testContactChange() throws Exception {
        String toExternal = "keymapversion=6\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String newestAditoData = "keymapversion=7\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String toAvoid = "";

        Map<String, String> contactMetaData = new HashMap<>();
        contactMetaData.put("avoidfields", toAvoid);
        contactMetaData.put("to_external", toExternal);
        contactMetaData.put("devicespecifics", "fileAsMapping=LastCommaFirst");
        ParseContact parse = new ParseContact();
        Contact contact = parse.getContact(newestAditoData, contactMetaData, true);
        assertNotEquals(null, contact);
    }

    @Test
    void testContactIgnoreTabsNoChanges() throws Exception {
        String toExternal = """
                keymapversion=6
                        anrede=
                        funktion=Inhaber/in
                        name=Lüthen
                        geb=20150924
                        vorname=Wilfried
                        geb=20150924
                        p_strasse=
                        p_plz=
                        p_ort=
                        p_lkz=
                        bphone1=
                        bphone2=+49 3834 817444
                        mobile=+49 1738029528
                        carphone=
                        otherfax=+49 3834 514954
                        bfax=
                        companyphone=+49 3834 817444
                        web=
                        firma=Ing.-Büro Wilfried Lüthen
                        strasse=Andreas-Mayer-Str. 23
                        ort=Greifswald
                        plz=17491
                        email1=
                        email2=w.luethen@t-online.de
                        email3=W.Luethen@t-online.de
                        info=""";
        String newestAditoData = """
                keymapversion=6
                anrede=
                funktion=Inhaber/in
                name=Lüthen
                geb=20150924
                vorname=Wilfried
                geb=20150924
                p_strasse=
                p_plz=
                p_ort=
                p_lkz=
                bphone1=
                bphone2=+49 3834 817444
                mobile=+49 1738029528
                carphone=
                otherfax=+49 3834 514954
                bfax=
                companyphone=+49 3834 817444
                web=
                firma=Ing.-Büro Wilfried Lüthen
                strasse=Andreas-Mayer-Str. 23
                ort=Greifswald
                plz=17491
                email1=
                email2=w.luethen@t-online.de
                email3=W.Luethen@t-online.de
                info=""";
        String toAvoid = "";

        Map<String, String> contactMetaData = new HashMap<>();
        contactMetaData.put("avoidfields", toAvoid);
        contactMetaData.put("to_external", toExternal);
        contactMetaData.put("devicespecifics", "fileAsMapping=LastCommaFirst");
        ParseContact parse = new ParseContact();
        Contact contact = parse.getContact(newestAditoData, contactMetaData, true);
        assertNull(contact);
    }

    @Test
    void testContactNoChanges() throws Exception {
        String toExternal = "keymapversion=6\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String newestAditoData = "keymapversion=6\nanrede=\nfunktion=Inhaber/in\nname=Lüthen\ngeb=20150924\nvorname=Wilfried\ngeb=20150924\np_strasse=\np_plz=\np_ort=\np_lkz=\nbphone1=\nbphone2=+49 3834 817444\nmobile=+49 1738029528\ncarphone=\notherfax=+49 3834 514954\nbfax=\ncompanyphone=+49 3834 817444\nweb=\nfirma=Ing.-Büro Wilfried Lüthen\nstrasse=Andreas-Mayer-Str. 23\nort=Greifswald\nplz=17491\nemail1=\nemail2=w.luethen@t-online.de\nemail3=W.Luethen@t-online.de\ninfo=";
        String toAvoid = "";
        ParseContact parse = new ParseContact();

        Map<String, String> contactMetaData = new HashMap<>();
        contactMetaData.put("avoidfields", toAvoid);
        contactMetaData.put("to_external", toExternal);
        contactMetaData.put("devicespecifics", "fileAsMapping=LastCommaFirst");
        Contact contact = parse.getContact(newestAditoData, contactMetaData, true);
        assertNull(contact);
    }
}