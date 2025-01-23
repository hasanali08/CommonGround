package dbhandlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import commonground.Pitch;

public class PitchDBHandler implements PitchDataHandler {
	
	private String connectionString;
	
	public PitchDBHandler(String connectionString) {
		
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
    
    public String generateNewPitchID() throws SQLException{
        String query = "SELECT MAX(CAST(PitchID AS INT)) FROM Pitches";
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
    
    public String getPitchOwner(String pitchID) {
        String query = "SELECT UserID FROM PitchOwner WHERE PitchID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pitchID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("UserID");
                } else {
                    return null; 
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }
    
    public ArrayList<Pitch> getPitchesByOwner(String pitchOwnerID) {
        ArrayList<Pitch> pitches = new ArrayList<>();
        String query = "SELECT p.* " +
                       "FROM pitches p " +
                       "JOIN PitchOwner po ON p.pitchID = po.PitchID " +
                       "WHERE po.UserID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pitchOwnerID);
            try (ResultSet rs = stmt.executeQuery()) {
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pitches;
    }
    
    

    public boolean addPitch(Pitch pitch, String userID) {

        if (pitchExists(pitch.getPitchID())) {
            return false;
        }

        try (Connection conn = connect();
             PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO pitches (pitchID, pitchName, pitchLocation, pitchType, pitchImage, pitchAvailable, bookingPrice) VALUES (?, ?, ?, ?, ?, ?, ?)");
             PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO PitchOwner (UserID, PitchID) VALUES (?, ?)")) {

            // 1. Insert into pitches table
            stmt1.setString(1, pitch.getPitchID());
            stmt1.setString(2, pitch.getPitchName());
            stmt1.setString(3, pitch.getPitchLocation());
            stmt1.setString(4, pitch.getPitchType());
            stmt1.setString(5, pitch.getPitchImage());
            stmt1.setBoolean(6, pitch.getPitchAvailable());
            stmt1.setDouble(7, pitch.getBookingPrice());
            int rowsAffected1 = stmt1.executeUpdate();

            // 2. Insert into PitchOwner table
            stmt2.setString(1, userID);
            stmt2.setString(2, pitch.getPitchID());
            int rowsAffected2 = stmt2.executeUpdate();

            // Check if both inserts were successful
            return rowsAffected1 > 0 && rowsAffected2 > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updatePitch(Pitch pitch) {
       
        String query = "UPDATE pitches SET pitchName = ?, pitchLocation = ?, pitchType = ?, pitchImage = ?, pitchAvailable = ?, bookingPrice = ? WHERE pitchID = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

        	 
             stmt.setString(1, pitch.getPitchName());
             stmt.setString(2, pitch.getPitchLocation());
             stmt.setString(3, pitch.getPitchType());
             stmt.setString(4, pitch.getPitchImage());
             stmt.setBoolean(5, pitch.getPitchAvailable());
             stmt.setDouble(6, pitch.getBookingPrice());
             stmt.setString(7, pitch.getPitchID());

           
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
