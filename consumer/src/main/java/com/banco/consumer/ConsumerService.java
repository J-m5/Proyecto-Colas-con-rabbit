package com.banco.consumer;

import com.rabbitmq.client.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;

public class ConsumerService {
    private static final List<String> COLAS = Arrays.asList(Config.getBancos());
    
    private final RabbitMQConnection rabbitConnection;
    private final ApiClient apiClient;
    private final ObjectMapper objectMapper;

    public ConsumerService() {
        this.rabbitConnection = new RabbitMQConnection();
        this.apiClient = new ApiClient();
        this.objectMapper = new ObjectMapper();
    }

    public void iniciarConsumidores() {
        try {
            Connection connection = rabbitConnection.getConnection();
            
            for (String nombreCola : COLAS) {
                Channel channel = connection.createChannel();
                channel.queueDeclare(nombreCola, true, false, false, null);
                channel.basicQos(1);
                
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String mensaje = new String(delivery.getBody(), "UTF-8");
                    System.out.println("Mensaje recibido: " + mensaje);
                    
                    try {
                        Transaction transaccion = objectMapper.readValue(mensaje, Transaction.class);
                        
                        System.out.println("Recibida de " + nombreCola + ": " + transaccion.getIdTransaccion());
                        
                        boolean guardado = apiClient.guardarTransaccion(transaccion);
                        
                        if (guardado) {
                            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                            System.out.println("Confirmada: " + transaccion.getIdTransaccion());
                        } else {
                            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                            System.out.println("Reintentando: " + transaccion.getIdTransaccion());
                        }
                        
                    } catch (Exception e) {
                        System.err.println("Error procesando mensaje de " + nombreCola + ": " + e.getMessage());
                        channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                    }
                };
                
                channel.basicConsume(nombreCola, false, deliverCallback, consumerTag -> {});
                System.out.println("Escuchando cola: " + nombreCola);
            }
            
            System.out.println("Todos los consumidores iniciados");
            
        } catch (Exception e) {
            System.err.println("Error iniciando consumidores: " + e.getMessage());
        }
    }
}
