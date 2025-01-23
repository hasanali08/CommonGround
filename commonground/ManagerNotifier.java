package commonground;

import java.sql.SQLException;

import managers.NotificationList;

public interface ManagerNotifier {
	void registerPlayer(PlayerObserver player);
    void unregisterPlayer(PlayerObserver player);
    public void notifyPlayers(String message, NotificationList notificationList) throws SQLException;
}
