import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CustomerSignup extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("nameS");
        String email = request.getParameter("email");
        String password = request.getParameter("pswdL");
        String confirmPassword = request.getParameter("c_pswd");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection con = null;
        try {
            if (!password.equals(confirmPassword)) {
                out.println("<h3 style='color:red;'>Passwords do not match. Please try again.</h3>");
                RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                rd.include(request, response);
                return;
            }

            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/hotel";
            con = DriverManager.getConnection(url, "root", "root");

            String checkQuery = "SELECT COUNT(*) FROM customer WHERE cust_name = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                out.println("<h3 style='color:red;'>Username already exists. Please choose a different username.</h3>");
                RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                rd.include(request, response);
                return;
            }

            String query = "INSERT INTO customer (cust_name, password, email, role) VALUES (?, ?, ?, 'customer')";
            PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, name);
            pst.setString(2, password);
            pst.setString(3, email);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    // Successful registration, create session
                    HttpSession session = request.getSession();
                    session.setAttribute("cust_id", generatedKeys.getInt(1)); // Store new customer ID
                    session.setAttribute("cust_name", name); // Store customer name

                    out.println("<h3 style='color:green;'>Congratulations! You have been successfully registered.</h3>");
                    RequestDispatcher rd = request.getRequestDispatcher("cust_opt.jsp");
                    rd.include(request, response);
                }
            } else {
                out.println("<h3 style='color:red;'>Sign-up failed. Please try again.</h3>");
                RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                rd.include(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ignored) {}
        }
    }
}
