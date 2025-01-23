import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class Cancel extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); 
        if (session == null || session.getAttribute("cust_id") == null || !"Customer".equals(session.getAttribute("role").toString())) {
            response.sendRedirect("login.jsp");
            return;
        }

        PrintWriter out = response.getWriter();

        int custId = (int) session.getAttribute("cust_id");

        String reservationIdParam = request.getParameter("reservation_id");
        
        if (reservationIdParam == null || reservationIdParam.isEmpty()) {
            out.println("<html><body><h3>Error: Reservation ID is required</h3>");
            out.println("<p>Please provide a valid Reservation ID.</p>");
            out.println("<a href='cancel.jsp'>Try Again</a>");
            out.println("</body></html>");
            return;
        }

        int reservationId = -1;  // Default value
        try {
            reservationId = Integer.parseInt(reservationIdParam);
        } catch (NumberFormatException e) {
            out.println("<html><body><h3>Error: Invalid Reservation ID</h3>");
            out.println("<p>Reservation ID must be a valid number.</p>");
            out.println("<a href='cancel.jsp'>Try Again</a>");
            out.println("</body></html>");
            return;
        }

        // SQL queries
        String checkReservationQuery = "SELECT * FROM Reservations WHERE reservation_id = ? AND cust_id = ?";
        String cancelReservationQuery = "UPDATE Reservations SET status = 'canceled' WHERE reservation_id = ? AND cust_id = ?";

        Connection con = null;
        PreparedStatement pstCheckReservation = null;
        PreparedStatement pstCancelReservation = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish the database connection
            String url = "jdbc:mysql://127.0.0.1:3306/hotel";
            con = DriverManager.getConnection(url, "root", "root");

            // Check if the reservation exists and belongs to the logged-in user
            pstCheckReservation = con.prepareStatement(checkReservationQuery);
            pstCheckReservation.setInt(1, reservationId);
            pstCheckReservation.setInt(2, custId);
            ResultSet rs = pstCheckReservation.executeQuery();

            if (!rs.next()) {
                // Reservation not found for this customer
                out.println("<html><body><h3>Error: Reservation not found</h3>");
                out.println("<p>The reservation does not exist or does not belong to the logged-in user.</p>");
                out.println("<a href='cancel.jsp'>Try Again</a>");
                out.println("</body></html>");
                return;
            }

            // Proceed with canceling the reservation
            pstCancelReservation = con.prepareStatement(cancelReservationQuery);
            pstCancelReservation.setInt(1, reservationId);
            pstCancelReservation.setInt(2, custId);
            int rowsAffected = pstCancelReservation.executeUpdate();

            if (rowsAffected > 0) {
                // Reservation successfully canceled
                out.println("<html><body><h3>Reservation Canceled</h3>");
                out.println("<p>Your reservation has been successfully canceled.</p>");
                out.println("<a href='cust_opt.jsp'>Back to Dashboard</a>");
                out.println("</body></html>");
            } else {
                // Cancellation failed
                out.println("<html><body><h3>Error: Could not cancel reservation</h3>");
                out.println("<p>There was an issue processing your cancellation request. Please try again later.</p>");
                out.println("<a href='cancel.jsp'>Try Again</a>");
                out.println("</body></html>");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            try {
                if (pstCheckReservation != null) pstCheckReservation.close();
                if (pstCancelReservation != null) pstCancelReservation.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
