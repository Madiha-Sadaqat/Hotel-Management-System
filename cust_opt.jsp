<<!DOCTYPE html>
<html lang="en">

<head>
    <title>Customer welcome</title>
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
    <h1>Welcome, Customer!</h1>
    <p style="color:green;" id="congratsMessage"></p>
    <p>Select from the options:</p>
    <a href="search.jsp">Search Room</a> <br>
    <a href="book.jsp">Book Room</a> <br>
    <a href="cancel.jsp">Cancel Reservations</a> <br>
    <form action="Logout" method="get">
        <input type="submit" value="Log Out">
    </form>

    <!-- <br><br>
    <form action="Add" method="post">
        <input type="submit" value="Test">
    </form> -->

</body>
</html>

