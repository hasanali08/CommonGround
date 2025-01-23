package commonground;

import java.util.ArrayList;

public class PlayerList {
	
	ArrayList<Player> list;
	PlayerDBHandler DBHandler;
	
	PlayerList(PlayerDBHandler DBHandler) {
		
		list = new ArrayList<Player>();
		this.DBHandler = DBHandler;
		
	}

}
