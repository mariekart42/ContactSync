package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class App {

    public static final String SYNCPRINCIPALID = "syncprincipalid";
    public static final String SYNCDEVICE_ID = "syncdevice_id";
    public static final String SYNCUSERID = "syncuser_id";
    public static final String PRINCIPAL_SYNCRESULT = "principal_syncresult";
    public static final String SYNCRESULT = "syncresult";
    public static final String ABO_SYNCRESULT = "abo_syncresult";
    public static final String SYNCABONNEMENTID = "syncabonnementid";
    public static final String LUID = "luid";
    public static final String GUID = "guid";
    public static final String ABOSTART = "abostart";
    public static final String ABOENDE = "aboende";
    public static final String CHANGED = "changed";
    public static final String SYNCED = "synced";
    public static final String TO_EXTERNAL = "to_external";
    public static final String AVOIDFIELDS = "avoidfields";
    public static final String DEVICE = "device";
    public static final String DEVICESPECIFICS = "devicespecifics";

    public static final String ADITO_CONTACTS_FOLDERNAME = "AditoKontakte";


    protected static final String USER = "mensing@kieback-peter.de"; // now for testing. TODO delete later

    public static void syncContacts()
    {
        try
        {
            // TODO change to get all principals and iterate through them
            ResultSet aboData = DBConnector.getContactData();

            while (aboData.next())
            {
                Map<String, String> contactMetaData = getContactMetaData(aboData);
                OutlookContactUpdater.updateContact(contactMetaData);// TODO check contactMetaData for null or change try-catch structure here
            }
        }
        catch (SQLException e)
        {
            log.error("\033[0;31mAn SQLException error occurred while retrieving Contact Data from Database: \033[0m", e);
        }
        catch (Exception e)
        {
            log.error("\033[0;31mAn error occurred while updating Contacts: \033[0m", e);
        }
    }


    public List<Map<String, String>> getContactDataFromPrincipalId(int msg)
    {
        try
        {
            ResultSet principalData = DBConnector.getContactDataFromPrincipalId(String.valueOf(msg));
            if (principalData == null)
                return null;

            List<Map<String, String>> contactData = new ArrayList<>();

            while (principalData.next())
            {
                Map<String, String> contactMetaData = getContactMetaData(principalData);

                OutlookContactUpdater.updateContact(contactMetaData);// TODO check contactMetaData for null or change try-catch structure here

                if (contactMetaData.get(PRINCIPAL_SYNCRESULT) == null || contactMetaData.get(PRINCIPAL_SYNCRESULT).isBlank())
                    DBConnector.updateSyncprincipalEntry(contactMetaData, SYNCRESULT, principalData.getString(SYNCPRINCIPALID), "ok");
                if (contactMetaData.get(ABO_SYNCRESULT) == null || contactMetaData.get(ABO_SYNCRESULT).isBlank())
                    DBConnector.updateSyncabonnementEntry(contactMetaData, SYNCRESULT, principalData.getString(SYNCABONNEMENTID), "ok");

                replaceEmptyWithNull(contactMetaData); // so we can show null in frontend
                contactData.add(contactMetaData);
            }
            return contactData;
        }
        catch (SQLException e)
        {
            log.error("\033[0;31mAn SQLException error occurred while retrieving Contact Data from Database:\033[0m ", e);
        }
        catch (Exception e)
        {
            log.error("\033[0;31mAn error occurred while synchronising Contacts:\033[0m ", e);
        }
        return null;
    }


    private void replaceEmptyWithNull(Map<String, String> contactMetaData)
    {
        for (var lel : contactMetaData.keySet())
        {
            if (contactMetaData.get(lel) == null || contactMetaData.get(lel).isBlank())
                contactMetaData.put(lel, "[NULL]");
        }
    }


    public List<Map<String, String>> getPrincipals()
    {
        try
        {
            ResultSet principalData = DBConnector.getPrincipals();
            List<Map<String, String>> principals = new ArrayList<>();

            while (principalData.next())
            {
                Map<String, String> principal = new HashMap<>();
                principal.put(SYNCPRINCIPALID ,principalData.getString(SYNCPRINCIPALID));
                principal.put(SYNCUSERID ,principalData.getString(SYNCUSERID));
                principal.put(SYNCDEVICE_ID ,principalData.getString(SYNCDEVICE_ID));
                principal.put(PRINCIPAL_SYNCRESULT ,principalData.getString(PRINCIPAL_SYNCRESULT));
                principals.add(principal);
            }
            return principals;
        }
        catch (Exception e)
        {
            log.error("\033[0;31mAn error occurred while retrieving Principal Data from ResultSet:\033[0m ", e);
        }
        return null;
    }


    private static Map<String, String> getContactMetaData(ResultSet aboData) {
        try
        {
            Map<String, String> map = new HashMap<>();
            map.put(SYNCABONNEMENTID, aboData.getString(SYNCABONNEMENTID));
            map.put(LUID, aboData.getString(LUID));
            map.put(GUID, aboData.getString(GUID));
            map.put(ABOSTART, aboData.getString(ABOSTART));
            map.put(ABOENDE, aboData.getString(ABOENDE));
            map.put(CHANGED, aboData.getString(CHANGED));
            map.put(SYNCED, aboData.getString(SYNCED));
            map.put(TO_EXTERNAL, aboData.getString(TO_EXTERNAL));
            map.put(AVOIDFIELDS, aboData.getString(AVOIDFIELDS));
            map.put(DEVICE, aboData.getString(DEVICE));
            map.put(ABO_SYNCRESULT, null);
            map.put(PRINCIPAL_SYNCRESULT, null);
            map.put(DEVICESPECIFICS, aboData.getString(DEVICESPECIFICS));
            map.put(SYNCPRINCIPALID, aboData.getString(SYNCPRINCIPALID));
            return map;
        }
        catch (Exception e)
        {
            log.error("\033[0;31mAn error occurred while extracting Data from Database ResultSet:\033[0m ", e);
        }
        return null;
    }
}
