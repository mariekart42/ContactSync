package org.example;

import java.net.URI;
import java.net.http.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class App {

    private static final String USER = "mensing@kieback-peter.de";

    public static void main(String[] args) throws Exception {
        String token = getAccessToken();
        sendGraphRequest(token);
    }

    public static String getAccessToken() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        GraphCredentials credentials = GraphCredentials.fromOAuthProperties();
        if (credentials == null)
            throw new Exception("Unable to retrieve Access Token.");

        String body = buildFormBody(Map.of(
                "client_id", credentials.getClientId(),
                "scope", credentials.getScope(),
                "client_secret", credentials.getClientSecret(),
                "grant_type", "client_credentials"
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://login.microsoftonline.com/" + credentials.getTenantId() + "/oauth2/v2.0/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String responseBody = response.body();
            // Token extrahieren
            String accessToken = responseBody.split("\"access_token\":\"")[1].split("\"")[0];
            return accessToken;
        } else {
            throw new RuntimeException("Token request failed: " + response.body());
        }
    }

    public static void sendGraphRequest(String token) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://graph.microsoft.com/v1.0/users/" + USER + "/contacts"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Graph API response:\n" + response.body());
    }

    private static String buildFormBody(Map<String, String> data) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (result.length() > 0) result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return result.toString();
    }
}