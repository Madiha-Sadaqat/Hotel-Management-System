  import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AdminLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("ad_name");
        String password = request.getParameter("ad_pswd");

        PrintWriter out = response.getWriter();
        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/hotel"; 
            con = DriverManager.getConnection(url, "root", "root"); 

            String query = "SELECT * FROM admin WHERE name = ? AND password = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession(); 
                session.setAttribute("id", rs.getInt("id")); 
                session.setAttribute("name", rs.getString("name")); 
                response.sendRedirect("ad_opt.jsp"); 
            } 
            else {
                response.sendRedirect("ad_login.jsp?error=Invalid credentials. Please try again.");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        } 
        finally {
            try {
                if (con != null) con.close(); // Close the database connection
            } catch (SQLException ignored) {}
        }
    }
}
