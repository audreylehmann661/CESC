/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import model.User;
import java.sql.*;
/**
 *
 * @author audre
 */

public class UserDAO {

    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean registerUser(User user) {
        try {
            String query = "INSERT INTO users(name, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User loginUser(String email, String password) {
        try {
            String query = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}