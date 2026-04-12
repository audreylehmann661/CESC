/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import DAO.UserDAO;
import model.User;
import java.sql.Connection;

/**
 *
 * @author audre
 */

public class AuthService {

    private UserDAO userDAO;

    public AuthService(Connection conn) {
        this.userDAO = new UserDAO(conn);
    }

    public boolean register(String name, String email, String password, String role) {
        User user = new User(name, email, password, role);
        return userDAO.registerUser(user);
    }

    public User login(String email, String password) {
        return userDAO.loginUser(email, password);
    }
}
