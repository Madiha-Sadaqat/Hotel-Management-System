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

</head>
<body>
    <h1>Add a New Room</h1>

    <!-- Display message if set by the servlet -->
    <p id="message">${message}</p>

    <form action="Add" method="post">
        Room Number: <input type="text" name="room_number" required><br><br>
        Room Type: <input type="text" name="room_type" required><br><br>
        Price: <input type="number" name="price" required><br><br>
        <label for="is_available">Is Available (1 for Yes, 0 for No):</label>
        <input type="number" id="is_available" name="is_available" min="0" max="1" required><br><br>
        
        <input type="submit" value="Add Room">
    </form>

    <a href="ad_opt.jsp">Back to Dashboard</a>
</body>
</html>



