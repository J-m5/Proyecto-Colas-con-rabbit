package com.banco.consumer;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnection {
    private static final String RABBITMQ_HOST = "localhost";
    private final ConnectionFactory factory;

    public RabbitMQConnection() {
        factory = new ConnectionFactory();
        factory.setHost(RABBITMQ_HOST);
    }

    public Connection getConnection() {
        try {
            return factory.newConnection();
        } catch (Exception e) {
            throw new RuntimeException("Error conectando a RabbitMQ", e);
        }
    }
}
