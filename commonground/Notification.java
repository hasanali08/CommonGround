package commonground;


import java.time.LocalDateTime;

public class Notification {
    private String notificationId;
    private String message;
    private String senderId; 
    private LocalDateTime timestamp;

   

    public Notification(String message, String senderId, LocalDateTime localDate) {
        
        this.message = message;
        this.setSenderId(senderId);
        if(localDate!=null) {
        this.setTimestamp(LocalDateTime.now());
        }
        else {
        	this.setTimestamp(localDate);
        }
        
        
    }
    
    public Notification(String notificationId2, String message2, String senderId2, LocalDateTime timestamp2) {
    	
    	this.notificationId = notificationId2;
    	this.message = message2;
        this.setSenderId(senderId2);
        this.setTimestamp(timestamp2);
        
	}

	public String getMessage() {
		return message;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
