package commonground;

public class LeagueEntry {
	
	
	private String teamId;
	private int points;
	private int goalDifference;
	private int wins;
	private int losses;
	private int draws;
	private int matchesPlayed;
	
	public LeagueEntry(String teamId, int points, int goalDifference) {
	    this.teamId = teamId;
	    this.points = points;
	    this.goalDifference = goalDifference;
	}
	
	public LeagueEntry() {
	   
	}
	
	public String getTeamId() {
	    return teamId;
	}
	
	public int getPoints() {
	    return points;
	}
	
	public void setPoints(int points) {
	    this.points = points;
	}
	
	public int getGoalDifference() {
	    return goalDifference;
	}
	
	public void setGoalDifference(int goalDifference) {
	    this.goalDifference = goalDifference;
	}

	public void setTeamId(String string) {
		this.teamId = string;
		
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getDraws() {
		return draws;
	}

	public void setDraws(int draws) {
		this.draws = draws;
	}

	public int getMatchesPlayed() {
		return matchesPlayed;
	}

	public void setMatchesPlayed(int matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	  
}
