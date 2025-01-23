package dbhandlers;

import java.sql.SQLException;
import java.util.ArrayList;

import commonground.Match;

public interface MatchDataHandler {
	
	public void addMatch(Match match) throws SQLException;
	public String generateNewMatchID() throws SQLException;
	public ArrayList<Match> getAllMatches() throws SQLException;
	public ArrayList<Match> getUpcomingLeagueMatches(String teamID, String leagueID) throws SQLException;
	public ArrayList<Match> getPastLeagueMatches(String teamID, String leagueID) throws SQLException;
	public ArrayList<Match> getUpcomingMatchesForPitch(String pitchID) throws SQLException;
	public ArrayList<Match> getPastMatchesForPitch(String pitchID) throws SQLException;
	public void updateMatchInfo(Match match) throws SQLException; 
	

}
