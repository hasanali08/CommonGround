package dbhandlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import commonground.Team;

public class TeamDBHandler {
	private String connectionString;

    public TeamDBHandler(String connectionString) {
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
    
    public ArrayList<Team> getAllTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        String query = "SELECT * FROM Teams";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
            	Team team = new Team();
                team.setTeamID( resultSet.getString("TeamID"));
                team.setTeamName(resultSet.getString("TeamName"));
                team.setMaxPlayers(resultSet.getInt("maxPlayers"));
                team.setHomePitchID(resultSet.getString("homePitchID"));
                team.setWins(resultSet.getInt("wins"));
                team.setLosses(resultSet.getInt("losses"));
               
                teams.add(team);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }
     
    public void addTeam(Team team) throws SQLException {
        String query = "INSERT INTO Teams (TeamID, TeamName, maxPlayers, homePitchID, wins, losses) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connect().prepareStatement(query)) {
            statement.setString(1, team.getTeamID());
            statement.setString(2, team.getTeamName());
            statement.setInt(3, team.getMaxPlayers());
            statement.setString(4, team.getHomePitchID());
            statement.setInt(5, team.getWins());
            statement.setInt(6, team.getLosses());
            statement.executeUpdate();
        }
    }
    
    public String getTeamIdForManager(String managerUserId) throws SQLException {
        String query = "SELECT TeamID FROM ManagerTeam WHERE UserID = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, managerUserId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("TeamID");
                }
            }
        }
        return null; 
    }
    
    public void addManagerTeam(String managerUserId, String teamId) throws SQLException {
        String query = "INSERT INTO ManagerTeam (UserID, TeamID) VALUES (?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, managerUserId);
            statement.setString(2, teamId);
            statement.executeUpdate();
        }
    }
    
    public void updateTeam(Team team) throws SQLException {
        String query = "UPDATE Teams SET TeamName = ?, maxPlayers = ?, homePitchID = ?, wins = ?, losses = ? " +
                       "WHERE TeamID = ?";

        try (PreparedStatement statement = connect().prepareStatement(query)) {
            statement.setString(1, team.getTeamName());
            statement.setInt(2, team.getMaxPlayers());
            statement.setString(3, team.getHomePitchID());
            statement.setInt(4, team.getWins());
            statement.setInt(5, team.getLosses());
            statement.setString(6, team.getTeamID()); 
            

            statement.executeUpdate();
        }
    }
    
    public String generateNewTeamID() {
    	String query = "SELECT MAX(CAST(TeamID AS INT)) FROM Teams"; 
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
    
    

}
