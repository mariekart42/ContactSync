package org.example;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;


public class AditoRequests {

    public enum CONTACT_STATUS
    {
        TO_CREATE, TO_CHANGE, TO_DELETE, UNCHANGED
    }

    // with the information of abostart, aboende, changed and synced,
    // function determinants and returns status of the contact:
    // TO_CHANGE, TO_CREATE, TO_DELETE or UNCHANGED
    //
     // contact Map structure:
    // | luid | abostart | aboende | changed | synced | to_external |
    public static CONTACT_STATUS getContactStatus(Map<String, String> contact)
    {
        try
        {
            Date abostart = contact.get("abostart") != null ? Date.valueOf(contact.get("abostart")) : null;
            Date aboende = contact.get("aboende") != null ? Date.valueOf(contact.get("aboende")) : null;
            Date changed = contact.get("changed") != null ? Date.valueOf(contact.get("changed")) : null;
            Date synced  = contact.get("synced") != null ? Date.valueOf(contact.get("synced")) : null;

            // CHANGED: SYNCABONNEMENT.aboende` is NULL && `SYNCABONNEMENT.changed` > `SYNCABONNEMENT.synced`
            if (aboende == null && changed != null && changed.after(synced))
                return CONTACT_STATUS.TO_CHANGE;

            // NEW: `SYNCABONNEMENT.abostart` is NULL && `SYNCABONNEMENT.synced` is NULL
            else if (abostart == null && synced == null)
                return CONTACT_STATUS.TO_CREATE;

            // DELETED: `SYNCABONNEMENT.aboende` is NOT NULL && `SYNCABONNEMENT.aboende` > NVL(`SYNCABONNEMENT.synced`, `SYNCABONNEMENT.aboende`-1)
            else if (aboende != null && (synced == null || aboende.after(synced)))
                return CONTACT_STATUS.TO_DELETE;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return CONTACT_STATUS.UNCHANGED;
    }


    // Package-Funktion `sync.getContactMap(v_contactid number, v_avoidfields varchar2, v_principal_pnr varchar2) return varchar2`
    public static void printResultFromSQLFunction() throws SQLException {
        System.out.println(DBConnector.executeGetContactMapSQLFunction());
    }


    // similar to printResultFromSQLFunction() but uses my mock function
    // `get_latest_contact_properties(username varchar2) return varchar2`
    // avoid fields functionality is included afterward as a Java function
    public static String getResultFromMockSQLFunction(Map<String, String> contactMetaData) throws SQLException {
        return DBConnector.executeMockGetContactMapSQLFunction(contactMetaData.get("guid"), contactMetaData.get("avoid"));
    }
}
