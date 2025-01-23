package commonground;

import java.sql.SQLException;

import dbhandlers.BookingDBHandler;
import dbhandlers.MatchDBHandler;
import dbhandlers.MatchDataHandler;
import dbhandlers.NotificationDBHandler;
import dbhandlers.PitchDBHandler;
import dbhandlers.PitchDataHandler;
import dbhandlers.PlayerDBHandler;
import dbhandlers.PlayerStatsDBHandler;
import dbhandlers.TeamDBHandler;
import managers.BookingList;
import managers.MatchList;
import managers.NotificationList;
import managers.PitchList;
import managers.PlayerList;
import managers.PlayerStatsList;
import managers.TeamList;
import managers.UserManager;

public class FactoryClass {
	
	private static FactoryClass instance = null;
	
	private static String connectionString = "jdbc:sqlserver://Ahmed-PC\\SQLEXPRESS;databaseName=CommonGround;encrypt=false;IntegratedSecurity=true";
	
	PitchList pitchlist;
	BookingList bookinglist;
	TeamList teamlist;
	PlayerList playerlist;
	MatchList matchlist;
	NotificationList notificationlist;
	PlayerStatsList playerstatslist;
	
	BookingDBHandler bookingDBHandler;
	PitchDataHandler pitchDBHandler;
	PlayerDBHandler playerDBHandler;
	TeamDBHandler teamDBHandler;
	MatchDataHandler matchDBHandler;
	NotificationDBHandler notificationDBHandler;
	PlayerStatsDBHandler playerstatsDBHandler;
	
	UserManager userManager; 
	

	
	public FactoryClass() throws SQLException {
		
		
		pitchDBHandler = new PitchDBHandler(connectionString);
		bookingDBHandler = new BookingDBHandler(connectionString);
		playerDBHandler = new PlayerDBHandler(connectionString);
		userManager = new UserManager(connectionString);
		teamDBHandler = new TeamDBHandler(connectionString);
		matchDBHandler = new MatchDBHandler(connectionString);
		notificationDBHandler = new NotificationDBHandler(connectionString);
		playerstatsDBHandler = new PlayerStatsDBHandler(connectionString);
		
		playerlist = new PlayerList(playerDBHandler);
		pitchlist = new PitchList(this.pitchDBHandler);
		
		
		bookinglist = new BookingList(bookingDBHandler);
		teamlist = new TeamList(teamDBHandler,playerlist);
		
		matchlist = new MatchList(matchDBHandler);
		
		notificationlist = new NotificationList(notificationDBHandler);
		
		playerstatslist = new PlayerStatsList(playerstatsDBHandler);
	}
	
	
	public static synchronized FactoryClass getInstance() throws SQLException {
		
		if( instance==null ) {
			instance = new FactoryClass();
		}
		
		return instance;
	}
	
	public static void createDBConnection(String string) {
		connectionString = string;
	}
	
	public NotificationList getNotificationListInstance() {
		return this.notificationlist;
	}
	
	
	
	public PitchList getPitchListInstance() {
		return this.pitchlist;
	}
	
	public TeamList getTeamListInstance() {
		return this.teamlist;
	}
	
	public BookingList getBookingListInstance() {
		return this.bookinglist;
	}
	
	public MatchList getMatchListInstance() {
		return this.matchlist;
	}
	
	public PlayerList getPlayerListInstance() {
		return this.playerlist;
	}
	
	
	public UserManager getUserManagerInstance() {
		return this.userManager;
	}
	
	public PlayerStatsList getPlayerStatsListInstance() {
		return this.playerstatslist;
	}

}
