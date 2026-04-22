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
import service.ReportService;
import util.DBConnection;
import model.User;
import model.report.CreditDebitReportRow;
import model.report.MonthlyPaymentReportRow;
import model.report.ScootersPerStationReportRow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty() || "dashboard".equals(action)) {
            loadDashboard(request, response);
            return;
        }

        response.sendRedirect("index.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        Connection conn = DBConnection.getConnection();
        try {
            AuthService authService = new AuthService(conn);

            if ("register".equals(action)) {

                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String role = request.getParameter("role");

                boolean success = authService.register(name, email, password, role);

                if (success) {
                    response.sendRedirect("login.jsp?registered=1");
                } else {
                    response.sendRedirect("register.jsp?error=1");
                }
            } else if ("login".equals(action)) {

                String email = request.getParameter("email");
                String password = request.getParameter("password");

                User user = authService.login(email, password);

                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);

                    response.sendRedirect("controller?action=dashboard");
                } else {
                    response.sendRedirect("login.jsp?error=1");
                }
            } else if ("logout".equals(action)) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect("login.jsp?logout=1");
            } else {
                response.sendRedirect("index.html");
            }
        } finally {
            DBConnection.closeQuietly(conn);
        }
    }

    private void loadDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Connection conn = DBConnection.getConnection();
        List<CreditDebitReportRow> creditsDebits = Collections.emptyList();
        List<MonthlyPaymentReportRow> monthlyPayments = Collections.emptyList();
        List<ScootersPerStationReportRow> scootersPerStation = Collections.emptyList();
        String dbError = null;

        try {
            if (conn != null) {
                ReportService reportService = new ReportService(conn);
                creditsDebits = reportService.getCreditsAndDebitsReport();
                monthlyPayments = reportService.getMonthlyPaymentsReport();
                scootersPerStation = reportService.getScootersPerStationReport();
            } else {
                dbError = "Database connection could not be established. Check the MySQL settings in DBConnection.";
            }
        } catch (Exception e) {
            dbError = "Unable to load report data: " + e.getMessage();
            e.printStackTrace();
        } finally {
            DBConnection.closeQuietly(conn);
        }

        request.setAttribute("creditsDebits", creditsDebits);
        request.setAttribute("monthlyPayments", monthlyPayments);
        request.setAttribute("scootersPerStation", scootersPerStation);
        request.setAttribute("dbError", dbError);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}


