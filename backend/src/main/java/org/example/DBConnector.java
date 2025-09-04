package org.example;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@Slf4j
public class DBConnector implements AutoCloseable
{
    private static DBConnector _instance;
    private Connection _connection;


    private DBConnector()
    {
        Properties props = loadProperties();
        _connection = createConnection(props);
    }


    // Loads database configuration from oAuth.properties
    private Properties loadProperties()
    {
        try (InputStream input = App.class.getResourceAsStream("/oAuth.properties"))
        {
            if (input == null)
                throw new RuntimeException("Could not find /oAuth.properties file");

            Properties props = new Properties();
            props.load(input);
            return props;
        }
        catch (IOException e)
        {
            log.error("\033[0;31mFailed to read oAuth.properties configuration\033[0m", e);
            throw new RuntimeException("Failed to read oAuth.properties configuration", e);
        }
    }


    // Creates a database connection using loaded properties
    private Connection createConnection(Properties props)
    {
        String url = props.getProperty("postgres.url");
        String username = props.getProperty("postgres.username");
        String password = props.getProperty("postgres.password");

        if (url == null || username == null || password == null)
            throw new IllegalStateException("Database configuration missing in oAuth.properties");

        try
        {
            return DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            log.error("\033[0;31mFailed to connect to database {} \033[0m", url, e);
            throw new RuntimeException("Failed to connect to database", e);
        }
    }


    private static DBConnector getInstance()
    {
        if (_instance == null)
        {
            synchronized (DBConnector.class)
            {
                if (_instance == null)
                    _instance = new DBConnector();
            }
        }
        return _instance;
    }


    Connection getConnection() {
        if (_connection == null)
            return _connection = getInstance().getConnection();
        return _connection;
    }


    private static ResultSet executeQuery(String query) throws SQLException
    {
        return getFromDB(query);
    }


    public static ResultSet getContactData() throws SQLException
    {
        String query = "select a.guid,a.syncabonnementid,a.luid,a.abostart,a.aboende,a.changed,a.synced,a.to_external,a.syncresult AS abo_syncresult,d.device,d.devicespecifics,d.avoidfields,p.syncresult AS principal_syncresult from syncabonnement a join syncprincipal p ON a.principal=p.syncprincipalid JOIN syncdevice d ON p.syncdevice_id=d.syncdeviceid";
        return getFromDB(query);
    }


    public static ResultSet getPrincipals() throws SQLException
    {
        String query = "SELECT pri.syncprincipalid, pri.syncuser_id, pri.syncdevice_id, pri.syncresult AS principal_syncresult FROM syncprincipal pri order by pri.syncprincipalid";
        return getFromDB(query);
    }


    public static ResultSet getContactDataFromPrincipalId(String principalId) throws SQLException
    {
        if (principalId == null || principalId.isBlank() || !isPositiveInteger(principalId))
            return null;

        String query = "select dev.syncdeviceid, dev.device, dev.devicespecifics, dev.avoidfields, pri.syncprincipalid, pri.syncdevice_id, pri.syncresult AS principal_syncresult, abo.syncabonnementid, abo.principal, abo.guid, abo.luid, abo.abostart, abo.aboende, abo.synced, abo.changed, abo.to_external, abo.syncresult AS abo_syncresult from syncprincipal pri join syncabonnement abo on abo.principal = pri.syncprincipalid join syncdevice dev on dev.syncdeviceid = pri.syncdevice_id where pri.syncprincipalid = '"+principalId+"'";

        return getFromDB(query);
    }


    private static void updateDB(String query) throws SQLException
    {
        try
        {
            Connection connection = getInstance().getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        }
        catch (SQLException e)
        {
            throw new SQLException("An error occurred while making UPDATE to Database with following Statement: " + query);
        }
    }


    private static ResultSet getFromDB(String query) throws SQLException
    {
        try
        {
            Connection connection = getInstance().getConnection();
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        }
        catch (SQLException e)
        {
            throw new SQLException("An error occurred while retrieving Data from Database with following Statement: " + query);
        }
    }




    // give function the literal of with field (columnName), the value to change and the syncabonnementid on which syncabonnement to change
    public static void updateSyncabonnementEntry(Map<String, String> contactMetaData, String columnName, String syncabonnementid, String value) throws SQLException, RuntimeException
    {
        String query = "UPDATE syncabonnement SET "+columnName+" = '"+value+"' WHERE syncabonnementid = '"+syncabonnementid+"'";
        if (columnName == null || columnName.isBlank())
            throw new IllegalArgumentException("Failed to execute UPDATE on: SYNCABONNEMENT because columnName was null or empty.");
        if (syncabonnementid == null || syncabonnementid.isBlank())
            throw new IllegalArgumentException("Failed to execute UPDATE on: SYNCABONNEMENT."+columnName+" because syncabonnementid was null or empty.");

        try
        {
            if (value == null || value.isBlank())
                query = "UPDATE syncabonnement SET "+columnName+" = null WHERE syncabonnementid = '"+syncabonnementid+"'";
            DBConnector.updateDB(query);

            if (columnName.equalsIgnoreCase(App.SYNCRESULT))
                contactMetaData.put(App.ABO_SYNCRESULT, value);
            else
                contactMetaData.put(columnName, value);
        }
        catch (SQLException e)
        {
            throw e;// TODO figure out if there is a better way
        }
        catch (RuntimeException  e)
        {
            throw new RuntimeException("An error occurred while making an UPDATE to Table SYNCABONNEMENT." + columnName + e);
        }
    }


    public static void updateSyncprincipalEntry(Map<String, String> contactMetaData, String columnName, String syncprincipalid, String value) throws SQLException, RuntimeException
    {
        String query = "UPDATE syncprincipal SET "+columnName+" = '"+value+"' WHERE syncprincipalid = '"+syncprincipalid+"'";
        if (columnName == null || columnName.isBlank())
            throw new IllegalArgumentException("Failed to execute UPDATE on: SYNCPRINCIPAL because columnName was null or empty.");
        if (syncprincipalid == null || syncprincipalid.isBlank())
            throw new IllegalArgumentException("Failed to execute UPDATE on: SYNCPRINCIPAL."+columnName+" because syncprincipalid was null or empty.");

        try
        {
            if (value == null || value.isBlank())
                query = "UPDATE syncprincipal SET "+columnName+" = null WHERE syncprincipalid = '"+syncprincipalid+"'";
            DBConnector.updateDB(query);

            if (columnName.equalsIgnoreCase(App.SYNCRESULT))
                contactMetaData.put(App.PRINCIPAL_SYNCRESULT, value);
            else
                contactMetaData.put(columnName, value);
        }
        catch (SQLException e)
        {
            throw new SQLException(e);
        }
        catch (RuntimeException  e)
        {
            throw new RuntimeException ("An error occurred while making an UPDATE to Table SYNCPRINCIPAL." + columnName + e);
        }
    }


    public static boolean isPositiveInteger(String s) {
        try {
            int n = Integer.parseInt(s);
            return n > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    // TODO: this will not work yet, later when we work with the real Adito DB we can actually
    //       call the function 'getContactMap()' but rn with postgres db, this function is not defined
    //       The function gives us the newest data of a user, this we can compare to the data saved in to_external
    public static String executeGetContactMapSQLFunction() throws SQLException
    {
        Connection connection = getInstance().getConnection();
        CallableStatement stmt = connection.prepareCall("{ ? = call sync.getContactMap(?, ?, ?) }");

        // Rückgabewert registrieren (VARCHAR2 => Types.VARCHAR)
        stmt.registerOutParameter(1, Types.VARCHAR);

        stmt.setInt(2, 76290);         // v_contactid
        stmt.setString(3, "");         // v_avoidfields
        stmt.setString(4, "10690");    // v_principal_pnr (ist varchar2!)

        stmt.execute();

        String result = stmt.getString(1);

        stmt.close();
        return result;
    }


    // gets all the newest information about a specific contact
    public static String executeMockGetContactMapSQLFunction(String guid, String avoidFields) throws SQLException
    {
        Connection connection = getInstance().getConnection();
        CallableStatement stmt = connection.prepareCall("{ ? = call get_latest_contact_properties(?) }");

        stmt.registerOutParameter(1, Types.VARCHAR);

        stmt.setString(2, guid);

        stmt.execute();

        String result = stmt.getString(1);

        stmt.close();
        return extractLinesToAvoid(result, avoidFields);
    }


    // function takes list of Adito data of a contact and extracts all lines defined in 'avoidFields'
    // syntax of avoidFields:
    //      '*bfax*, *mobile*, *geb*'
    //  or  ''  if nothing to be avoided
    // fields that cant be avoided: mapversion, name, vorname, firma, strasse, ort, plz
    private static String extractLinesToAvoid(String data, String avoidFields)
    {
        if (avoidFields == null || avoidFields.isEmpty())
            return data; // check if i should return data or something else

        String[] parts = avoidFields.split(",");
        List<String> toAvoid = new ArrayList<>();
        for (String part : parts)
        {
            String fieldName = part.replace("*", "").trim();
            if (!fieldName.isEmpty())
                toAvoid.add(fieldName);
        }

        List<String> alwaysInclude = List.of("mapversion", "name", "vorname", "firma", "strasse", "ort", "plz");

        StringBuilder result = new StringBuilder();
        String[] lines = data.split("\n");

        for (String line : lines)
        {
            if (line.contains("=")) {
                String key = line.substring(0, line.indexOf("=")).trim();

                if (toAvoid.contains(key) && !alwaysInclude.contains(key))
                    result.append("        ").append(key).append("=\n");
                else
                    result.append(line).append("\n");
            }
            else
                result.append(line).append("\n");
        }
        return result.toString();
    }


    void closeConnection()
    {
        try
        {
            if (_connection != null && !_connection.isClosed())
                _connection.close();
        }
        catch (SQLException e)
        {
            log.error("\033[0;31mAn error occurred while closing Database Connection: \033[0m", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void close() {
        closeConnection();
    }
}
