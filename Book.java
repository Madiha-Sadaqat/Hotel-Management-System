import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class Book extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("cust_id") == null || !"Customer".equals(session.getAttribute("role").toString())) {
            response.sendRedirect("login.jsp");
            return;
        }
        PrintWriter out = response.getWriter();
        
        int custId = (int) session.getAttribute("cust_id");

        String room_id = request.getParameter("room_id");
        String checkInDate = request.getParameter("checkin_date");
        String checkOutDate = request.getParameter("checkout_date");

        int roomId = -1;  // Default value
        if (room_id != null && !room_id.isEmpty()) {
            try {
                roomId = Integer.parseInt(room_id);
            } 
            catch (NumberFormatException e) {
                out.println("<html><body><h3>Error: Invalid room ID</h3>");
                out.println("<p>Room ID must be a valid number.</p>");
                out.println("<a href='book.jsp'>Try Again</a>");
                out.println("</body></html>");
                return;
            }
        } 
        else {
            out.println("<html><body><h3>Error: Room ID is required</h3>");
            out.println("<p>Please provide a valid Room ID.</p>");
            out.println("<a href='book.jsp'>Try Again</a>");
            out.println("</body></html>");
            return;
        }

        String checkAvailabilityQuery = "SELECT * FROM rooms WHERE room_id = ? AND is_available = TRUE";
        String checkReservationQuery = "SELECT * FROM reservations WHERE room_id = ? "
                                       + "AND ((checkin_date <= ? AND checkout_date >= ?) "
                                       + "OR (checkin_date >= ? AND checkout_date <= ?)) "
                                       + "AND status = 'booked'";
        String insertReservationQuery = "INSERT INTO reservations (cust_id, room_id, checkin_date, checkout_date, status) "
                                      + "VALUES (?, ?, ?, ?, 'booked')";

        response.setContentType("text/html");

        Connection con = null;
        PreparedStatement pstCheckAvailability = null;
        PreparedStatement pstCheckReservation = null;
        PreparedStatement pstInsertReservation = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/hotel";
            con = DriverManager.getConnection(url, "root", "root");

            // Check if the room is available
            pstCheckAvailability = con.prepareStatement(checkAvailabilityQuery);
            pstCheckAvailability.setInt(1, roomId);
            ResultSet rs = pstCheckAvailability.executeQuery();

            if (!rs.next()) {
                // Room not found or unavailable
                out.println("<html><body><h3>Room Unavailable</h3>");
                out.println("<p>Sorry, this room is unavailable.</p>");
                out.println("<a href='book.jsp'>Try Again</a>");
                out.println("</body></html>");
            } else {
                // Room is available, check if it is already reserved for the dates
                pstCheckReservation = con.prepareStatement(checkReservationQuery);
                pstCheckReservation.setInt(1, roomId);
                pstCheckReservation.setString(2, checkInDate);
                pstCheckReservation.setString(3, checkInDate);
                pstCheckReservation.setString(4, checkOutDate);
                pstCheckReservation.setString(5, checkOutDate);

                ResultSet reservationRs = pstCheckReservation.executeQuery();

                if (reservationRs.next()) {
                    // Room is already reserved for the selected dates
                    out.println("<html><body><h3>Room Unavailable for Dates</h3>");
                    out.println("<p>Sorry, this room is already booked for the selected dates.</p>");
                    out.println("<a href='book.jsp'>Try Again</a>");
                    out.println("</body></html>");
                } else {
                    // Room is available, proceed with booking
                    pstInsertReservation = con.prepareStatement(insertReservationQuery, Statement.RETURN_GENERATED_KEYS);
                    pstInsertReservation.setInt(1, custId);  // Use session-based cust_id
                    pstInsertReservation.setInt(2, roomId);
                    pstInsertReservation.setString(3, checkInDate);
                    pstInsertReservation.setString(4, checkOutDate);

                    // Execute the insertion query
                    int rowsAffected = pstInsertReservation.executeUpdate();

                    if (rowsAffected > 0) {
                        // Get the generated reservation ID
                        ResultSet generatedKeys = pstInsertReservation.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int reservationId = generatedKeys.getInt(1);  // Get generated reservation ID

                            // Reservation successful, show reservation ID
                            out.println("<html><body><h3>Reservation Successful!</h3>");
                            out.println("<p>Your reservation has been booked.</p>");
                            out.println("<p>Reservation ID: " + reservationId + "</p>");
                            out.println("<p>Use this Reservation ID to cancel your reservation.</p>");
                            out.println("<a href='cust_opt.jsp'>Back to Dashboard</a>");
                            out.println("</body></html>");
                        }
                    } else {
                        // Reservation failed, show error message
                        out.println("<html><body><h3>Reservation Failed</h3>");
                        out.println("<p>Sorry, we couldn't process your reservation at this time.</p>");
                        out.println("<a href='book.jsp'>Try Again</a>");
                        out.println("</body></html>");
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            try {
                if (pstCheckAvailability != null) pstCheckAvailability.close();
                if (pstCheckReservation != null) pstCheckReservation.close();
                if (pstInsertReservation != null) pstInsertReservation.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
