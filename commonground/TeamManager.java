package commonground;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import managers.NotificationList;
import managers.TeamList;

public class TeamManager extends User implements ManagerNotifier{
	
	private TeamList teamlist;
	private String teamID;
	private String experience;
	private Team team;
	private ArrayList<PlayerObserver> observers;
	
	
	public TeamManager() {
		observers = new ArrayList<PlayerObserver>();
		
		
	}
	
	public void getTeamList(TeamList teamlist) {
		this.teamlist = teamlist;
		
	}
	
	public void createTeam(String teamName) {
		
		team = this.teamlist.createTeam(teamName, this.getUserID());
		if(team!=null) {
			try {
				System.out.println("Manager got new team");
				this.teamlist.DBHandler.addManagerTeam(this.getUserID(), this.team.getTeamID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void addTeamMember(Player player) {
		try {
			this.teamlist.addPlayerToTeam(player, teamID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeTeamMember(Player player) {
		
	}
	
	public ArrayList<Player> getTeamRoster() {
		
		return this.teamlist.getTeamByID(team.getTeamID()).getTeamRoster();
		
	}

	@Override
	public void registerPlayer(PlayerObserver player) {
		observers.add(player);
		
	}

	@Override
	public void unregisterPlayer(PlayerObserver player) {
		for(PlayerObserver players : observers) {
			if(players.getPlayerID().equals(player.getPlayerID())) {
				observers.remove(player);
				return;
			}
		}
		
		
	}
	
	

	@Override
	public void notifyPlayers(String message, NotificationList notificationList) throws SQLException {
		
	
		Notification notification = notificationList.createNotification(message, this.getUserID()); 
		
        
        String teamId = this.teamID; 

        ArrayList<Notification> notifications = notificationList.getNotificationsForTeam(teamId);
		for(PlayerObserver players : observers) {
			players.update(notifications);
		}
		
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public void setTeamID(String teamID2) {
		this.teamID = teamID2;
		
	}
	
	public String getTeamID() {
		return this.teamID;
	}
	
}
