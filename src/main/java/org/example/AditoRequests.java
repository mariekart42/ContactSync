package org.example;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public class AditoRequests {

    public enum CONTACT_STATUS
    {
        TO_CREATE, TO_CHANGE, TO_DELETE, UNCHANGED
    }

    public static void printAllYouGot()
    {
        try
        {
            System.out.println();
            System.out.println(" ------- ALL TABLES ---------------------------------------------");
            ResultSet resultSet = DBConnector.executeQuery("SELECT * FROM SYNCUSER");
            int i = 1;
            System.out.println("# #  SYNCUSER  # #");
            while (resultSet.next())
            {
                System.out.println("[" + i++ + "]");
                System.out.println("SYNCUSER.syncuserid\t:\t" + resultSet.getString("syncuserid"));
                System.out.println("SYNCUSER.username\t:\t" + resultSet.getString("username"));
                System.out.println("SYNCUSER.aditouser\t:\t" + resultSet.getString("aditouser"));
                System.out.println();
            }
            System.out.println("------------------------------------------------------------------");
            resultSet = DBConnector.executeQuery("SELECT * FROM SYNCDEVICE");
            i = 1;
            System.out.println("# #  SYNCDEVICE  # #");
            while (resultSet.next())
            {
                System.out.println("[" + i++ + "]");
                System.out.println("SYNCDEVICE.syncdeviceid\t\t:\t" + resultSet.getString("syncdeviceid"));
                System.out.println("SYNCDEVICE.device\t\t\t:\t" + resultSet.getString("device"));
                System.out.println("SYNCDEVICE.description\t\t:\t" + resultSet.getString("description"));
                System.out.println("SYNCDEVICE.tzid\t\t\t\t:\t" + resultSet.getString("tzid"));
                System.out.println("SYNCDEVICE.devicespecifics\t:\t" + resultSet.getString("devicespecifics"));
                System.out.println("SYNCDEVICE.avoidfields\t\t:\t" + resultSet.getString("avoidfields"));
                System.out.println();
            }
            System.out.println("------------------------------------------------------------------");
            resultSet = DBConnector.executeQuery("SELECT * FROM SYNCPRINCIPAL");
            i = 1;
            System.out.println("# #  SYNCPRINCIPAL  # #");
            while (resultSet.next())
            {
                System.out.println("[" + i++ + "]");
                System.out.println("SYNCPRINCIPAL.syncprincipalid\t:\t" + resultSet.getString("syncprincipalid"));
                System.out.println("SYNCPRINCIPAL.syncuser_id\t\t:\t" + resultSet.getString("syncuser_id"));
                System.out.println("SYNCPRINCIPAL.syncdevice_id\t\t:\t" + resultSet.getString("syncdevice_id"));
                System.out.println("SYNCPRINCIPAL.syncresult\t\t:\t" + resultSet.getString("syncresult"));
                System.out.println();
            }
            System.out.println("------------------------------------------------------------------");
            resultSet = DBConnector.executeQuery("SELECT * FROM SYNCABONNEMENT");
            i = 1;
            System.out.println("# #  SYNCABONNEMENT  # #");
            while (resultSet.next())
            {
                System.out.println("[" + i++ + "]");
                System.out.println("SYNCABONNEMENT.syncabonnementid\t:\t" + resultSet.getString("syncabonnementid"));
                System.out.println("SYNCABONNEMENT.principal\t\t:\t" + resultSet.getString("principal"));
                System.out.println("SYNCABONNEMENT.dbname\t\t\t:\t" + resultSet.getString("dbname"));
                System.out.println("SYNCABONNEMENT.guid\t\t\t\t:\t" + resultSet.getString("guid"));
                System.out.println("SYNCABONNEMENT.luid\t\t\t\t:\t" + resultSet.getString("luid"));
                System.out.println("SYNCABONNEMENT.abostart\t\t\t:\t" + resultSet.getString("abostart"));
                System.out.println("SYNCABONNEMENT.aboende\t\t\t:\t" + resultSet.getString("aboende"));
                System.out.println("SYNCABONNEMENT.changed\t\t\t:\t" + resultSet.getString("changed"));
                System.out.println("SYNCABONNEMENT.synced\t\t\t:\t" + resultSet.getString("synced"));
                System.out.println("SYNCABONNEMENT.to_external\t\t:\t" + resultSet.getString("to_external"));
                System.out.println("SYNCABONNEMENT.from_external\t:\t" + resultSet.getString("from_external"));
                System.out.println("SYNCABONNEMENT.syncresult\t\t:\t" + resultSet.getString("syncresult"));
                System.out.println();
            }
            System.out.println("------------------------------------------------------------------");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    public static String getResultFromMockSQLFunction(String guid, String avoidFields) throws SQLException {
        return DBConnector.executeMockGetContactMapSQLFunction(guid, avoidFields);
    }
}
