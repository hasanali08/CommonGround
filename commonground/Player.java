package commonground;

import java.util.ArrayList;

public class Player extends User implements PlayerObserver{
	
	private String position;
	private ArrayList<String> skills = new ArrayList<String>();
	private int matchesPlayed;
	private int goalsScored;
	private int assistsMade;
	private String teamID;
	private ArrayList<Notification> notifications = new ArrayList<>();

	

	public Player() {
		
		position = " ";
		matchesPlayed = 0;
		goalsScored = 0;
		assistsMade = 0;
		teamID = " ";
		
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}

	public ArrayList<String> getSkills() {
		return skills;
	}

	public void setSkills(ArrayList<String> skills) {
		this.skills = skills;
	}

	public Integer getMatchesPlayed() {
		return matchesPlayed;
	}

	public void setMatchesPlayed(int matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	public Integer getGoalsScored() {
		return goalsScored;
	}

	public void setGoalsScored(Integer goalsScored) {
		this.goalsScored = goalsScored;
	}

	public Integer getAssistsMade() {
		return assistsMade;
	}

	public void setAssistsMade(Integer assistsMade) {
		this.assistsMade = assistsMade;
	}

	public String getTeamID() {
		return teamID;
	}

	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}

	@Override
	public void update(ArrayList<Notification> notifications) {
		
		this.notifications = notifications;
	}
	
	public ArrayList<Notification> getNotifications(){
		return this.notifications;
	}
	
	

	@Override
	public String getPlayerID() {
		return this.getUserID();
	}
	
	


	

}
