package application;

import java.sql.SQLException;
import java.util.ArrayList;

import commonground.FactoryClass;
import commonground.TeamManager;
import commonground.ManagerNotifier;
import commonground.Notification;
import commonground.Player;
import commonground.PlayerObserver;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import managers.NotificationList;
import managers.TeamList;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

public class ManagerNotificationViewController {
	@FXML
	private VBox managerNotifications;
	@FXML
	private Label headingLabel;
	@FXML
	private ScrollPane notificationScrollPane;
	@FXML
	private VBox notificationVBox;
	@FXML
	private Pane messagePane;
	@FXML
	private Button sendMessageButton;
	@FXML
	private TextField messageTextBox;
	
	private TeamList teamlist;
	
	private NotificationList notificationList;
		
	private TeamManager manager;
	
	
	
	
	public void initialize() throws SQLException {
		teamlist = FactoryClass.getInstance().getTeamListInstance();
		notificationList = FactoryClass.getInstance().getNotificationListInstance();
		
		manager = (TeamManager)FactoryClass.getInstance().getUserManagerInstance().getUser();
		manager.setTeamID(teamlist.getTeamByManager(FactoryClass.getInstance().getUserManagerInstance().getUser().getUserID()).getTeamID()); 
		
		ArrayList<Player> team = teamlist.getTeamByManager(FactoryClass.getInstance().getUserManagerInstance().getUser().getUserID()).getTeamRoster();
		for(PlayerObserver player : team) {
			manager.registerPlayer(player);
		}
		
		sendMessageButton.setOnAction(event -> {
			try {
				sendMessage();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		});
		
		loadNotifications();
		
	}
	
	public void sendMessage() throws SQLException {
		if(!messageTextBox.getText().isBlank()) {
			String message = messageTextBox.getText();
			message = manager.getFirstName() +": "+message;
			System.out.println("In notification view: " + message);
			manager.notifyPlayers(message,FactoryClass.getInstance().getNotificationListInstance());
			
		}
	}
	
	public void loadNotifications() throws SQLException {
		
		ArrayList<Notification> notifications = notificationList.getNotificationsForTeam(manager.getTeamID());

        
        for (Notification notification : notifications) {
        	Label notificationLabel = new Label(notification.getMessage());

     
            notificationLabel.setPadding(new Insets(10, 10, 10, 10)); 

    
            notificationLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));

       
            notificationLabel.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

          
            if (notificationVBox.getChildren().size() % 2 == 0) {
                notificationLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                notificationLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            }

            notificationVBox.getChildren().add(notificationLabel);
        }
	}

}
