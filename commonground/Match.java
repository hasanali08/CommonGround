package commonground;

import java.time.LocalDate;

public class Match {
	private String matchID;
	private String matchDate;
	private String homeTeamID;
	private String awayTeamID;
	private String pitchID;
	private String matchType;
	private String matchStatus;
	private String winningTeamID;
	private String tournamentID;
	
	

	public Match() {
		
	}

	public Match(String team1, String team2) {
		
	}
	
	public Match(String matchDate, String homeTeamID, String awayTeamID, String matchType, String tournamentID) {
		
		this.matchDate = matchDate;
		this.homeTeamID = homeTeamID;
		this.awayTeamID = awayTeamID;
		this.pitchID = null;
		this.matchType = matchType;
		this.tournamentID = tournamentID;

		
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String localDate) {
		this.matchDate = localDate;
	}

	public String getHomeTeamID() {
		return homeTeamID;
	}

	public void setHomeTeamID(String homeTeamID) {
		this.homeTeamID = homeTeamID;
	}

	public String getAwayTeamID() {
		return awayTeamID;
	}

	public void setAwayTeamID(String awayTeamID) {
		this.awayTeamID = awayTeamID;
	}

	public String getPitchID() {
		return pitchID;
	}

	public void setPitchID(String pitchID) {
		this.pitchID = pitchID;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getTournamentID() {
		return tournamentID;
	}

	public void setTournamentID(String tournamentID) {
		this.tournamentID = tournamentID;
	}

	public String getWinningTeamID() {
		return winningTeamID;
	}

	public void setWinningTeamID(String winningTeamID) {
		this.winningTeamID = winningTeamID;
	}

	public String getMatchID() {
		
		return this.matchID;
	}

	public void setMatchID(String string) {
		this.matchID = string;
		
	}

	public String getMatchStatus() {
		
		return this.matchStatus;
	}
	
	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}

	
	

}
