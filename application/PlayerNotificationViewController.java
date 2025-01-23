package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle; 

import commonground.FactoryClass;
import commonground.Notification;
import commonground.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import managers.NotificationList;

public class PlayerNotificationViewController{
    @FXML
    private VBox notificationsVBox; 
    @FXML
    private Label headingLabel;
    @FXML
    private ScrollPane notificationScrollPane;
    @FXML
    private VBox notificationVBox; // This is the VBox inside the ScrollPane

    private NotificationList notificationList;

    
    public void initialize() {
        try {
            
            Player player = (Player) FactoryClass.getInstance().getUserManagerInstance().getUser();

            
            notificationList = FactoryClass.getInstance().getNotificationListInstance();

            
            String teamId = FactoryClass.getInstance().getPlayerListInstance().getPlayerTeamID(player.getPlayerID());

            
            ArrayList<Notification> notifications = notificationList.getNotificationsForTeam(teamId);

           
            for (Notification notification : notifications) {
            	Label notificationLabel = new Label(notification.getMessage());

                // 1. Add some padding
                notificationLabel.setPadding(new Insets(10, 10, 10, 10)); 

                // 2. Set a larger font size
                notificationLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));

                // 3. Add a border
                notificationLabel.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                // 4. Alternate background colors
                if (notificationsVBox.getChildren().size() % 2 == 0) {
                    notificationLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    notificationLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                notificationVBox.getChildren().add(notificationLabel);
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }
}