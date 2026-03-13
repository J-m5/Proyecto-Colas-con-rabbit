package com.banco.consumer;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();
    
    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (Exception e) {
            System.err.println("Error cargando config.properties: " + e.getMessage());
        }
    }
    
    public static String[] getBancos() {
        String colas = props.getProperty("bancos.colas", "BAC,BANRURAL,BI,GYT");
        return colas.split(",");
    }
}
