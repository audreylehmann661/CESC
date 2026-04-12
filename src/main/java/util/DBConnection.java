/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author audre
 */
public class DBConnection {
    
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/cesc_db";
            String user = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
