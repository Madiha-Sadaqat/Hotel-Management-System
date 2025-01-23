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
            if (session == null || !"Admin".equals(session.getAttribute("role"))) {
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
    </header>le>View Rooms</title>
</head>
<body>
    <h2>View All Rooms</h2>
    <form action="View" method="GET">
        <button type="submit">View Rooms</button>
    </form>
    <a href="ad_opt.jsp">Dashboard</a>
</body>
</html>