package managers;

import java.sql.SQLException;
import java.util.ArrayList;

import commonground.Player;
import commonground.PlayerInfo;
import dbhandlers.PlayerDBHandler;

public class PlayerList implements PlayerInfo{
	
	ArrayList<Player> list;
	PlayerDBHandler DBHandler;
	
	public PlayerList(PlayerDBHandler DBHandler) {
		
		list = new ArrayList<Player>();
		this.DBHandler = DBHandler;
		
	}
	public ArrayList<Player> getPlayersWithNoTeam(){
		return this.DBHandler.getPlayersWithoutTeam();
	}
	public ArrayList<Player> getAllPlayers(){
		this.refreshlist();
		return this.list;
	}
	public boolean addPlayer(Player player) {
		
		return this.DBHandler.addPlayer(player);
		
	}
	public void refreshlist() {
		this.list =  this.DBHandler.getAllPlayers();
	}
	
	public Player getPlayerByID(String playerID) {
		this.refreshlist();
		for(Player player : this.list) {
			if(player.getUserID().equals(playerID)) {
				return player;
			}
		}
		
		return null;
	}
	
	public ArrayList<Player> getPlayersByTeam(String TeamID) {
		try {
			return this.DBHandler.getPlayersForTeam(TeamID);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPlayerTeamID(String playerID) {
		try {
			return this.DBHandler.getPlayerTeamID(playerID);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean assignTeamToPlayer(String playerID, String teamID) {
		try {
			return this.DBHandler.assignTeamToPlayer(playerID, teamID);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	public void updatePlayer(Player updatedPlayer) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUserID().equals(updatedPlayer.getUserID())) {
                list.set(i, updatedPlayer); 
                DBHandler.updatePlayer(updatedPlayer); 
                break;
            }
        }
    }

}
