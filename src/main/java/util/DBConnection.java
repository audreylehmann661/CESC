/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author audre
 */
public class DBConnection {

    private static final Properties PROPERTIES = loadProperties();
    
    public static Connection getConnection() {
        try {
            String url = getSetting("CESC_DB_URL", "db.url", "jdbc:mysql://localhost:3306/cesc_db?useSSL=false&serverTimezone=America/Toronto");
            String user = getSetting("CESC_DB_USER", "db.user", "cst8288");
            String password = getSetting("CESC_DB_PASSWORD", "db.password", "cst8288");

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getSetting(String envKey, String propertyKey, String defaultValue) {
        String value = System.getenv(envKey);
        if (value == null || value.trim().isEmpty()) {
            value = System.getProperty(envKey);
        }
        if ((value == null || value.trim().isEmpty()) && PROPERTIES != null) {
            value = PROPERTIES.getProperty(propertyKey);
        }
        return (value == null || value.trim().isEmpty()) ? defaultValue : value;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static String getSetting(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.trim().isEmpty()) {
            value = System.getProperty(key);
        }
        return (value == null || value.trim().isEmpty()) ? defaultValue : value;
    }
}
