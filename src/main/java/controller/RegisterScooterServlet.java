package controller;

import business.ScooterService;
import dao.ScooterDAOImpl;
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

@WebServlet("/registerScooter")
public class RegisterScooterServlet extends HttpServlet {

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

        String make = request.getParameter("make");
        String model = request.getParameter("model");
        String color = request.getParameter("color");
        String vehicleNumber = request.getParameter("vehicleNumber");
        int batteryCapacity = Integer.parseInt(request.getParameter("batteryCapacity"));

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.getWriter().println("Please log in first.");
            return;
        }

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (!"Sponsor".equalsIgnoreCase(loggedUser.getUserType())) {
            response.getWriter().println("Only sponsors can register scooters.");
            return;
        }

        try (Connection connection = getConnection()) {
            ScooterService scooterService = new ScooterService(new ScooterDAOImpl(connection));
            boolean success = scooterService.registerScooter(
                    make, model, color, vehicleNumber, batteryCapacity, loggedUser.getUserId()
            );

            if (success) {
                response.sendRedirect("index.jsp");
            } else {
                response.getWriter().println("Scooter registration failed.");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
