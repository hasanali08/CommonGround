package commonground;

import java.util.ArrayList;

public interface PlayerObserver {
	
	void update(ArrayList<Notification> notifications);
    String getPlayerID();

}
