package org.example;

import java.sql.*;

public class DBConnector implements AutoCloseable{
    private static DBConnector instance;
    private Connection connection;

    private DBConnector()
    {
        try
        {
            String url = "";
            String username = "";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
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
