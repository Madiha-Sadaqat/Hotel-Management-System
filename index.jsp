<!DOCTYPE html>
<html>

<head>
    <title>Login</title>
    <%@ page language="java" import="javax.servlet.*,javax.servlet.http.*" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" session="false" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hotel Management System</title>
</head>
<body>
    <h1>Welcome to Hotel Management System!</h1>
    <p>Select your role:</p>
    <form action="RoleSelection" method="post">
        <input type="radio" name="role" value="Admin" required> Admin <br>
        <input type="radio" name="role" value="Customer" required> Customer <br><br>
        <input type="submit" value="Proceed"> 
    </form>
</body>
</html>
