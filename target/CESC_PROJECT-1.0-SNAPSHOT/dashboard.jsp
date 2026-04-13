<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.User" %>
<%@ page import="model.report.CreditDebitReportRow" %>
<%@ page import="model.report.MonthlyPaymentReportRow" %>
<%@ page import="model.report.ScootersPerStationReportRow" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<CreditDebitReportRow> creditsDebits = (List<CreditDebitReportRow>) request.getAttribute("creditsDebits");
    List<MonthlyPaymentReportRow> monthlyPayments = (List<MonthlyPaymentReportRow>) request.getAttribute("monthlyPayments");
    List<ScootersPerStationReportRow> scootersPerStation = (List<ScootersPerStationReportRow>) request.getAttribute("scootersPerStation");
    String dbError = (String) request.getAttribute("dbError");

    DecimalFormat money = new DecimalFormat("$#,##0.00");
    SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    BigDecimal totalCredits = BigDecimal.ZERO;
    BigDecimal totalDebits = BigDecimal.ZERO;
    if (creditsDebits != null) {
        for (CreditDebitReportRow row : creditsDebits) {
            if ("CREDIT".equalsIgnoreCase(row.getTransactionType())) {
                totalCredits = totalCredits.add(row.getAmount());
            } else if ("DEBIT".equalsIgnoreCase(row.getTransactionType())) {
                totalDebits = totalDebits.add(row.getAmount());
            }
        }
    }

    BigDecimal totalAmountDue = BigDecimal.ZERO;
    if (monthlyPayments != null) {
        for (MonthlyPaymentReportRow row : monthlyPayments) {
            totalAmountDue = totalAmountDue.add(row.getAmountDue());
        }
    }

    int totalScooters = 0;
    if (scootersPerStation != null) {
        for (ScootersPerStationReportRow row : scootersPerStation) {
            totalScooters += row.getScooterCount();
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>CESC Dashboard</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body class="dashboard-page">
<main class="dashboard-shell">
    <section class="topbar">
        <div>
            <p class="eyebrow">Operational Analytics</p>
            <h1>CESC Reporting Dashboard</h1>
            <p class="hero-copy">Signed in as <strong><%= user.getName() %></strong> (<%= user.getRole() %>)</p>
        </div>
        <form action="controller" method="post">
            <input type="hidden" name="action" value="logout">
            <button type="submit" class="secondary-btn">Logout</button>
        </form>
    </section>

    <% if (dbError != null) { %>
        <div class="alert error"><%= dbError %></div>
    <% } %>

    <section class="metrics-grid">
        <article class="metric-card">
            <span>Total Credits</span>
            <strong><%= money.format(totalCredits) %></strong>
        </article>
        <article class="metric-card">
            <span>Total Debits</span>
            <strong><%= money.format(totalDebits) %></strong>
        </article>
        <article class="metric-card">
            <span>Total Amount Due</span>
            <strong><%= money.format(totalAmountDue) %></strong>
        </article>
        <article class="metric-card">
            <span>Scooters Tracked</span>
            <strong><%= totalScooters %></strong>
        </article>
    </section>

    <section class="panel">
        <div class="panel-header">
            <h2>FR-06 Credits / Debits</h2>
            <p>Wallet movement history for riders and sponsor credits.</p>
        </div>
        <div class="table-shell">
            <table>
                <thead>
                <tr>
                    <th>User</th>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Description</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                <% if (creditsDebits != null && !creditsDebits.isEmpty()) {
                    for (CreditDebitReportRow row : creditsDebits) { %>
                    <tr>
                        <td><%= row.getUserName() %></td>
                        <td><span class="badge <%= "CREDIT".equalsIgnoreCase(row.getTransactionType()) ? "credit" : "debit" %>"><%= row.getTransactionType() %></span></td>
                        <td><%= money.format(row.getAmount()) %></td>
                        <td><%= row.getDescription() %></td>
                        <td><%= dateTime.format(row.getTransactionDate()) %></td>
                    </tr>
                <%  }
                } else { %>
                    <tr><td colspan="5">No wallet transactions available.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>

    <section class="dashboard-grid">
        <article class="panel">
            <div class="panel-header">
                <h2>Monthly Payments Due</h2>
                <p>Monthly credits, debits, and the amount payable within 10 days after month-end.</p>
            </div>
            <div class="table-shell">
                <table>
                    <thead>
                    <tr>
                        <th>Month</th>
                        <th>Credits</th>
                        <th>Debits</th>
                        <th>Amount Due</th>
                        <th>Due By</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (monthlyPayments != null && !monthlyPayments.isEmpty()) {
                        for (MonthlyPaymentReportRow row : monthlyPayments) { %>
                        <tr>
                            <td><%= row.getMonthLabel() %></td>
                            <td><%= money.format(row.getTotalCredits()) %></td>
                            <td><%= money.format(row.getTotalDebits()) %></td>
                            <td><%= money.format(row.getAmountDue()) %></td>
                            <td><%= row.getDueBy() %></td>
                        </tr>
                    <%  }
                    } else { %>
                        <tr><td colspan="5">No monthly payment data available.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </article>

        <article class="panel">
            <div class="panel-header">
                <h2>Scooters Per Station</h2>
                <p>Station inventory and available fleet distribution.</p>
            </div>
            <div class="table-shell">
                <table>
                    <thead>
                    <tr>
                        <th>Station</th>
                        <th>Location</th>
                        <th>Capacity</th>
                        <th>E-Scooter List</th>
                        <th>Scooters</th>
                        <th>Available</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (scootersPerStation != null && !scootersPerStation.isEmpty()) {
                        for (ScootersPerStationReportRow row : scootersPerStation) { %>
                        <tr>
                            <td><%= row.getStationName() %></td>
                            <td><%= row.getLocation() %></td>
                            <td><%= row.getCapacity() %></td>
                            <td><%= row.getScooterList() %></td>
                            <td><%= row.getScooterCount() %></td>
                            <td><%= row.getAvailableScooters() %></td>
                        </tr>
                    <%  }
                    } else { %>
                        <tr><td colspan="6">No station inventory data available.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </article>
    </section>
</main>
</body>
</html>
