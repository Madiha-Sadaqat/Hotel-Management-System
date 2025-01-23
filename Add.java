import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Add extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Retrieve parameters from the form
        String roomNumber = request.getParameter("room_number");
        String roomType = request.getParameter("room_type");
        String price = request.getParameter("price");
        String availability = request.getParameter("is_available");

        HttpSession session = request.getSession(false); 
        if (session == null || session.getAttribute("id") == null || !"Admin".equals(session.getAttribute("role").toString())) {
            response.sendRedirect("ad_login.jsp");
            return;
        }

        PrintWriter out = response.getWriter();
        Connection con = null;
        PreparedStatement pst = null;
       
        String message = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/hotel";
            con = DriverManager.getConnection(url, "root", "root");

            String query = "INSERT INTO rooms (room_id, room_type, price, is_available) VALUES (?, ?, ?, ?)";
            pst = con.prepareStatement(query);
            pst.setString(1, roomNumber);
            pst.setString(2, roomType);
            pst.setString(3, price);
            pst.setString(4, availability);

            int result = pst.executeUpdate();

            if (result > 0) {
                message = "Room added successfully!";
            } else {
                message = "Room not added. Please try again.";
            }
        } 
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            message = "Error occurred: " + e.getMessage();
        } 
        finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        out.println("<html><body>");
        out.println("<h3>" + message + "</h3>");
        out.println("<a href='ad_opt.jsp'>Back to Dashboard</a>");
        out.println("</body></html>");
    }
}
