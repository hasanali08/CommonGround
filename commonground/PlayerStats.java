package commonground;

public class PlayerStats {
	
	private String playerID;
	private String statID;
	private String matchID;
	private int goalsScored;
	private int assistsMade;
	private float matchRating;
	private int minutesPlayed;
	
	public PlayerStats() {
		
		playerID = " ";
		statID = " ";
		matchID = " ";
		goalsScored = 0;
		assistsMade = 0;
		minutesPlayed = 0;
		matchRating = 0;
	}

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public String getMatchID() {
		return matchID;
	}

	public void setMatchID(String matchID) {
		this.matchID = matchID;
	}

	public int getGoalsScored() {
		return goalsScored;
	}

	public void setGoalsScored(int goalsScored) {
		this.goalsScored = goalsScored;
	}

	public int getAssistsMade() {
		return assistsMade;
	}

	public void setAssistsMade(int assistsMade) {
		this.assistsMade = assistsMade;
	}

	public float getMatchRating() {
		return matchRating;
	}

	public void setMatchRating(float matchRating2) {
		this.matchRating = matchRating2;
	}

	public Integer getMinutesPlayed() {
		return minutesPlayed;
	}

	public void setMinutesPlayed(int minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}

	public String getStatID() {
		return statID;
	}

	public void setStatID(String statID) {
		this.statID = statID;
	}
	
	

}
