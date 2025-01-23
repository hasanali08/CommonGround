package commonground;

import java.util.ArrayList;

public class KnockoutTournamentList {
	
	private ArrayList<KnockoutTournament> list;
	
	KnockoutTournamentList() {
		list = new ArrayList<KnockoutTournament>();
	}
	
	public KnockoutTournament getTournamentByID(String tournamentID) {
		
		for(KnockoutTournament tournament : list) {
			if(tournament.getTournamentID().equals(tournamentID)) {
				return tournament;
			}
		}
		return null;
	}
}
