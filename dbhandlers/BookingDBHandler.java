package dbhandlers;

import java.sql.*;
import java.util.ArrayList;

import commonground.Booking;

public class BookingDBHandler {
    private String connectionString;

    public BookingDBHandler(String connectionString) {
        this.connectionString = connectionString;
    }

    private Connection connect() {
        try {
            return DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String generateNewBookingID() {
        String query = "SELECT MAX(CAST(BookingID AS INT)) FROM Bookings";
        int newNumber = 0;
        
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            
            if (resultSet.next()) {
                newNumber = resultSet.getInt(1);
            }
            
            newNumber++;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Integer.toString(newNumber);
    }

    public ArrayList<Booking> getAllBookings() {
        ArrayList<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Bookings";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking(rs.getString("PitchID"));
                booking.setBookingID(rs.getString("BookingID"));
                booking.setUserID(rs.getString("UserID"));
                booking.setMatchID(rs.getString("MatchID"));
                booking.setBookingDate(rs.getString("BookingDate"));
                booking.setBookingStatus(rs.getString("bookingStatus"));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public boolean addBooking(Booking booking) {
        String query = "INSERT INTO Bookings (BookingID, UserID, PitchID, MatchID, BookingDate, bookingStatus) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, booking.getBookingID());
            stmt.setString(2, booking.getUserID());
            stmt.setString(3, booking.getPitchID());
            stmt.setString(4, booking.getMatchID());
            stmt.setString(5, booking.getBookingDate());
            stmt.setString(6, booking.getBookingStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBooking(Booking booking) {
        String query = "UPDATE Bookings SET UserID = ?, PitchID = ?, BookingDate = ? , MatchID = ?, bookingStatus = ? WHERE BookingID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, booking.getUserID());
            stmt.setString(2, booking.getPitchID());
            stmt.setString(3, booking.getBookingDate());
            stmt.setString(4, booking.getMatchID());
            stmt.setString(5, booking.getBookingStatus());
            stmt.setString(6, booking.getBookingID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   

    public boolean deleteBooking(String bookingID) {
        String query = "DELETE FROM Bookings WHERE bookingID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bookingID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
