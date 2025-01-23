import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class Search extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); 
        if (session == null || session.getAttribute("cust_id") == null || !"Customer".equals(session.getAttribute("role").toString())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String checkinDate = request.getParameter("checkin_date");
        String checkoutDate = request.getParameter("checkout_date");
        String roomType = request.getParameter("room_type");

        response.setContentType("text/html");
   
        PrintWriter out = response.getWriter();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/hotel";
            con = DriverManager.getConnection(url, "root", "root");

            String query = "SELECT * FROM rooms WHERE room_id NOT IN ("
                         + "SELECT room_id FROM reservations WHERE status = 'booked' AND ("
                         + "(checkin_date BETWEEN ? AND ?) "
                         + "OR (checkout_date BETWEEN ? AND ?) "
                         + "OR (? BETWEEN checkin_date AND checkout_date) "
                         + "OR (? BETWEEN checkin_date AND checkout_date)))"
                         + " AND room_type = ?";

            pst = con.prepareStatement(query);

            pst.setString(1, checkoutDate);
            pst.setString(2, checkinDate);
            pst.setString(3, checkoutDate);
            pst.setString(4, checkinDate);
            pst.setString(5, checkinDate);
            pst.setString(6, checkoutDate);
            pst.setString(7, roomType);  

            rs = pst.executeQuery();

            out.println("<html><body>");
            out.println("<h2>Available Rooms</h2>");

            boolean available = false;
            while (rs.next()) {
                available = true;
                int roomId = rs.getInt("room_id");
                String roomTypeResult = rs.getString("room_type");
                double price = rs.getDouble("price");
                out.println("<p>Room ID: " + roomId + "<br>Room Type: " + roomTypeResult + "<br>Price: " + price + "</p>");
            }

            if (!available) {
                out.println("<p>No rooms available for the selected dates and type.</p>");
            }

            out.println("<a href='cust_opt.jsp'>Back to Dashboard</a>");
            out.println("</body></html>");
        } 
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error while searching for rooms: " + e.getMessage());
        } 
        finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
