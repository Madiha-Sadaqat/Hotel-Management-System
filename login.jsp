<!DOCTYPE html>
<html lang="en">

<head>
    <title>Customer Dashboard</title>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="javax.servlet.*,javax.servlet.http.*" %>
</head>

<body>
    <% 
        try {
            session = request.getSession(false); // Retrieve the session, don't create a new one
            if (session == null || !"Customer".equals(session.getAttribute("role"))) {
                response.sendRedirect("index.jsp"); // Redirect to index.jsp if session is invalid or role is not customer
                return;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            response.sendRedirect("index.jsp"); // Redirect to index.jsp on error
            return;
        }
    %>

    <header>
        <h1>Customer's Dashboard</h1>
    </header>

    <div class="container">
        <h2>Welcome Customer!</h2>
        <p>Select from the options below:</p>

        <form action="CustomerLogin" method="post">
            <label for="nameL">Username:</label>
            <input type="text" id="nameL" name="nameL" required> <br><br>

            <label for="pswdL">Password:</label>
            <input type="password" id="pswdL" name="pswdL" required> <br><br>

            <input type="submit" value="Login">
        </form>

        <p>Don't have an account? <a href="signup.jsp">Sign up</a></p>

    </div>
</body>

</html>
