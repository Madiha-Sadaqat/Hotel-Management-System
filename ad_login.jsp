<!DOCTYPE html>
<html lang="en">

<head>
    <title>Admin Dashboard</title>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="javax.servlet.*,javax.servlet.http.*" %>
</head>

<body>
    <% 
        try {
            if (session == null || !"Admin".equals(session.getAttribute("role"))) {
                response.sendRedirect("index.jsp"); // Redirect to index.jsp if session is invalid or role is not admin
                return;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            response.sendRedirect("index.jsp"); // Redirect to index.jsp on error
            return;
        }
    %>

    <header>
        <h1>Admin's Dashboard</h1>
    </header>

    <nav>
        <a href="index.jsp">Homepage</a>
        <a href="logout.jsp">Logout</a>
    </nav>

    <div class="container">
        <h2>Login Page</h2>

        <!-- Form for admin login -->
        <form action="AdminLogin" method="post">
            <label for="ad_name">Username:</label>
            <input type="text" id="ad_name" name="ad_name" required />

            <label for="ad_pswd">Password:</label>
            <input type="password" id="ad_pswd" name="ad_pswd" required />

            <button type="submit">Login</button>
        </form>

        <!-- Placeholder for displaying error messages -->
        <p class="error" id="errorMessage"></p>
    </div>

    <script src="login.js"></script>
</body>

</html>
