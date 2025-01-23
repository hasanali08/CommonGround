package commonground;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PlayerDBHandler {
	
	private String connectionString;
	
	PlayerDBHandler(String connectionString) {
		
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
