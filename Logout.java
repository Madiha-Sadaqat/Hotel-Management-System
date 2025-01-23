import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;

public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the session
        HttpSession session = request.getSession(false); // Retrieve existing session
        
        // Check for admin or customer session
        if (session != null) {
            // Invalidate the session if admin or customer is logged in
            if (session.getAttribute("id") != null) {
                session.invalidate(); // Admin session
                response.sendRedirect("index.jsp");
            } else if (session.getAttribute("cust_id") != null) {
                session.invalidate(); // Customer session
                response.sendRedirect("index.jsp");
            }
        } else {
            // If no session exists, redirect to home or login page
            response.sendRedirect("index.jsp");
        }
    }
}
