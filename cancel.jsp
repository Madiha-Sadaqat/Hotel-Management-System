<!DOCTYPE html>
<html lang="en">

<head>
    <title>NOMI>>>>>>>></title>
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
    <h3>Cancel Your Reservation</h3>
    <form action="Cancel" method="post">
        <label for="reservation_id">Reservation ID:</label>
        <input type="text" name="reservation_id" id="reservation_id" required>
        <button type="submit">Cancel Reservation</button>
    </form>
</body>
</html>
