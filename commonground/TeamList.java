package commonground;

import java.util.ArrayList;

public class TeamList {
	
	ArrayList<Team> list;
	
	TeamList() {
		list = new ArrayList<Team>();
	}
	
	public Team getTeamByID(String teamID) {
		for(Team team : list) {
			if(team.getTeamID().equals(teamID)) {
				return team;
			}
		}
		return null;
	}
	
	public void addTeam(Team team) {
		list.add(team);
	}

}
