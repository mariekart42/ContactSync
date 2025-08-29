CREATE OR REPLACE FUNCTION get_latest_contact_properties(guid TEXT)
RETURNS TEXT AS $$
DECLARE
    result TEXT;
BEGIN
    IF guid = '5500' THEN -- changed
        RETURN
        'keymapversion=6
        anrede=DEF(anrede)
        funktion=DEF(funktion)
        name=overridden
        geb=DEF(geb)
        vorname=same
        geb=DEF(geb)
        p_strasse=DEF(p_strasse)
        p_plz=DEF(p_plz)
        p_ort=DEF(p_ort)
        p_lkz=DEF(p_lkz)
        bphone1=DEF(bphone1)
        bphone2=DEF(bphone2)
        mobile=DEF(mobile)
        carphone=DEF(carphone)
        otherfax=DEF(otherfax)
        bfax=DEF(bfax)
        companyphone=DEF(companyphone)
        web=DEF(web)
        firma=DEF(firma)
        strasse=DEF(strasse)
        ort=DEF(ort)
        plz=DEF(plz)
        email1=DEF(email1)
        email2=DEF(email2)
        email3=DEF(email3)
        info=DEF(info)';
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