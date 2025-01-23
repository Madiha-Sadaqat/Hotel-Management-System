import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class View extends HttpServlet { 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession(false); 
        if (session == null || session.getAttribute("id") == null || !"Admin".equals(session.getAttribute("role").toString())) {
            response.sendRedirect("ad_login.jsp");
            return;
        }

        PrintWriter out = response.getWriter();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/hotel";
            con = DriverManager.getConnection(url, "root", "root");

            String query = "SELECT * FROM rooms";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            out.println("<html><body>");
            out.println("<h3>Room Details</h3>");
            out.println("<table border='1'>");
            out.println("<tr><th>Room ID</th><th>Room Type</th><th>Price</th><th>Availability</th></tr>");

            while (rs.next()) {
                String roomId = rs.getString("room_id");
                String roomType = rs.getString("room_type");
                double price = rs.getDouble("price");
                boolean isAvailable = rs.getBoolean("is_available");

                out.println("<tr>");
                out.println("<td>" + roomId + "</td>");
                out.println("<td>" + roomType + "</td>");
                out.println("<td>" + price + "</td>");
                out.println("<td>" + (isAvailable ? "Available" : "Not Available") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<a href='ad_opt.jsp'>Back to Dashboard</a>");
            out.println("</body></html>");
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("<html><body><h3>Error: " + e.getMessage() + "</h3></body></html>");
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
