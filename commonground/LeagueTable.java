package commonground;

import java.util.ArrayList;
import java.util.Comparator;


public class LeagueTable {

    private ArrayList<LeagueEntry> standings;
    private ArrayList<String> teams;
    private Integer numberOfTeams;
    private Integer maxTeams;

    public LeagueTable() {
        this.standings = new ArrayList<LeagueEntry>();
        this.teams = new ArrayList<String>();
        
    }

    
    public Integer getNumberOfTeams() {
		return numberOfTeams;
	}


	public void setNumberOfTeams(int numberOfTeams) {
		this.numberOfTeams = numberOfTeams;
	}


	public Integer getMaxTeams() {
		return maxTeams;
	}


	public void setMaxTeams(int maxTeams) {
		this.maxTeams = maxTeams;
	}


	public Boolean addTeam(String teamId) {
    	if(numberOfTeams<maxTeams) {
    		
	        standings.add(new LeagueEntry(teamId, 0, 0));
	        this.numberOfTeams+=1;
	        this.teams.add(teamId);
	        return true;
	        
    	}
    	else {
    		return false;
    	}
    }

    
    public boolean updateTeamWin(String teamId, int goal_difference) {
        for (LeagueEntry entry : standings) {
            if (entry.getTeamId().equals(teamId)) {
                entry.setPoints(entry.getPoints()+3);
                entry.setGoalDifference(entry.getGoalDifference()+goal_difference);
                entry.setWins(entry.getWins()+1);
                return true;
            }
        }
        return false;
    }
    
    public boolean updateTeamLoss(String teamId, int goal_difference) {
        for (LeagueEntry entry : standings) {
            if (entry.getTeamId().equals(teamId)) {
                entry.setPoints(entry.getPoints()+0);
                entry.setGoalDifference(entry.getGoalDifference()+goal_difference);
                entry.setLosses(entry.getLosses()+1);
                return true;
            }
        }
        return false;
    }
    
    public boolean updateTeamDraws(String teamId, int goal_difference) {
        for (LeagueEntry entry : standings) {
            if (entry.getTeamId().equals(teamId)) {
                entry.setPoints(entry.getPoints()+1);
                entry.setGoalDifference(entry.getGoalDifference()+goal_difference);
                entry.setDraws(entry.getDraws()+1);
                return true;
            }
        }
        return false;
    }
    
    public void sortStandings() {
    	
    	standings.sort(Comparator
                .comparing(LeagueEntry::getPoints)
                .thenComparing(LeagueEntry::getGoalDifference)
                .reversed());
    }
    public ArrayList<LeagueEntry> getSortedStandings() {
        this.sortStandings();
        return standings;
    }

    
    public void displayStandings() {
        System.out.println("Team ID | Points | Goal Difference");
        for (LeagueEntry entry : getSortedStandings()) {
            System.out.printf("%7d | %6d | %15d\n", entry.getTeamId(), entry.getPoints(), entry.getGoalDifference());
        }
    }
    
    public ArrayList<String> getTeamsByID() {
    	
    	this.teams.clear();
    	for(LeagueEntry entry: standings) {
    		teams.add(entry.getTeamId());
    	}
    	
    	return this.teams;
    }

    public void setSortedStandings(ArrayList<LeagueEntry> standings) {
    	this.standings = standings;
    	this.sortStandings();
    	
    }


	
}

