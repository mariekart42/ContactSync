CREATE OR REPLACE FUNCTION get_latest_contact_properties(username TEXT)
RETURNS TEXT AS $$
DECLARE
    result TEXT;
BEGIN
    IF username = 'schuetze' THEN
        RETURN
        'keymapversion=6
        anrede=
        funktion=KD-Systemtechniker
        name=Schütze
        geb=
        vorname=Marcel
        geb=
        p_strasse=
        p_plz=
        p_ort=
        p_lkz=
        bphone1=+49 361 44267-21
        bphone2=
        mobile=+49 1511 6825803
        carphone=
        otherfax=
        bfax=+49 361 44267-33
        companyphone=+49 361 44267-0
        web=www.kieback-peter.de
        firma=Kieback&Peter GmbH & Co. KG Niederlassung Erfurt
        strasse=Weimarische Straße 16a
        ort=Erfurt
        plz=99099
        email1=
        email2=schuetze@kieback-peter.de
        email3=nl-erfurt@kieback-peter.de
        info=';
    ELSIF username = 'Marcel' THEN
        RETURN
            'keymapversion=6
            anrede=
            funktion=KD-Systemtechniker
            name=Schütze
            geb=
            vorname=Marcel
            geb=
            p_strasse=
            p_plz=
            p_ort=
            p_lkz=
            bphone1=+49 361 44267-21
            bphone2=
            mobile=+49 1511 6825803
            carphone=
            otherfax=
            bfax=+49 361 44267-33
            companyphone=+49 361 44267-0
            web=www.kieback-peter.de
            firma=Kieback&Peter GmbH & Co. KG Niederlassung Erfurt
            strasse=Weimarische Straße 16a
            ort=Erfurt
            plz=99099
            email1=
            email2=schuetze@kieback-peter.de
            email3=nl-erfurt@kieback-peter.de
            info=';
        ELSIF username = 'test' THEN
            RETURN
                'keymapversion=6
                anrede=TESTTESTanrede
                funktion=TESTTESTfunktion
                name=TESTTESTname
                geb=
                vorname=TESTTESTvorname
                geb=TESTTESTgeb
                p_strasse=
                p_plz=TESTTESTp_plz
                p_ort=TESTTESTp_ort
                p_lkz=TESTTESTp_lkz
                bphone1=TESTTESTbphone1
                bphone2=TESTTESTbphone2
                mobile=TESTTESTmobile
                carphone=TESTTESTcarphone
                otherfax=TESTTESTotherfax
                bfax=TESTTESTbfax
                companyphone=TESTTESTcompanyphone
                web=TESTTESTweb
                firma=TESTTESTfirma
                strasse=TESTTESTstrasse
                ort=TESTTESTort
                plz=TESTTESTplz
                email1=
                email2=EMAIL2_NEW
                email3=EMAIL3_NEW
                info=vv';
    ELSE
        RETURN
            'keymapversion=6
            anrede=
            funktion=Einkauf Sachbearbeiter/in
            name=HERETHISDUDE
            geb=
            vorname=S.
            geb=
            p_strasse=
            p_plz=
            p_ort=THIS IS DIFFERENT
            p_lkz=
            bphone1=+49 203 30500 31
            bphone2=
            mobile=
            carphone=
            otherfax=
            bfax=+49 203 30500 21
            companyphone=+49 203 30500 0
            web=www.railmaint.com
            firma=RailMaint GmbH Werk Duisburg
            strasse=Wintgensstr. 91
            ort=Duisburg
            plz=47058
            email1=s.gueney@wrs-duisburg.de
            email2=
            email3=info-de@railmaint.com
            info=';
    END IF;
END;
$$ LANGUAGE plpgsql;