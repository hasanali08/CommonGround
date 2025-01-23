package commonground;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PitchDBHandler {
	
	private String connectionString;
	
	PitchDBHandler(String connectionString) {
		
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

    public ArrayList<Pitch> getAllPitches() {
        ArrayList<Pitch> pitches = new ArrayList<>();
        String query = "SELECT * FROM pitches"; 

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pitch pitch = new Pitch();
                pitch.setPitchID(rs.getString("pitchID"));
                pitch.setPitchName(rs.getString("pitchName"));
                pitch.setPitchLocation(rs.getString("pitchLocation"));
                pitch.setPitchType(rs.getString("pitchType"));
                pitch.setPitchImage(rs.getString("pitchImage"));
                pitch.setPitchAvailable(rs.getBoolean("pitchAvailable"));
                pitch.setBookingPrice(rs.getDouble("bookingPrice"));
                pitches.add(pitch);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pitches;
    }
    
    public boolean pitchExists(String pitchID) {
        String query = "SELECT 1 FROM pitches WHERE pitchID = ?";
        
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pitchID);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next(); 

        } catch (SQLException e) {
            System.err.println("Error while checking if pitch exists: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPitch(Pitch pitch) {
    	
    	if (pitchExists(pitch.getPitchID())) {
           
            return false;
        }
    	
    	
        String query = "INSERT INTO pitches (pitchID, pitchName, pitchLocation, pitchType, pitchImage, pitchAvailable, bookingPrice) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pitch.getPitchID());
            stmt.setString(2, pitch.getPitchName());
            stmt.setString(3, pitch.getPitchLocation());
            stmt.setString(4, pitch.getPitchType());
            stmt.setString(5, pitch.getPitchImage());
            stmt.setBoolean(6, pitch.getPitchAvailable());
            stmt.setDouble(7, pitch.getBookingPrice());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updatePitch(Pitch pitch) {
       
        String query = "UPDATE pitches SET pitchName = ?, pitchLocation = ?, pitchType = ?, pitchImage = ?, pitchAvailable = ?, bookingPrice = ? WHERE pitchID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

        	 stmt.setString(1, pitch.getPitchID());
             stmt.setString(2, pitch.getPitchName());
             stmt.setString(3, pitch.getPitchLocation());
             stmt.setString(4, pitch.getPitchType());
             stmt.setString(5, pitch.getPitchImage());
             stmt.setBoolean(6, pitch.getPitchAvailable());
             stmt.setDouble(7, pitch.getBookingPrice());

           
            int rowsUpdated = stmt.executeUpdate();
            
            
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error while updating pitch: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean deletePitch(String pitchID) {
        String query = "DELETE FROM pitches WHERE pitchID = ?";
        
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pitchID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
