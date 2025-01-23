<!DOCTYPE html>
<html lang="en">

<head>
    <title>book room</title>
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
    <h1>Book a Room</h1>
    <form action="Book" method="POST">
        <label for="room_id">Room ID:</label>
        <input type="number" id="room_id" name="room_id" required><br><br>

        <label for="checkin_date">Check-in Date:</label>
        <input type="date" id="checkin_date" name="checkin_date" required><br><br>

        <label for="checkout_date">Check-out Date:</label>
        <input type="date" id="checkout_date" name="checkout_date" required><br><br>

        <input type="submit" value="Book Reservation">
    </form>

    <br>
    <a href="cust_opt.jsp">Back to Dashboard</a>
</body>
</html>
