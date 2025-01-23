package managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commonground.FactoryClass;
import commonground.PitchOwner;
import commonground.Player;
import commonground.TeamManager;
import commonground.User;

public class UserManager {
	
	private String connectionString;
	
	private User user;
	
	public UserManager(String connectionString) {
		this.connectionString = connectionString;
		
		
	}
	
	public Connection connect() {
		
		try {
            return DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public User login(String username, String password) throws SQLException {
		
        String query = "SELECT u.UserID, u.userType " +
                "FROM _User u JOIN LoginInfo l ON u.UserID = l.UserID " +
                "WHERE l.UserName = ? AND l.UserPassword = ?"; 

		 try (PreparedStatement statement = connect().prepareStatement(query)) {
		     statement.setString(1, username);
		     statement.setString(2, password);  
		
		
		     try (ResultSet resultSet = statement.executeQuery()) {
		         if (resultSet.next()) {
		             String userId = resultSet.getString("UserID");
		             String userType = resultSet.getString("userType");
		
		         
		             return getUserById(userId, userType); 
		         }
		     }
		 }
		 return null; 
		 
		}
	
		public boolean userExistsByUsername(String username) throws SQLException {
		    String query = "SELECT 1 FROM _User WHERE userName = ?"; 
		    try (PreparedStatement statement = connect().prepareStatement(query)) {
		        statement.setString(1, username);
		        try (ResultSet resultSet = statement.executeQuery()) {
		            return resultSet.next(); // Returns true if a user with the username exists
		        }
		    }
		}

		public User getUserById(String userId, String userType) throws SQLException {
			 String query = "SELECT * FROM _User WHERE UserID = ?";
			 try (PreparedStatement statement = connect().prepareStatement(query)) {
			     statement.setString(1, userId);
			     try (ResultSet resultSet = statement.executeQuery()) 
			     {
			         if (resultSet.next()) {
			             
			             switch (userType) {
			                 case "Player":
			                     user = new Player();
			                     
			                     break;
			                 case "TeamManager":
			                     user = new TeamManager();
			                     break;
			                 case "PitchOwner":
			                	 user = new PitchOwner();
			                 
			                 default:
			                     break;
			             }
			
			             if (user != null) {
			                 user.setUserID(resultSet.getString("UserID"));
			                 user.setUserName(resultSet.getString("userName"));
			                 user.setFirstName(resultSet.getString("firstName"));
			                 user.setLastName(resultSet.getString("lastName"));
			                 user.setEmail(resultSet.getString("email"));
			                 user.setPhoneNumber(resultSet.getString("phoneNumber"));
			                 user.setUserType(resultSet.getString("userType"));
			             }
			             return user;
			         }
			     }
			 }
			 return null; 
		}
		
		public boolean signup(User user, String username, String password) throws SQLException {

	        String userQuery = "INSERT INTO _User (UserID, userName, firstName, lastName, email, phoneNumber, userType) " +
	                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
	        try (PreparedStatement userStatement = connect().prepareStatement(userQuery)) {
	            String userId = generateUniqueUserID(); // Implement this method
	            userStatement.setString(1, userId);
	            userStatement.setString(2, user.getUserName());
	            userStatement.setString(3, user.getFirstName());
	            userStatement.setString(4, user.getLastName());
	            userStatement.setString(5, user.getEmail());  

	            userStatement.setString(6, user.getPhoneNumber());
	            userStatement.setString(7, user.getUserType()); 

	            int rowsAffected = userStatement.executeUpdate();
	            if (rowsAffected == 0) {
	                return false; 
	            }

	            
	            String loginQuery = "INSERT INTO LoginInfo (UserID, UserName, UserPassword, UserType) " +
	                               "VALUES (?, ?, ?, ?)";
	            try (PreparedStatement loginStatement = connect().prepareStatement(loginQuery)) {
	                loginStatement.setString(1, userId);
	                loginStatement.setString(2, username);
	                loginStatement.setString(3, password); 
	                loginStatement.setString(4, user.getUserType());

	                loginStatement.executeUpdate(); 
	            }

	            this.user = user;
	            this.user.setUserID(userId);
	            
	            if (user instanceof Player) {
	            	Player player = (Player)user;
	            	FactoryClass.getInstance().getPlayerListInstance().addPlayer(player);
	            	System.out.println("New player signed up!");
	                
	            } else if (user instanceof TeamManager) {
	            	TeamManager manager = (TeamManager)user;
	            	System.out.println("New manager signed up!");
	            }
	            else if(user instanceof PitchOwner) {
	            	PitchOwner pitchowner = (PitchOwner)user;
	            	System.out.println("New Pitch Owner signed up!");
	            }

	            return true; 
	        }
	    }
		public User getUser() {
			return this.user;
		}
		public boolean isValidUsername(String username) {
			String usernameRegex = "^[a-zA-Z0-9_]*$";    
			Pattern pattern = Pattern.compile(usernameRegex);
		    Matcher matcher = pattern.matcher(username);
		    return matcher.matches();
		}
		
		 public boolean isValidPassword(String password) {
			 String passwordRegex = "^.{5,}$";
		        Pattern pattern = Pattern.compile(passwordRegex);
		        Matcher matcher = pattern.matcher(password);
		        return matcher.matches();
		    }
		
		public boolean isValidPhoneNumber(String phoneNumber)  
		 {
		    
		    String phoneRegex = "^\\d{11}$"; 
		    Pattern pattern = Pattern.compile(phoneRegex);
		    Matcher matcher = pattern.matcher(phoneNumber);
		    return matcher.matches();
		}
		
		public boolean isValidEmail(String email) {
		    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
		    Pattern pattern = Pattern.compile(emailRegex);
		    Matcher matcher = pattern.matcher(email);  

		    return matcher.matches();
		}
		public String generateUniqueUserID() throws SQLException {
		    String query = "SELECT MAX(CAST(UserID AS INT)) FROM _User"; 
		    try (PreparedStatement statement = connect().prepareStatement(query);
		         ResultSet resultSet = statement.executeQuery()) {

		        if (resultSet.next()) {
		            long maxId = resultSet.getLong(1); 
		            return String.valueOf(maxId + 1);
		        } else {
		            
		            return "1"; 
		        }
		    }
		}
	}


