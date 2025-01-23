// import javax.servlet.*;
// import javax.servlet.http.*;
// import java.io.IOException;
// import java.sql.*;

// public class CustomerLogin extends HttpServlet {
//     @Override
//     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//         String name = request.getParameter("nameL");
//         String password = request.getParameter("pswdL");

//         Connection con = null;
//         Statement st = null;

//         try {
//             Class.forName("com.mysql.jdbc.Driver");
//             String url = "jdbc:mysql://127.0.0.1:3306/hotel";
//             con = DriverManager.getConnection(url, "root", "root");

//             String query = "SELECT * FROM customer WHERE cust_name = ? AND password = ?";
//             PreparedStatement pst = con.prepareStatement(query);
//             pst.setString(1, name);
//             pst.setString(2, password);
//             ResultSet rs = pst.executeQuery();

//             if (rs.next()) {
//                 // Login successful, redirect to customer options page
//                 response.sendRedirect("cust_opt.html");
//             } else {
//                 // Login failed, redirect to login page with error message
//                 String errorMessage = "Invalid credentials. Please try again.";
//                 response.sendRedirect("login.html?error=" + errorMessage);
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//             response.getWriter().println("Error: " + e.getMessage() + " login cust");
//         }
//     }
// }

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CustomerLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("nameL");
        String password = request.getParameter("pswdL");

        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/hotel";
            con = DriverManager.getConnection(url, "root", "root");

            String query = "SELECT * FROM customer WHERE cust_name = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("cust_id", rs.getInt("cust_id")); 
                session.setAttribute("cust_name", rs.getString("cust_name")); 
                response.sendRedirect("cust_opt.jsp");
            } 
            else {
                response.sendRedirect("login.jsp?error=Invalid credentials. Please try again.");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } 
        finally {
            try {
                if (con != null) con.close();
            }
            catch (SQLException ignored) {}
        }
    }
}

