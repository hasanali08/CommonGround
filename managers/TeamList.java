package managers;

import java.sql.SQLException;
import java.util.ArrayList;

import commonground.Player;
import commonground.PlayerInfo;
import commonground.Team;
import dbhandlers.TeamDBHandler;

public class TeamList {
	
	ArrayList<Team> list;
	public TeamDBHandler DBHandler;
	PlayerInfo playerinfo;
	
	public TeamList(TeamDBHandler DBHandler, PlayerInfo playerinfo) {
		list = new ArrayList<Team>();
		this.DBHandler = DBHandler;
		this.playerinfo = playerinfo;
		this.refreshList();
		
	}
	
	public Team getTeamByID(String teamID) {
		this.refreshList();
		for(Team team : list) {
			if(team.getTeamID().equals(teamID)) {
				return team;
			}
		}
		return null;
	}
	public void refreshList() {
		this.list =  this.DBHandler.getAllTeams();
		for(Team team : this.list) {
			team.setTeamRoster(playerinfo.getPlayersByTeam(team.getTeamID()));
		}
		
		
	}
	
	public Team getTeamByManager(String managerID) throws SQLException {
		String teamID = DBHandler.getTeamIdForManager(managerID);
		if(teamID==null) {
			return null;
		}
		else {
			return this.getTeamByID(teamID);
		}
	}
	
	
	
	public Team createTeam(String teamName, String managerID) {
		try {
			if(DBHandler.getTeamIdForManager(managerID)!=null) {
				System.out.println("Manager already has a team ");
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("New team created!");
		Team team = new Team();
		team.setTeamID(DBHandler.generateNewTeamID());
		team.setTeamName(teamName);
		team.setHomePitchID(null);
		team.setWins(0);
		team.setLosses(0);
		team.setTeamRoster(null);
		
		this.list.add(team);
		try {
			DBHandler.addTeam(team);
			try {
				this.DBHandler.addManagerTeam(managerID, team.getTeamID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return team;
		
	}
	
	public boolean addPlayerToTeam(Player player, String teamId) throws SQLException {
		
		this.refreshList();
		
		if(this.getTeamByID(teamId).addPlayer(player)) {
			this.playerinfo.assignTeamToPlayer(player.getPlayerID(), teamId);
			return true;
		}
		else {
			return false;
		}
		
		
		
		  
	    
	}
	
	 public boolean updateTeam(Team team, String managerID) {
	        try {
	           
	            String teamID = DBHandler.getTeamIdForManager(managerID);
	            if (teamID == null || !teamID.equals(team.getTeamID())) {
	                System.out.println("Manager is not authorized to update this team.");
	                return false;
	            }

	            
	            DBHandler.updateTeam(team);

	           
	            for (int i = 0; i < list.size(); i++) {
	                if (list.get(i).getTeamID().equals(team.getTeamID())) {
	                    list.set(i, team); 
	                    break;
	                }
	            }

	            return true;

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	
	public Team getTeamByName(String teamName) {
		this.refreshList();
		for(Team team : list) {
			if(team.getTeamName().equals(teamName)) {
				return team;
			}
		}
		return null;
	}
	
	public ArrayList<String> getTeamMemberNames(String teamID) {
		this.refreshList();
		for(Team team : list) {
			if(team.getTeamID().equals(teamID)) {
				ArrayList<String> returnList = new ArrayList<String>(); 
				ArrayList<Player> roster = team.getTeamRoster();
				for(Player player : roster) {
					String playerName = player.getFirstName()+" "+player.getLastName();
					returnList.add(playerName);
				}
				
				return returnList;
				
			}
		}
		
		return null;
	}
	
	public ArrayList<String> getAllTeamNames(){
		ArrayList<String> teamNames = new ArrayList<String>();
		for(Team team : list) {
			teamNames.add( team.getTeamName() );
		}
		return teamNames;
	}
	
	public ArrayList<Team> getAllTeams(){
		return this.list;
	}
	
	

}
