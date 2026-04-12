/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author audre
 */

import service.AuthService;
import util.DBConnection;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        Connection conn = DBConnection.getConnection();
        AuthService authService = new AuthService(conn);

        if (action.equals("register")) {

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            boolean success = authService.register(name, email, password, role);

            if (success) {
                response.sendRedirect("login.jsp");
            } else {
                response.sendRedirect("register.jsp?error=1");
            }
        }

        else if (action.equals("login")) {

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = authService.login(email, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                response.sendRedirect("dashboard.jsp");
            } else {
                response.sendRedirect("login.jsp?error=1");
            }
        }
    }
}


