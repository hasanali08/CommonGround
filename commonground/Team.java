package commonground;

import java.util.ArrayList;

public class Team {
	
	private String teamID;
	
	private String teamName;
	
	private ArrayList<Player> teamRoster;
	
	private int maxPlayers;
	
	private String homePitchID;
	
	private int wins;
	
	private int losses;
	
	public Team() {
		
		maxPlayers = 8;
	}

	public ArrayList<Player> getTeamRoster() {
		return teamRoster;
	}

	public void setTeamRoster(ArrayList<Player> teamRoster) {
		this.teamRoster = teamRoster;
	}
	
	public boolean addPlayer(Player player) {
		
		if(teamRoster.size()<this.maxPlayers) {
			
			teamRoster.add(player);
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void removePlayer(Player rplayer) {
		
		if(teamRoster.size()>0) {
			
			for(Player player : teamRoster) {
				if(player.getUserID().equals(rplayer.getPlayerID())) {
					teamRoster.remove(player);
					break;
				}
			}
			
		}
	}

	public String getHomePitchID() {
		return homePitchID;
	}

	public void setHomePitchID(String homePitchID) {
		this.homePitchID = homePitchID;
	}

	public String getTeamID() {
		return teamID;
	}

	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public Integer getLosses() {
		return losses;
	}

	public void setLosses(Integer losses) {
		this.losses = losses;
	}

	public int getMaxPlayers() {
		
		return this.maxPlayers;
	}

	public void setMaxPlayers(int int1) {
		this.maxPlayers = int1;
		
	}

}
