package controller;

import business.UserService;
import dao.UserDAOImpl;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection connection = getConnection()) {
            UserService userService = new UserService(new UserDAOImpl(connection));
            User user = userService.loginUser(email, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", user);
                response.sendRedirect("index.jsp");
            } else {
                response.getWriter().println("Invalid login.");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
