package dbhandlers;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import commonground.Match;

public class MatchDBHandler implements MatchDataHandler{
    private String connection;

    public MatchDBHandler(String connection) throws SQLException {
        this.connection = connection;
    }
    
    private Connection connect() {
    	 try {
			return DriverManager.getConnection(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    public String generateNewMatchID() throws SQLException{
        String query = "SELECT MAX(CAST(MatchID AS INT)) FROM Matches";
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
    

    public void addMatch(Match match) throws SQLException {
        String query = "INSERT INTO Matches (MatchID, MatchDate, HomeTeamID, AwayTeamID, " +
                       "PitchID, MatchType, WinningTeamID, MatchStatus) " + 
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"; 

        try (PreparedStatement statement = connect().prepareStatement(query)) {
            statement.setString(1, match.getMatchID());
            statement.setString(2, match.getMatchDate());
            statement.setString(3, match.getHomeTeamID());
            statement.setString(4, match.getAwayTeamID());
            statement.setString(5, match.getPitchID());
            statement.setString(6, match.getMatchType());
            System.out.println(match.getMatchType());
            statement.setString(7, match.getWinningTeamID()); 
            statement.setString(8, match.getMatchStatus()); 
            statement.executeUpdate();
        }
    }
    
    public ArrayList<Match> getAllMatches() throws SQLException {
        ArrayList<Match> allMatches = new ArrayList<>();
        String query = "SELECT * FROM Matches";

        try (PreparedStatement statement = connect().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                allMatches.add(createMatchFromResultSet(resultSet));
            }
        }
        return allMatches;
    }
    
    public ArrayList<Match> getUpcomingLeagueMatches(String teamID, String leagueID) throws SQLException {
        ArrayList<Match> upcomingMatches = new ArrayList<>();
        String query = "SELECT m.* " +
                       "FROM Matches m " +
                       "JOIN LeagueMatches lm ON m.MatchID = lm.MatchID " +
                       "WHERE (m.HomeTeamID = ? OR m.AwayTeamID = ?) " +
                       "AND lm.LeagueID = ? AND CONVERT(date, m.MatchDate) >= CONVERT(date, ?)";

        try (PreparedStatement statement = connect().prepareStatement(query)) {
            statement.setString(1, teamID);
            statement.setString(2, teamID);
            statement.setString(3, leagueID);
            statement.setString(4, LocalDate.now().toString()); // Set today's date as string
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    upcomingMatches.add(createMatchFromResultSet(resultSet));
                }
            }
        }
        return upcomingMatches;
    }
    
    public ArrayList<Match> getPastLeagueMatches(String teamID, String leagueID) throws SQLException {
        ArrayList<Match> pastMatches = new ArrayList<>();
        String query = "SELECT m.* " +
                       "FROM Matches m " +
                       "JOIN LeagueMatches lm ON m.MatchID = lm.MatchID " +
                       "WHERE (m.HomeTeamID = ? OR m.AwayTeamID = ?) " +
                       "AND lm.LeagueID = ? AND CONVERT(date, m.MatchDate) < CONVERT(date, ?)";

        try (PreparedStatement statement = connect().prepareStatement(query)) {
            statement.setString(1, teamID);
            statement.setString(2, teamID);
            statement.setString(3, leagueID);
            statement.setString(4, LocalDate.now().toString()); // Set today's date as string
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    pastMatches.add(createMatchFromResultSet(resultSet));
                }
            }
        }
        return pastMatches;
    }
    
    public void updateMatchInfo(Match match) throws SQLException {
        String query = "UPDATE Matches SET MatchDate = ?, HomeTeamID = ?, AwayTeamID = ?, " +
                       "PitchID = ?, MatchType = ?, LeagueID = ?, WinningTeamID = ?, MatchStatus = ? " + // Add MatchStatus
                       "WHERE MatchID = ?";

        try (PreparedStatement statement = connect().prepareStatement(query)) {
            statement.setString(1, match.getMatchDate());
            statement.setString(2, match.getHomeTeamID());
            statement.setString(3, match.getAwayTeamID());
            statement.setString(4, match.getPitchID());
            statement.setString(5, match.getMatchType());
            statement.setString(6, match.getTournamentID());
            statement.setString(7, match.getWinningTeamID());
            statement.setString(8, match.getMatchStatus()); // Set MatchStatus
            statement.setString(9, match.getMatchID()); // The WHERE clause to identify the match
            statement.executeUpdate();
        }
    }
    
    public String getLeagueID(String MatchID) {
    	String query = "SELECT LeagueID FROM LeagueMatches WHERE MatchID = ?";
        PreparedStatement statement;
		try {
			statement = connect().prepareStatement(query);
			statement.setString(1, MatchID);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
			    return resultSet.getString("LeagueID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return null;
    }
    
    public ArrayList<Match> getUpcomingMatchesForPitch(String pitchID) throws SQLException{
        String query = "SELECT * FROM Matches WHERE PitchID = ? AND MatchDate >= ?";
        ArrayList<Match> matches = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, pitchID);
            statement.setObject(2, LocalDate.now()); 

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    matches.add(createMatchFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matches;
    }
    
    public ArrayList<Match> getPastMatchesForPitch(String pitchID) throws SQLException{
        String query = "SELECT * FROM Matches WHERE PitchID = ? AND MatchDate < ?";
        ArrayList<Match> matches = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, pitchID);
            statement.setObject(2, LocalDate.now()); // Today's date

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    matches.add(createMatchFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matches;
    }


    private Match createMatchFromResultSet(ResultSet resultSet) throws SQLException {
        Match match = new Match();
        match.setMatchID(resultSet.getString("MatchID")); 
        match.setMatchDate(resultSet.getString("MatchDate"));
        match.setHomeTeamID(resultSet.getString("HomeTeamID"));
        match.setAwayTeamID(resultSet.getString("AwayTeamID"));;
        match.setPitchID(resultSet.getString("PitchID"));
        match.setMatchType(resultSet.getString("MatchType"));;
        match.setTournamentID(this.getLeagueID(match.getMatchID()));
        match.setWinningTeamID(resultSet.getString("WinningTeamID"));;
        match.setMatchStatus(resultSet.getString("MatchStatus")); // Get MatchStatus
        return match;
    }
}