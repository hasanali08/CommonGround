package managers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import commonground.Match;
import dbhandlers.MatchDataHandler;

public class MatchList {
	
    private MatchDataHandler dbHandler;
    private ArrayList<Match> list; 

    public MatchList(MatchDataHandler dbHandler) {
        this.dbHandler = dbHandler;
        this.list = new ArrayList<>();
        loadMatchesFromDatabase(); 
    }

    private void loadMatchesFromDatabase() {     
       try {
		this.list = dbHandler.getAllMatches();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }

   

    public Match createMatch(String matchDate, String homeTeamID, 
            String awayTeamID, String pitchID, String matchType, 
            String leagueID, String winningTeamID, String matchStatus) throws SQLException {

			Match newMatch = new Match();
			newMatch.setMatchID(dbHandler.generateNewMatchID());
			newMatch.setMatchDate(matchDate);
			newMatch.setHomeTeamID(homeTeamID);
			newMatch.setAwayTeamID(awayTeamID);
			newMatch.setPitchID(pitchID);
			newMatch.setMatchType(matchType);
			System.out.println(newMatch.getMatchType());
			newMatch.setTournamentID(leagueID); 
			newMatch.setWinningTeamID(winningTeamID);
			newMatch.setMatchStatus(matchStatus);
			
			dbHandler.addMatch(newMatch);
			list.add(newMatch); 
			return newMatch;
			}

    public ArrayList<Match> getUpcomingMatches(String teamID) {
        ArrayList<Match> upcomingMatches = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the pattern if needed
        for (Match match : list) {
            if ((match.getHomeTeamID().equals(teamID) || match.getAwayTeamID().equals(teamID)) &&
                LocalDate.parse(match.getMatchDate(), formatter).isAfter(LocalDate.now())) { 
                upcomingMatches.add(match);
            }
        }
        return upcomingMatches;
    }

    public ArrayList<Match> getPastMatches(String teamID) {
        ArrayList<Match> pastMatches = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust the pattern if needed
        for (Match match : list) {
            if ((match.getHomeTeamID().equals(teamID) || match.getAwayTeamID().equals(teamID)) &&
                LocalDate.parse(match.getMatchDate(), formatter).isBefore(LocalDate.now())) { 
                pastMatches.add(match);
            }
        }
        return pastMatches;
    }
    
    public ArrayList<Match> getUpcomingLeagueMatches(String teamID, String leagueID) throws SQLException {
        return dbHandler.getUpcomingLeagueMatches(teamID, leagueID);
    }

    public ArrayList<Match> getPastLeagueMatches(String teamID, String leagueID) throws SQLException {
        return dbHandler.getPastLeagueMatches(teamID, leagueID);
    }
    
    public ArrayList<Match> getUpcomingMatchesForPitch(String PitchID) throws SQLException{
    	return this.dbHandler.getUpcomingMatchesForPitch(PitchID);
    	
    }
    
    public ArrayList<Match> getPastMatchesForPitch(String PitchID) throws SQLException{
    	return this.dbHandler.getPastMatchesForPitch(PitchID);
    }

    

    public void updateMatchInfo(Match match) throws SQLException {
        dbHandler.updateMatchInfo(match);
        // Update the match in the cache
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMatchID().equals(match.getMatchID())) {
                list.set(i, match); // Replace with the updated match
                break;
            }
        }
    }

    
}
