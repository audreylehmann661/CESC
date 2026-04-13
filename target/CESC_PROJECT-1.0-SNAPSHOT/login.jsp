<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>CESC Login</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body class="auth-page">
<main class="auth-shell">
    <section class="auth-card">
        <p class="eyebrow">CESC Portal</p>
        <h1>Sign in to the analytics dashboard</h1>
        <% if (request.getParameter("error") != null) { %>
            <div class="alert error">Invalid email or password.</div>
        <% } %>
        <% if (request.getParameter("registered") != null) { %>
            <div class="alert success">Registration completed. You can sign in now.</div>
        <% } %>
        <% if (request.getParameter("logout") != null) { %>
            <div class="alert success">You have been logged out.</div>
        <% } %>
        <form action="controller" method="post" class="stack-form">
            <input type="hidden" name="action" value="login">

            <label>Email</label>
            <input type="email" name="email" placeholder="admin@cesc.ca" required>

            <label>Password</label>
            <input type="password" name="password" placeholder="Enter your password" required>

            <button type="submit" class="primary-btn full-width">Login</button>
        </form>
        <p class="helper-text">Seed admin account: <strong>admin@cesc.ca</strong> / <strong>admin123</strong></p>
        <a class="inline-link" href="register.jsp">Need an account? Register here.</a>
    </section>
</main>
</body>
</html>
