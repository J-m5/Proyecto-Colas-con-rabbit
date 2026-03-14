package com.banco.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProducerService {
    private static final String RABBITMQ_HOST = "localhost";
    private final ConnectionFactory factory;
    private final ObjectMapper objectMapper;

    public ProducerService() {
        factory = new ConnectionFactory();
        factory.setHost(RABBITMQ_HOST);
        this.objectMapper = new ObjectMapper();
    }

    public void enviarTransaccion(Transaction transaccion, String colaDestino) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            
            channel.queueDeclare(colaDestino, true, false, false, null);
            String mensajeJson = objectMapper.writeValueAsString(transaccion);
            channel.basicPublish("", colaDestino, null, mensajeJson.getBytes());
            
            System.out.println(" Enviada a " + colaDestino + ": " + transaccion.getIdTransaccion());
            
        } catch (Exception e) {
            System.err.println("Error enviando transacción " + transaccion.getIdTransaccion() + 
                             " a " + colaDestino + ": " + e.getMessage());
        }
    }
}
