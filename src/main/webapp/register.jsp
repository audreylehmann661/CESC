<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>CESC Registration</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body class="auth-page">
<main class="auth-shell">
    <section class="auth-card">
        <p class="eyebrow">CESC Portal</p>
        <h1>Create an account</h1>
        <% if (request.getParameter("error") != null) { %>
            <div class="alert error">Registration failed. The email may already exist.</div>
        <% } %>
        <form action="controller" method="post" class="stack-form">
            <input type="hidden" name="action" value="register">

            <label>Name</label>
            <input type="text" name="name" placeholder="Full name" required>

            <label>Email</label>
            <input type="email" name="email" placeholder="name@cesc.ca" required>

            <label>Password</label>
            <input type="password" name="password" placeholder="Create a password" required>

            <label>Role</label>
            <select name="role">
                <option value="USER">User</option>
                <option value="SPONSOR">Sponsor</option>
                <option value="MAINTAINER">Maintainer</option>
                <option value="ADMIN">Admin</option>
            </select>

            <button type="submit" class="primary-btn full-width">Register</button>
        </form>
        <a class="inline-link" href="login.jsp">Back to login</a>
    </section>
</main>
</body>
</html>
