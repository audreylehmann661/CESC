package controller;

import business.UserService;
import dao.UserDAOImpl;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

@WebServlet("/registerUser")
public class RegisterUserServlet extends HttpServlet {

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cesc_db",
                "cst8288",
                "cst8288"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        try (Connection connection = getConnection()) {
            UserService userService = new UserService(new UserDAOImpl(connection));
            boolean success = userService.registerUser(name, email, password, userType);

            if (success) {
                response.sendRedirect("login.jsp");
            } else {
                response.getWriter().println("User registration failed.");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
