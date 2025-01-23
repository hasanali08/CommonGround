package managers;

import java.sql.SQLException;
import java.util.ArrayList;

import commonground.PlayerStats;
import dbhandlers.PlayerStatsDBHandler;

public class PlayerStatsList {

    private ArrayList<PlayerStats> list;
    PlayerStatsDBHandler DBHandler;

    public PlayerStatsList(PlayerStatsDBHandler DBHandler) {
        this.list = new ArrayList<PlayerStats>();
        this.DBHandler = DBHandler;
    }

    public PlayerStats getPlayerStatsByMatch(String playerID, String matchID) throws SQLException {
    
        PlayerStats stats = DBHandler.getPlayerStats(playerID); 
        if (stats != null && stats.getMatchID().equals(matchID)) {
            return stats;
        }

    
        for (PlayerStats playerstats : list) {
            if (playerstats.getPlayerID().equals(playerID) && playerstats.getMatchID().equals(matchID)) {
                return playerstats;
            }
        }

        return null;
    }

    public void createPlayerStats(String playerID, String matchID, int goalsScored, int assistsMade, float matchRating, int minutesPlayed) throws SQLException {
        PlayerStats playerstat = new PlayerStats();
        playerstat.setStatID(DBHandler.generateNewStatID());
        playerstat.setMatchID(matchID);
        playerstat.setPlayerID(playerID);
        playerstat.setGoalsScored(goalsScored);
        playerstat.setAssistsMade(assistsMade);
        playerstat.setMinutesPlayed(minutesPlayed);
        playerstat.setMatchRating(matchRating);


        DBHandler.addPlayerStats(playerstat);

 
        list.add(playerstat); 
    }

   
    public void updatePlayerStats(PlayerStats playerStats) throws SQLException {
       
        DBHandler.updatePlayerStats(playerStats);

    }

    public void deletePlayerStats(String statID) throws SQLException {
     
        DBHandler.deletePlayerStats(statID);

    }
}