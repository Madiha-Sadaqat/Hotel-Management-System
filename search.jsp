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
<body>
    <h1>Search Available Rooms</h1>
    <form method="POST" action="Search">
        <label for="checkin_date">Check-in Date:</label>
        <input type="date" id="checkin_date" name="checkin_date" required><br><br>
        
        <label for="checkout_date">Check-out Date:</label>
        <input type="date" id="checkout_date" name="checkout_date" required><br><br>
    
        <label for="room_type">Room Type:</label>
        <select id="room_type" name="room_type">
            <option value="single">Single</option>
            <option value="double">Double</option>
            <option value="suite">Suite</option>
        </select><br><br>
        
        <input type="submit" value="Search Rooms">
    </form>
</body>
</html>

