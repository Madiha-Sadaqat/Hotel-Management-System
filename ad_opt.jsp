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
             session = request.getSession(false); // Retrieve the session, don't create a new one
            if (session == null || !"Admin".equals(session.getAttribute("role"))) {
                response.sendRedirect("ad_login.jsp"); // Redirect to login page if session is invalid or role is not admin
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
        <a href="add.jsp">Add Room</a> |
        <a href="update.jsp">Update Room</a> |
        <a href="remove.jsp">Remove Room</a> |
        <a href="view.jsp">View Reservations</a> |
        <a href="History">History Management</a> |
        <form action="Logout" method="get">
            <input type="submit" value="Log Out">
        </form>
    </nav>

    <div class="container">
        <h2>Welcome Admin!</h2>
        <p>Select from the options above to manage the system.</p>
    </div>
</body>

</html>
<!-- baj login kren -->