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
    </header>
<body>
    <h2>Update Room</h2>
    <form action="Update" method="POST">
        <label for="room_id">Current Room Number:</label>
        <input type="text" id="room_id" name="room_id" required><br><br>
        
        <label for="newType">New Room Type:</label>
        <input type="text" id="newType" name="newType" required><br><br>
        
        <label for="newPrice">New Price:</label>
        <input type="number" id="newPrice" name="newPrice" step="0.01" required><br><br>
        
        <label for="isAvailable">Is Available (1 for Yes, 0 for No):</label>
        <input type="number" id="isAvailable" name="isAvailable" min="0" max="1" required><br><br>
        
        <button type="submit">Update Room</button>
    </form>
    <a href="ad_opt.jsp">Dashboard</a>
</body>
</html>