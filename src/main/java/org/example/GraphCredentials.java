package org.example;

import java.io.IOException;
import java.util.Properties;


/** TODO: delete info comment later
 * Immutable class for storing MSGraph API credentials
 *
 * This class is:
 * - immutable (final fields, no setters)
 * - final (cannot be subclassed)
 * - created via a factory method (fromOAuthProperties)
 *
 * Ensures thread-safety and clarity by preventing modification after creation
 */
public final class GraphCredentials {
    private final String _clientID;
    private final String _tenantID;
    private final String _scope;
    private final String _clientSecret;

    private GraphCredentials(String clientID, String tenantID, String scope, String clientSecret)
    {
        _clientID = clientID;
        _tenantID = tenantID;
        _scope = scope;
        _clientSecret = clientSecret;
    }

    public String getClientId() { return _clientID; }
    public String getTenantId() { return _tenantID; }
    public String getClientSecret() { return _clientSecret; }
    public String getScope() { return _scope; }

    public static GraphCredentials fromOAuthProperties() {
        final Properties oAuthProperties = new Properties();
        try
        {
            oAuthProperties.load(App.class.getResourceAsStream("/oAuth.properties"));
        }
        catch (IOException e)
        {
            System.out.println("Unable to read OAuth.properties configuration");
            return null;
        }

        String clientID = oAuthProperties.getProperty("app.clientId");
        String tenantID = oAuthProperties.getProperty("app.tenantId");
        String scope = oAuthProperties.getProperty("app.scope");
        String clientSecret = oAuthProperties.getProperty("app.clientSecret");

        if (clientID == null || tenantID == null || clientSecret == null) {
            throw new IllegalStateException("Environment variables for Graph credentials are missing");
        }
        return new GraphCredentials(clientID, tenantID, scope, clientSecret);
    }
}
