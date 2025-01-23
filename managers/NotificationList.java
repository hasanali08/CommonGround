package managers;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import commonground.Notification;
import dbhandlers.NotificationDBHandler;

public class NotificationList {
	
	NotificationDBHandler DBHandler;
	ArrayList<Notification> list;
	
	public NotificationList(NotificationDBHandler notificationDBHandler) {
		DBHandler = notificationDBHandler; 
		list = new ArrayList<>();
	}
	
	public Notification createNotification(String message, String senderId) throws SQLException {
	    String notificationId = DBHandler.generateNewNotificationID();
	    LocalDateTime timestamp = LocalDateTime.now();
//	    message = "( " + timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " ) "+ message;
	    Notification newNotification = new Notification(notificationId, message, senderId, timestamp);
	    

	    addNotification(newNotification); 
	    
	    return newNotification;
	}
	
	public void addNotification(Notification notification) throws SQLException {
	    
	    list.add(notification); 
	    DBHandler.addNotification(notification);
	}

	public ArrayList<Notification> getNotificationsForTeam(String teamId) throws SQLException {
	    list = DBHandler.getNotificationsForTeam(teamId);
	    return list;
	}
	
	

}