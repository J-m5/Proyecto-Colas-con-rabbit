package com.banco.producer;

import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        
        ApiClient apiClient = new ApiClient();
        ProducerService producerService = new ProducerService();
        
        System.out.println(" Obteniendo transacciones del API GET...");
        List<Transaction> transacciones = apiClient.obtenerTransacciones();
        
        if (transacciones != null && !transacciones.isEmpty()) {
            System.out.println(" Transacciones obtenidas exitosamente");
            System.out.println("   Total transacciones: " + transacciones.size());
            
            System.out.println(" Enviando a RabbitMQ...");
            
            int contador = 0;
            for (Transaction transaccion : transacciones) {
                String uuid = UUID.randomUUID().toString();
                transaccion.setIdTransaccion("JoseMario-" + uuid);
                
                transaccion.setNombre("Jose Rosales");
                transaccion.setCarnet("0905-24-17488");
                
                producerService.enviarTransaccion(transaccion);
                contador++;
                
                try { Thread.sleep(100); } catch (Exception e) {}
            }
            
            System.out.println("\nRESUMEN: " + contador + " transacciones enviadas a RabbitMQ");
            
        } else {
            System.err.println("\nERROR: No se pudieron obtener transacciones");
        }
        
    }
}
