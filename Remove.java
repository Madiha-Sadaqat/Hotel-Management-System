import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Remove extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String roomId = request.getParameter("room_id");

        HttpSession session = request.getSession(false); 
        if (session == null || session.getAttribute("id") == null || !"Admin".equals(session.getAttribute("role").toString())) {
            response.sendRedirect("ad_login.jsp");
            return;
        }

        String message = "";
        PrintWriter out = response.getWriter();

        Connection con = null;
        PreparedStatement pst = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/hotel";
            con = DriverManager.getConnection(url, "root", "root");

            String query = "DELETE FROM rooms WHERE room_id = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, roomId);

            int result = pst.executeUpdate();

            if (result > 0) {
                message = "Room removed successfully!";
            } else {
                message = "Room not found or failed to remove. Please try again.";
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
