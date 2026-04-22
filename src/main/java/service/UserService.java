package service;

import DAO.UserDAO;
import model.User;

public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean registerUser(String name, String email, String password, String type) {

        if (name == null || name.isBlank() ||
            email == null || email.isBlank() ||
            password == null || password.isBlank()) {
            return false;
        }

        if (userDAO.getUserByEmail(email) != null) {
            return false;
        }

        User user = UserFactory.createUser(type, name, email, password);
        return userDAO.registerUser(user);
    }

    public User login(String email, String password) {
        return userDAO.loginUser(email, password);
    }
}
