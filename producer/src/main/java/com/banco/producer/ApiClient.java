package com.banco.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiClient {
    private static final String API_URL = "https://hly784ig9d.execute-api.us-east-1.amazonaws.com/default/transacciones";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<Transaction> obtenerTransacciones() {
        try {
            System.out.println("Llamando a GET: " + API_URL);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Respuesta recibida correctamente");
                
                var jsonNode = objectMapper.readTree(response.body());
                var transaccionesNode = jsonNode.get("transacciones");
                
                return objectMapper.readValue(
                    transaccionesNode.toString(), 
                    new TypeReference<List<Transaction>>() {}
                );
            } else {
                System.err.println("Error HTTP: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error en la llamada GET: " + e.getMessage());
            return null;
        }
    }
}
