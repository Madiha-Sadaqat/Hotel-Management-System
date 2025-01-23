import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class RoleSelection extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getParameter("role");
        HttpSession session = request.getSession(); 
        session.setAttribute("role", role); 

        if ("Admin".equals(role)) {
            response.sendRedirect("ad_login.jsp");
        } 
        else if ("Customer".equals(role)) {
            response.sendRedirect("login.jsp");
        } 
        else {
            session.invalidate(); 
        }
    }
}
