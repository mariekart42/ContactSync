package org.example;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.serviceclient.GraphServiceClient;

public class AppConfig {

    public  GraphServiceClient getGraphServiceClient() throws Exception {
        GraphCredentials credentials = GraphCredentials.fromOAuthProperties();
        if (credentials == null)
            throw new Exception("Unable to retrieve Access Token.");

        final String scopes = credentials.getScope();

        final ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                .clientId(credentials.getClientId())
                .tenantId(credentials.getTenantId())
                .clientSecret(credentials.getClientSecret())
                .build();

        if (null == scopes || null == credential) {
            throw new Exception("Unable to retrieve valid Credentials");
        }

        return new GraphServiceClient(credential, scopes);
    }
}
