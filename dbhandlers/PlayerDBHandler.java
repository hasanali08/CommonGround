package dbhandlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import commonground.Player;

public class PlayerDBHandler {
	
	private String connectionString;
	
	public PlayerDBHandler(String connectionString) {
		
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
	 
	
	 
	 public boolean addPlayer(Player player) {
		    // 1. Check if the player already exists
		    String checkQuery = "SELECT COUNT(*) FROM Player WHERE UserID = ?";
		    try (Connection connection = connect();
		         PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

		        checkStatement.setString(1, player.getUserID());

		        try (ResultSet resultSet = checkStatement.executeQuery()) {
		            if (resultSet.next() && resultSet.getInt(1) > 0) {
		                // Player already exists
		                System.out.println("Player with UserID " + player.getUserID() + " already exists.");
		                return false; 
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }

		    
		    String insertQuery = "INSERT INTO Player (UserID, position, Skills, matchesPlayed, totalGoalsScored, totalAssistsMade) " +
		            "VALUES (?, ?, ?, ?, ?, ?)";

		    try (Connection connection = connect();
		         PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

		        insertStatement.setString(1, player.getUserID());
		        insertStatement.setString(2, player.getPosition());

		        String skillsString = String.join(",", player.getSkills());
		        insertStatement.setString(3, skillsString);

		        insertStatement.setInt(4, player.getMatchesPlayed());
		        insertStatement.setInt(5, player.getGoalsScored());
		        insertStatement.setInt(6, player.getAssistsMade());

		        int rowsAffected = insertStatement.executeUpdate();

		        return rowsAffected > 0;

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
	 
	 public ArrayList<Player> getPlayersWithoutTeam() {
	        ArrayList<Player> players = new ArrayList<>();
	        String query = "SELECT p.* " +
	                "FROM Player p " +
	                "LEFT JOIN PlayerTeam pt ON p.UserID = pt.UserID " +
	                "WHERE pt.UserID IS NULL";

	        try (Connection connection = connect();
	             PreparedStatement statement = connection.prepareStatement(query);
	             ResultSet resultSet = statement.executeQuery()) {

	            while (resultSet.next()) {
	                Player player = new Player();
	                player.setUserID(resultSet.getString("UserID"));
	                player.setPosition(resultSet.getString("position"));
	                player.setMatchesPlayed(resultSet.getInt("matchesPlayed"));
	                player.setGoalsScored(resultSet.getInt("totalGoalsScored"));
	                player.setAssistsMade(resultSet.getInt("totalAssistsMade"));
	                String skillsString = resultSet.getString("Skills");
	                if (skillsString != null) {
	                    ArrayList<String> skills = new ArrayList<>(Arrays.asList(skillsString.split(",")));
	                    player.setSkills(skills);
	                }

	                player = this.getPlayerUserDetails(player);
	                players.add(player);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        if(players.size()==0) {
	        	return null;
	        }
	        return players;
	    }
	 
	 public String getPlayerTeamID(String playerID) throws SQLException {
		    
	        String query = "SELECT TeamID FROM PlayerTeam WHERE UserID = ?";

	        try (PreparedStatement statement = connect().prepareStatement(query)) { 
	            statement.setString(1, playerID);

	            
	            try (ResultSet resultSet = statement.executeQuery()) {

	                
	                if (resultSet.next()) {
	                    return resultSet.getString("TeamID"); 
	                } else {
	                    return null; 
	                }
	            }
	        }
	    }
	 
	 public ArrayList<Player> getAllPlayers() {
	        ArrayList<Player> players = new ArrayList<>();
	        String query = "SELECT * FROM Player"; 

	        try (Connection connection = connect();
	             PreparedStatement statement = connection.prepareStatement(query);
	             ResultSet resultSet = statement.executeQuery()) {

	            while (resultSet.next()) 
	            {
	                Player player = new Player();
	                player.setUserID(resultSet.getString("UserID")); 
	                player.setPosition(resultSet.getString("position"));
	                player.setMatchesPlayed(resultSet.getInt("matchesPlayed")); 
	                player.setGoalsScored(resultSet.getInt("totalGoalsScored"));
	                player.setAssistsMade(resultSet.getInt("totalAssistsMade")); 
	                String skillsString = resultSet.getString("Skills"); 
	                if (skillsString != null) {
	                    ArrayList<String> skills = new ArrayList<>(Arrays.asList(skillsString.split(",")));
	                    player.setSkills(skills);
	                }

	                player = this.getPlayerUserDetails(player);
	                players.add(player);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return players;
	    }
	 
	 public Player getPlayerUserDetails(Player user) throws SQLException {
		    String query = "SELECT * FROM _User WHERE UserID = ?";
		    try (PreparedStatement statement = connect().prepareStatement(query)) {
		        statement.setString(1, user.getUserID());
		        try (ResultSet resultSet = statement.executeQuery()) {

		            if (resultSet.next()) { 
		                user.setUserID(resultSet.getString("UserID"));
		                user.setUserName(resultSet.getString("userName"));
		                user.setFirstName(resultSet.getString("firstName"));
		                user.setLastName(resultSet.getString("lastName"));
		                user.setEmail(resultSet.getString("email"));
		                user.setPhoneNumber(resultSet.getString("phoneNumber"));
		                user.setUserType(resultSet.getString("userType"));

		                return user;
		            }
		        }
		    }
		    return null;
		}
	 
	 	public ArrayList<Player> getPlayersForTeam(String teamID) throws SQLException {
		    ArrayList<Player> players = new ArrayList<>();

		   
		    String query = "SELECT p.* " +
		                   "FROM Player p " +
		                   "JOIN PlayerTeam pt ON p.UserID = pt.UserID " +
		                   "WHERE pt.TeamID = ?"; 

		    try (PreparedStatement statement = connect().prepareStatement(query)) { 
		        statement.setString(1, teamID);


		        try (ResultSet resultSet = statement.executeQuery()) {

		            
		            while (resultSet.next()) {
		                Player player = new Player();
		                player.setUserID(resultSet.getString("UserID")); 
		                player.setPosition(resultSet.getString("position"));
		                player.setMatchesPlayed(resultSet.getInt("matchesPlayed")); 
		                player.setGoalsScored(resultSet.getInt("totalGoalsScored"));
		                player.setAssistsMade(resultSet.getInt("totalAssistsMade")); 
		                String skillsString = resultSet.getString("Skills");
		                if (skillsString != null) {
		                    ArrayList<String> skills = new ArrayList<>(Arrays.asList(skillsString.split(",")));
		                    player.setSkills(skills);
		                }
		                player = this.getPlayerUserDetails(player);
		                players.add(player);
		            }
		        }
		    }
		    return players;
		}
	 	
	 	public boolean assignTeamToPlayer(String playerID, String teamID) throws SQLException {
	    	
	    	if(!this.playerHasTeam(playerID)) {
		        String query = "INSERT INTO PlayerTeam (UserID, TeamID) VALUES (?, ?)";
		
		 
		        try (PreparedStatement statement = connect().prepareStatement(query)) { 
		            statement.setString(1, playerID);
		            statement.setString(2, teamID);
		
		           
		            int rowsAffected = statement.executeUpdate();
		
		
		            if (rowsAffected > 0) {
		 
		                return true;
		            } else {
		                return false; 
		            }
		        }
	    	}
	    	else
	    	{
	    		
	    		return false;
	    	}
	    }
	 	
	 	 public boolean playerHasTeam(String playerID) throws SQLException {
	         String query = "SELECT COUNT(*) FROM PlayerTeam WHERE UserID = ?";
	         try (PreparedStatement statement = connect().prepareStatement(query)) {
	             statement.setString(1, playerID);

	             try (ResultSet resultSet = statement.executeQuery()) {
	                 if (resultSet.next()) {
	                     int count = resultSet.getInt(1);
	                     return count > 0;
	                 } else {
	                     return false; 
	                 }
	             }
	         }
	     }


	    public void updatePlayer(Player player) {
	        String query = "UPDATE Player SET Position = ?, Skills = ?, MatchesPlayed = ?, " +
	                       "GoalsScored = ?, AssistsMade = ? WHERE UserID = ?";

	        try (Connection connection = connect();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setString(1, player.getPosition());

	            System.out.println(player.getSkills());
	            String skillsString = String.join(",", player.getSkills()); 
	            statement.setString(2, skillsString); 

	            statement.setInt(3, player.getMatchesPlayed());
	            statement.setInt(4, player.getGoalsScored());
	            statement.setInt(5, player.getAssistsMade());
	            
	            statement.setString(6, player.getUserID());
	            
	           

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    

}
