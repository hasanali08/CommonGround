package dbhandlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commonground.League;
import commonground.LeagueEntry;
import commonground.LeagueTable;
import commonground.Match;

public class LeagueDBHandler {
	private String connectionString;
	

    public LeagueDBHandler(String connectionString) {
        
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
    
    public String generateNewLeagueID() {
//        String query = "SELECT MAX(CAST(SUBSTRING(LeagueID, 3) AS UNSIGNED)) FROM Leagues";
    	String query = "SELECT MAX(CAST(SUBSTRING(LeagueID, 3, LEN(LeagueID) - 2) AS INT)) FROM Leagues";
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
        
        return "LG" + newNumber;
    }

   
    public void insertLeague(League league) throws Exception {
        String query = "INSERT INTO Leagues (LeagueID, LeagueName, MaxTeams) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, league.getLeagueID());
            stmt.setString(2, league.getLeagueName());
            stmt.setInt(3, league.getMaxTeams());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    String leagueID = rs.getString(1);

                    saveLeagueMatches(leagueID, league.getMatchSchedule());
                    saveLeagueStandings(leagueID, league.getSortedStandings());
                }
            }
        }
    }

   
    private void saveLeagueMatches(String leagueID, List<Match> matches) throws Exception {
        String query = "INSERT INTO LeagueMatches (MatchID, LeagueID) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Match match : matches) {
                stmt.setString(1, match.getMatchID());
                stmt.setString(2, leagueID);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    
    private void saveLeagueStandings(String leagueID, ArrayList<LeagueEntry> standings) throws Exception {
        String query = "INSERT INTO LeagueStandings (LeagueID, TeamID, Points, GoalDifference, Wins, Losses, Draws) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (LeagueEntry entry : standings) {
                stmt.setString(1, leagueID);
                stmt.setString(2, entry.getTeamId());
                stmt.setInt(3, entry.getPoints());
                stmt.setInt(4, entry.getGoalDifference());
                stmt.setInt(5, entry.getWins());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

   
    public ArrayList<League> fetchLeagues() throws Exception {
        ArrayList<League> leagues = new ArrayList<>();
        String query = "SELECT * FROM Leagues";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                League league = new League();
                league.setLeagueID(rs.getString("TournamentID"));
                league.setLeagueName(rs.getString("LeagueName"));
                league.setMaxTeams(rs.getInt("MaxTeams"));
                league.setMatchSchedule(fetchLeagueMatches(rs.getString("LeagueID")));
                league.setLeagueTable(fetchLeagueStandings(rs.getString("LeagueID")));
                leagues.add(league);
            }
        }
        return leagues;
    }

   
    private ArrayList<Match> fetchLeagueMatches(String leagueID) throws Exception {
        ArrayList<Match> matches = new ArrayList<>();
        String query = "SELECT m.* FROM Matches m INNER JOIN LeagueMatches lm ON m.MatchID = lm.MatchID WHERE lm.LeagueID = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, leagueID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Match match = new Match();
                    match.setMatchID(rs.getString("MatchID"));
                    match.setMatchDate(rs.getString("MatchDate"));
                    match.setHomeTeamID(rs.getString("HomeTeamID"));
                    match.setAwayTeamID(rs.getString("AwayTeamID"));
                    match.setPitchID(rs.getString("PitchID"));
                    match.setMatchType(rs.getString("MatchType"));
                    match.setWinningTeamID(rs.getString("WinningTeamID"));
                    matches.add(match);
                }
            }
        }
        return matches;
    }

    
    private LeagueTable fetchLeagueStandings(String leagueID) throws Exception {
        LeagueTable leagueTable = new LeagueTable();
        ArrayList<LeagueEntry> standings = new ArrayList<>();
        String query = "SELECT ls.TeamID, t.TeamName, ls.Points, ls.GoalDifference FROM LeagueStandings ls " +
                       "JOIN Teams t ON ls.TeamID = t.TeamID WHERE ls.LeagueID = ? ORDER BY ls.Points DESC, ls.GoalDifference DESC";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, leagueID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LeagueEntry entry = new LeagueEntry();
                    entry.setTeamId(rs.getString("TeamID"));
                    entry.setPoints(rs.getInt("Points"));
                    entry.setGoalDifference(rs.getInt("GoalDifference"));
                    standings.add(entry);
                }
            }
        }
        leagueTable.setSortedStandings(standings);
        leagueTable.setNumberOfTeams(standings.size());
        return leagueTable;
    }

    public void updateLeagueStandings(String leagueID, String teamID, int pointsDelta, int goalDifferenceDelta) throws Exception {
        String query = "UPDATE LeagueStandings SET Points = Points + ?, GoalDifference = GoalDifference + ? " +
                       "WHERE LeagueID = ? AND TeamID = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pointsDelta);
            stmt.setInt(2, goalDifferenceDelta);
            stmt.setString(3, leagueID);
            stmt.setString(4, teamID);
            stmt.executeUpdate();
        }
    }
}

