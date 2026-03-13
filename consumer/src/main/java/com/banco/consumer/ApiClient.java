package com.banco.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private static final String API_URL =  "https://7e0d9ogwzd.execute-api.us-east-1.amazonaws.com/default/guardarTransacciones";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public boolean guardarTransaccion(Transaction transaccion) {
        try {
            String json = objectMapper.writeValueAsString(transaccion);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("   Guardada correctamente: " + transaccion.getIdTransaccion());
                return true;
            } else {
                System.err.println("   Error al guardar. Código: " + response.statusCode());
                return false;
            }
        } catch (Exception e) {
            System.err.println("   Error en POST: " + e.getMessage());
            return false;
        }
    }
}
