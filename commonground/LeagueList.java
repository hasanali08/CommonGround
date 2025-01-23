package commonground;

import java.util.ArrayList;

public class LeagueList {
	
	private ArrayList<League> list;
	
	LeagueList() {
		list = new ArrayList<League>();
	}
	
	public League getLeagueByID(String tournamentID) {
		
		for(League league : list) {
			if(league.getTournamentID().equals(tournamentID)) {
				return league;
			}
		}
		return null;
	}

}
