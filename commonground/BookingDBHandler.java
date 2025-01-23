package commonground;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BookingDBHandler {
	
	private String connectionString;
	
	BookingDBHandler(String connectionString) {
		
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

}
