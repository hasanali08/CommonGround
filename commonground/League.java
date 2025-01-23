package commonground;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class League {
	
	private String leagueID;
	private String leagueName;
	private LeagueTable leagueTable;
	private String winningTeam;
	private List<Match> matchSchedule;
	private ArrayList<Pitch> pitchVenues;
	
	public League() {
		
		leagueTable = new LeagueTable();
		matchSchedule = new ArrayList<Match>();
		pitchVenues = new ArrayList<Pitch>();
	}
	
	public boolean addTeam(String teamID) {
		return leagueTable.addTeam(teamID);
	}
	
	public boolean updateTeamWin(String teamID, int goals_scored, int goals_conceded) {
		
		boolean returnValue =  leagueTable.updateTeamWin(teamID, (goals_scored-goals_conceded));
		this.updateWinningTeam();
		return returnValue;
		
	}
	
	public String getParticipatingTeamID(String teamID) {
		
		ArrayList<String> list =  this.leagueTable.getTeamsByID();
		for(String team : list) {
			if(team.equals(teamID)) {
				return team;
			}
		}
		
		return null;
		
	}
	
	public boolean updateTeamLoss(String teamID, int goals_scored, int goals_conceded) {
		
		boolean returnValue =  leagueTable.updateTeamLoss(teamID, (goals_scored-goals_conceded));
		this.updateWinningTeam();
		return returnValue;
		
	}
	
	public boolean updateTeamDraws(String teamID, int goals_scored, int goals_conceded) {
		
		boolean returnValue =  leagueTable.updateTeamDraws(teamID, (goals_scored-goals_conceded));
		this.updateWinningTeam();
		return returnValue;
		
	}
	
	public void updateWinningTeam() {
		this.winningTeam = leagueTable.getSortedStandings().getFirst().getTeamId();
	}
	
	public String getWinningTeam() {
		return this.winningTeam;
	}
	
	
	public ArrayList<Pitch> getPitchVenues() {
		return pitchVenues;
	}

	public void setPitchVenues(ArrayList<Pitch> pitchVenues) {
		this.pitchVenues = pitchVenues;
	}

	public int getMaxTeams() {
		
		return this.leagueTable.getMaxTeams();
	}

	public List<Match> getMatchSchedule() {
		
		return this.matchSchedule;
	}

	public ArrayList<LeagueEntry> getSortedStandings() {
		
		return this.leagueTable.getSortedStandings();
	}
	
	public void setStandings() {
		
	}

	public void setMaxTeams(int int1) {
		this.leagueTable.setMaxTeams(int1);
		
	}

	public void setMatchSchedule(ArrayList<Match> fetchLeagueMatches) {
		this.matchSchedule = fetchLeagueMatches;
		
	}

	public void setLeagueTable(LeagueTable fetchLeagueStandings) {
		this.leagueTable = fetchLeagueStandings;
		
	}

	public void setLeagueID(String leagueID) {
		this.leagueID = leagueID;
	}

	public String getLeagueID() {
		
		return this.leagueID;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	
	
	

}
