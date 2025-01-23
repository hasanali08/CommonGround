package commonground;

import java.sql.SQLException;
import java.util.ArrayList;

import dbhandlers.MatchDBHandler;
import dbhandlers.MatchDataHandler;
import dbhandlers.PitchDBHandler;
import dbhandlers.PitchDataHandler;
import dbhandlers.PlayerDBHandler;
import dbhandlers.TeamDBHandler;
import managers.MatchList;
import managers.PitchList;
import managers.PlayerList;
import managers.TeamList;

public class TestMain{
	public static void main(String[] args) {
		MatchDataHandler DBHandler;
		TeamDBHandler teamDBHandler;
		PitchDataHandler pitchDBHandler;
		PlayerDBHandler playerDBHandler;
		String connection = "jdbc:sqlserver://Ahmed-PC\\SQLEXPRESS;databaseName=CommonGround;encrypt=false;IntegratedSecurity=true";
		
		try {
			 DBHandler = new MatchDBHandler(connection);
			 teamDBHandler = new TeamDBHandler(connection);
			 pitchDBHandler = new PitchDBHandler(connection);
			 playerDBHandler = new PlayerDBHandler(connection);
			 
			 MatchList matchlist = new MatchList(DBHandler);
			 
			 PlayerInfo playerlist = new PlayerList(playerDBHandler);
			 
			 TeamList teamlist = new TeamList(teamDBHandler, playerlist);
			 
			 teamlist.createTeam("Alpha Squad", "1");
			 teamlist.addPlayerToTeam(playerlist.getPlayerByID("3"), "1");
			 
			 System.out.println(teamlist.getTeamByName("TeamA").getTeamRoster().getFirst().getPosition());
			 
			 
			 
			 PitchList pitchlist = new PitchList(pitchDBHandler);
			 
			 
			 System.out.println(teamlist.getTeamByID("1").getTeamName());
			 System.out.println(teamlist.getTeamByID("1").getHomePitchID());
			 System.out.println(pitchlist.getPitchByID(teamlist.getTeamByID("1").getHomePitchID()).getPitchName());
			 for(Match match : matchlist.getUpcomingMatches("1")) {
				 if(match.getMatchStatus().equals("Upcoming")) {
					 System.out.println(match.getMatchID());
				 }
				 System.out.println(match.getMatchID());
				 System.out.println(match.getHomeTeamID());
				 System.out.println(pitchlist.getPitchByID(match.getPitchID()).getPitchName());
			 }
			 
			 for(Player player : teamlist.getTeamByName("TeamB").getTeamRoster()) {
				 System.out.println(player.getPlayerID());
			 }
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}