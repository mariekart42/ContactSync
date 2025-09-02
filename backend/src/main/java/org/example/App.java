package org.example;

import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class App {

    protected static final String USER = "mensing@kieback-peter.de"; // now for testing. TODO delete later

//    public List<String> fetchContacts()
//    {
//        return List.of(
//                "something", "grr"
//        );
//
//        try
//        {
//            // get data via sql
//            ResultSet aboData = DBConnector.getContactData();
//
//            while (aboData.next())
//            {
//
//                Map<String, String> contactMetaData = getContactMetaData(aboData);
//
//                var status = AditoRequests.getContactStatus(contactMetaData);
//
//                contactMetaData.put("guid", "5500"); // TODO delete this
//
//                OutlookContactUpdater.updateContact(contactMetaData, status);
//                break;// TODO and this
//            }
//
//            System.out.println();
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//            e.printStackTrace();
//        }
//    }


    private static Map<String, String> getContactMetaData(ResultSet aboData) throws SQLException {
        Map<String, String> map = new HashMap<>();
        map.put("syncabonnementid", aboData.getString("syncabonnementid"));
        map.put("luid", aboData.getString("luid"));
        map.put("guid", aboData.getString("guid"));
        map.put("abostart", aboData.getString("abostart"));
        map.put("aboende", aboData.getString("aboende"));
        map.put("changed", aboData.getString("changed"));
        map.put("synced", aboData.getString("synced"));
        map.put("to_external", aboData.getString("to_external"));
        map.put("avoidfields", aboData.getString("avoidfields"));
        map.put("device", aboData.getString("device"));
        map.put("abo_syncresult", aboData.getString("abo_syncresult"));
        map.put("principal_syncresult", aboData.getString("principal_syncresult"));
        map.put("devicespecifics", aboData.getString("devicespecifics"));
        return map;
    }
//
//    public void runRoutine() {
//        try
//        {
//            // get data via sql
//            ResultSet aboData = DBConnector.getContactData();
//
//            while (aboData.next())
//            {
//
//                Map<String, String> contactMetaData = getContactMetaData(aboData);
//
//                var status = AditoRequests.getContactStatus(contactMetaData);
//
//                contactMetaData.put("guid", "5500"); // TODO delete this
//
//                OutlookContactUpdater.updateContact(contactMetaData, status);
//                break;// TODO and this
//            }
//
//            System.out.println();
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//            e.printStackTrace();
//        }
//    }


//    public void syncPrincipal() {
//        try
//        {
//            // get data via sql
//            ResultSet aboData = DBConnector.getContactData();
//
//            while (aboData.next())
//            {
//
//                Map<String, String> contactMetaData = getContactMetaData(aboData);
//
//                var status = AditoRequests.getContactStatus(contactMetaData);
//
//                contactMetaData.put("guid", "5500"); // TODO delete this
//
//                OutlookContactUpdater.updateContact(contactMetaData, status);
//                break;// TODO and this
//            }
//
//            System.out.println();
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//            e.printStackTrace();
//        }
//    }

    public List<Map<String, String>> getContactDataFromPrincipalId(int msg) {
        try
        {
            System.out.println("in getContactDataFromPrincipalId");
            ResultSet principalData = DBConnector.getContactDataFromPrincipalId(String.valueOf(msg));
            if (principalData == null)
                return null;

            List<Map<String, String>> lol = new ArrayList<>();

            while (principalData.next())
            {
                Map<String, String> contactMetaData = getContactMetaData(principalData);


                var status = AditoRequests.getContactStatus(contactMetaData);
                OutlookContactUpdater.updateContact(contactMetaData, status);


                contactMetaData.put("principal", principalData.getString("syncprincipalid"));
                replaceEmptyWithNull(contactMetaData); // so we can show null in frontend
                lol.add(contactMetaData);
                System.out.println(lol);
            }
            return lol;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private void replaceEmptyWithNull(Map<String, String> contactMetaData) {
        for (var lel : contactMetaData.keySet())
        {
            if (contactMetaData.get(lel) == null || contactMetaData.get(lel).isBlank())
                contactMetaData.put(lel, "[NULL]");
        }

//        if (contactMetaData.get("avoidfields") == null || contactMetaData.get("avoidfields").isBlank())
//            contactMetaData.put("avoidfields", "[NULL]");

//
//        map.put("luid", aboData.getString("luid"));
//        map.put("guid", aboData.getString("guid"));
//        map.put("abostart", aboData.getString("abostart"));
//        map.put("aboende", aboData.getString("aboende"));
//        map.put("changed", aboData.getString("changed"));
//        map.put("synced", aboData.getString("synced"));
//        map.put("to_external", aboData.getString("to_external"));
//        map.put("avoidfields", aboData.getString("avoidfields"));
//        map.put("device", aboData.getString("device"));
//        map.put("abo_syncresult", aboData.getString("abo_syncresult"));
//        map.put("principal_syncresult", aboData.getString("principal_syncresult"));
//        map.put("devicespecifics", aboData.getString("devicespecifics"));
    }


    public List<Map<String, String>> getPrincipals() {
        try
        {
            ResultSet principalData = DBConnector.getPrincipals();
            List<Map<String, String>> lol = new ArrayList<>();

            while (principalData.next())
            {
                Map<String, String> principal = new HashMap<>();
                principal.put("syncprincipalid" ,principalData.getString("syncprincipalid"));
                principal.put("syncuser_id" ,principalData.getString("syncuser_id"));
                principal.put("syncdevice_id" ,principalData.getString("syncdevice_id"));
                principal.put("principal_syncresult" ,principalData.getString("principal_syncresult"));

                lol.add(principal);
            }
            return lol;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
