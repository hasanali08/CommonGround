package managers;

import java.util.ArrayList;

import commonground.League;
import commonground.LeagueTable;
import dbhandlers.LeagueDBHandler;

public class LeagueList {
	
	private ArrayList<League> list;
	private LeagueDBHandler DBHandler;
	
	LeagueList(LeagueDBHandler DBHandler) {
		this.DBHandler = DBHandler;
		list = new ArrayList<League>();
	}
	
	public League createLeague(String leagueName, String creatorID, ArrayList<String> teamlist, int totalMatches, String startDate) {
		League league = new League();
		LeagueTable leagueTable = new LeagueTable();
		leagueTable.setMaxTeams(teamlist.size());
		for(String team : teamlist) {
			leagueTable.addTeam(team);
		}
		league.setLeagueID(DBHandler.generateNewLeagueID());
		league.setLeagueName(leagueName);
		league.setLeagueTable(leagueTable);
		
		league.setMaxTeams(teamlist.size());
		try {
			this.DBHandler.insertLeague(league);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return league;
	}
	
	public boolean addTeamWin(String leagueID, String teamID, int goals_scored, int goals_conceded) {
		return this.getLeagueByID(leagueID).updateTeamWin(teamID, goals_scored, goals_conceded);
	}
	
	public boolean addTeamLoss(String leagueID, String teamID, int goals_scored, int goals_conceded) {
		return this.getLeagueByID(leagueID).updateTeamLoss(teamID, goals_scored, goals_conceded);
	}
	
	public boolean addTeamDraw(String leagueID, String teamID, int goals_scored, int goals_conceded) {
		return this.getLeagueByID(leagueID).updateTeamDraws(teamID, goals_scored, goals_conceded);
	}
	
	public League getLeagueByTeamID(String teamID) {
		
		for(League league : list) {
			if(league.getParticipatingTeamID(teamID)!=null) {
				return league;
			}
		}
		return null;
	}
	public League getLeagueByID(String leagueID) {
		
		for(League league : list) {
			if(league.getLeagueID().equals(leagueID)) {
				return league;
			}
		}
		return null;
	}

}
