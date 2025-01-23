package dbhandlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commonground.PlayerStats;

public class PlayerStatsDBHandler {

    private String connectionString;

    public PlayerStatsDBHandler(String connectionString) {
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

    public String generateNewStatID() {
        String query = "SELECT MAX(CAST(StatID AS INT)) FROM PlayerStats";
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

    public boolean addPlayerStats(PlayerStats stats) {
        String query = "INSERT INTO PlayerStats (StatID, UserID, MatchID, goalsScored, assistsMade, matchRating, minutesPlayed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, stats.getStatID());
            statement.setString(2, stats.getPlayerID());
            statement.setString(3, stats.getMatchID());
            statement.setInt(4, stats.getGoalsScored());
            statement.setInt(5, stats.getAssistsMade());
            statement.setFloat(6, stats.getMatchRating());
            statement.setInt(7, stats.getMinutesPlayed());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PlayerStats getPlayerStats(String statID) throws SQLException {
        String query = "SELECT * FROM PlayerStats WHERE StatID = ?";

        try (PreparedStatement statement = connect().prepareStatement(query)) {
            statement.setString(1, statID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    PlayerStats stats = new PlayerStats();
                    stats.setStatID(resultSet.getString("StatID"));
                    stats.setPlayerID(resultSet.getString("UserID"));
                    stats.setMatchID(resultSet.getString("MatchID"));
                    stats.setGoalsScored(resultSet.getInt("goalsScored"));
                    stats.setAssistsMade(resultSet.getInt("assistsMade"));
                    stats.setMatchRating(resultSet.getFloat("matchRating"));
                    stats.setMinutesPlayed(resultSet.getInt("minutesPlayed"));
                    return stats;
                } else {
                    return null; 
                }
            }
        }
    }

    public boolean updatePlayerStats(PlayerStats stats) {
        String query = "UPDATE PlayerStats SET UserID = ?, MatchID = ?, goalsScored = ?, " +
                "assistsMade = ?, matchRating = ?, minutesPlayed = ? WHERE StatID = ?";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, stats.getPlayerID());
            statement.setString(2, stats.getMatchID());
            statement.setInt(3, stats.getGoalsScored());
            statement.setInt(4, stats.getAssistsMade());
            statement.setFloat(5, stats.getMatchRating());
            statement.setInt(6, stats.getMinutesPlayed());
            statement.setString(7, stats.getStatID());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePlayerStats(String statID) {
        String query = "DELETE FROM PlayerStats WHERE StatID = ?";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, statID);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}