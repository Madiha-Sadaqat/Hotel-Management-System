<!DOCTYPE html>
<html lang="en">

<head>
    <title>Customer signup</title>
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

<body>
    <h1>Customer's Dashboard</h1>
    <h2>Sign Up Page</h2>
    <form action="CustomerSignup" method="post">
        Username: <input type="text" id="nameS" name="nameS" required> <br><br>
        Email: <input type="email" id="email" name="email" required> <br><br>
        Password: <input type="password" id="pswdL" name="pswdL" required> <br><br>
        Confirm Password: <input type="password" id="c_pswd" name="c_pswd" required><br><br>
        <input type="submit" value="Signup">
        <p>Already have an account? <a href="login.jsp">Login</a></p>
    </form>
</body>
</html>
