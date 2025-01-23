package commonground;

import java.util.ArrayList;

public interface PlayerInfo {
	public Player getPlayerByID(String playerID);
	public ArrayList<Player> getPlayersByTeam(String TeamID);
	public String getPlayerTeamID(String playerID);
	public boolean assignTeamToPlayer(String playerID, String teamID);
}
