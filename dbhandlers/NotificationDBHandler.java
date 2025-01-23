package dbhandlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import commonground.Notification;

public class NotificationDBHandler {

    private String ConnectionString;
    

    public NotificationDBHandler(String ConnectionString) {
       this.ConnectionString = ConnectionString;
    }
    
    public Connection connect() throws SQLException {
    	return DriverManager.getConnection(this.ConnectionString);
    }
    
    public String generateNewNotificationID() {
	    String query = "SELECT MAX(CAST(notificationID AS INT)) FROM Notifications"; 
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

    public void addNotification(Notification notification) throws SQLException {
        String sql = "INSERT INTO Notifications (notificationId, message, senderId, timestamp) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, notification.getNotificationId());
            stmt.setString(2, notification.getMessage());
            stmt.setString(3, notification.getSenderId());
            stmt.setString(4, notification.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            stmt.executeUpdate();
        }
    }

    public ArrayList<Notification> getNotificationsForTeam(String teamId) throws SQLException {
        String sql = "SELECT * FROM Notifications WHERE senderId IN (SELECT UserID FROM ManagerTeam WHERE TeamID = ?)";
        ArrayList<Notification> notifications = new ArrayList<>();

        try (Connection conn = connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, teamId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String notificationId = rs.getString("notificationId");
                    String message = rs.getString("message");
                    String senderId = rs.getString("senderId");
                    LocalDateTime timestamp = LocalDateTime.parse(rs.getString("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    notifications.add(new Notification(notificationId, message, senderId, timestamp));
                }
            }
        }
        return notifications;
    }
}