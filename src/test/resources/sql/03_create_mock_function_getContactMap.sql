CREATE OR REPLACE FUNCTION get_latest_contact_properties(guid TEXT)
RETURNS TEXT AS $$
DECLARE
    result TEXT;
BEGIN
    IF guid = '5500' THEN -- changed
        RETURN
        'keymapversion=6
        anrede=
        funktion=Inhaber/in
        name=LüthenNEU
        geb=20150924
        vorname=Wilfried
        geb=20150924
        p_strasse=
        p_plz=
        p_ort=
        p_lkz=
        bphone1=+49 4532 4047 205
        bphone2=
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
        info=';
    ELSIF guid = '119425' THEN -- new
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
        ELSIF guid = '151058' THEN -- delete
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
            ELSIF guid = '4285' THEN -- unchanged
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
            NULL;
    END IF;
END;
$$ LANGUAGE plpgsql;