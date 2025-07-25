package org.example;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AditoRequests {
    //private static DBConnector _dbConnector = DBConnector.getInstance();

    public static List<String> getSomething()
    {
        List<String> result = new ArrayList<>();
        try
        {
            ResultSet resultSet = DBConnector.executeQuery("SELECT * FROM SYNCUSER");
            while (resultSet.next())
            {
                result.add(resultSet.getString("username"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
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


    public static ResultSet getFKoch() throws SQLException
    {
        ResultSet resultSet = DBConnector.executeQuery("select * from syncuser u, syncprincipal pr, syncdevice sd, syncabonnement abo \n" +
                "where u.syncuserid = pr.syncuser_id and pr.syncdevice_id  = sd.syncdeviceid and pr.syncprincipalid =abo.principal\n" +
                "and u.aditouser = 'fkoch'");
//        int i = 1;
//        while (resultSet.next())
//        {
//            System.out.println("[" + i++ + "]");
//            System.out.println("SYNCABONNEMENT.syncabonnementid\t:\t" + resultSet.getString("syncabonnementid"));
//            System.out.println("SYNCABONNEMENT.principal\t\t:\t" + resultSet.getString("principal"));
//            System.out.println("SYNCABONNEMENT.dbname\t\t\t:\t" + resultSet.getString("dbname"));
//            System.out.println("SYNCABONNEMENT.guid\t\t\t\t:\t" + resultSet.getString("guid"));
//            System.out.println("SYNCABONNEMENT.luid\t\t\t\t:\t" + resultSet.getString("luid"));
//            System.out.println("SYNCABONNEMENT.abostart\t\t\t:\t" + resultSet.getString("abostart"));
//            System.out.println("SYNCABONNEMENT.aboende\t\t\t:\t" + resultSet.getString("aboende"));
//            System.out.println("SYNCABONNEMENT.changed\t\t\t:\t" + resultSet.getString("changed"));
//            System.out.println("SYNCABONNEMENT.synced\t\t\t:\t" + resultSet.getString("synced"));
//            System.out.println("SYNCABONNEMENT.to_external\t\t:\t" + resultSet.getString("to_external"));
//            System.out.println("SYNCABONNEMENT.from_external\t:\t" + resultSet.getString("from_external"));
//            System.out.println("SYNCABONNEMENT.syncresult\t\t:\t" + resultSet.getString("syncresult"));
//            System.out.println();
//        }
        return resultSet;
    }

    // Package-Funktion `sync.getContactMap(v_contactid number, v_avoidfields varchar2, v_principal_pnr varchar2) return varchar2`
    public static void printResultFromSQLFunction() throws SQLException {
        System.out.println(DBConnector.executeGetContactMapSQLFunction());
    }

    // similar to printResultFromSQLFunction() but uses my mock function
    // `get_latest_contact_properties(username varchar2) return varchar2`
    // avoid fields functionality is included afterward as a Java function
    public static String getResultFromMockSQLFunction(String username, String avoidFields) throws SQLException {
        return DBConnector.executeMockGetContactMapSQLFunction(username, avoidFields);
    }
}
