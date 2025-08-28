package org.example;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class DBConnector implements AutoCloseable
{
    private static DBConnector instance;
    private Connection connection;


    private DBConnector()
    {
        try
        {
            final Properties oAuthProperties = new Properties();
            oAuthProperties.load(App.class.getResourceAsStream("/oAuth.properties"));
            String url = oAuthProperties.getProperty("postgres.url");
            String username = oAuthProperties.getProperty("postgres.username");
            String password = oAuthProperties.getProperty("postgres.password");
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Unable to read OAuth.properties configuration");
            throw new RuntimeException(e);
        }
    }


    private static DBConnector getInstance()
    {
        if (instance == null)
        {
            synchronized (DBConnector.class)
            {
                if (instance == null)
                    instance = new DBConnector();
            }
        }
        return instance;
    }


    Connection getConnection() {
        if (connection == null)
            return connection = getInstance().getConnection();
        return connection;
    }


    public static ResultSet executeQuery(String query) throws SQLException
    {
        Connection connection = getInstance().getConnection();
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
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
        // Prepare the list of fields to avoid (e.g., mobile, bfax)
        if (avoidFields == null || avoidFields.isEmpty())
            return data; // check if i should return data or something else

        String[] parts = avoidFields.split(",");
        List<String> toAvoid = new ArrayList<>();
        for (String part : parts)
        {
            // Remove asterisks (*) from field names
            String fieldName = part.replace("*", "").trim();
            if (!fieldName.isEmpty())
                toAvoid.add(fieldName);
        }

        // Fields that should always be included
        List<String> alwaysInclude = List.of("mapversion", "name", "vorname", "firma", "strasse", "ort", "plz");

        // New list to hold the lines that should be kept
        StringBuilder result = new StringBuilder();
        String[] lines = data.split("\n");

        for (String line : lines)
        {
            // Each line looks like: "key=value"
            if (line.contains("=")) {
                String key = line.substring(0, line.indexOf("=")).trim();

                // If the key is in the avoid list and it's not in "always include", set value to an empty string
                if (toAvoid.contains(key) && !alwaysInclude.contains(key))
                    result.append("        ").append(key).append("=\n"); // Keep key, erase value
                else
                    result.append(line).append("\n");
            }
            else
                result.append(line).append("\n"); // If there's no "=" in the line, just keep it (for cases like multiline strings)
        }
        return result.toString();
    }


    void closeConnection()
    {
        try
        {
            if (connection != null && !connection.isClosed())
                connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        closeConnection();
    }
}
