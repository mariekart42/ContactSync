package org.example;

import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class App {

    protected static final String USER = "mensing@kieback-peter.de"; // now for testing. TODO delete later

    public List<String> fetchContacts()
    {
        return List.of(
                "something", "hehe2"
        );
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
    }


    private static Map<String, String> getContactMetaData(ResultSet aboData) throws SQLException {
        Map<String, String> map = new HashMap<>();
        map.put("luid", aboData.getString("luid"));
        map.put("guid", aboData.getString("guid"));
        map.put("abostart", aboData.getString("abostart"));
        map.put("aboende", aboData.getString("aboende"));
        map.put("changed", aboData.getString("changed"));
        map.put("synced", aboData.getString("synced"));
        map.put("to_external", aboData.getString("to_external"));
        map.put("avoid", aboData.getString("avoidfields"));
        map.put("device", aboData.getString("device"));
        map.put("devicespecifics", aboData.getString("devicespecifics"));
        return map;
    }

    public void runRoutine() {
        try
        {
            // get data via sql
            ResultSet aboData = DBConnector.getContactData();

            while (aboData.next())
            {

                Map<String, String> contactMetaData = getContactMetaData(aboData);

                var status = AditoRequests.getContactStatus(contactMetaData);

                contactMetaData.put("guid", "5500"); // TODO delete this

                OutlookContactUpdater.updateContact(contactMetaData, status);
                break;// TODO and this
            }

            System.out.println();
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
