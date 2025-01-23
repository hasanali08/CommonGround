

package application;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import managers.PitchList;
import managers.PlayerList;
import managers.TeamList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import commonground.TeamManager;
import commonground.FactoryClass;
import commonground.Player;
import commonground.Team;

public class TeamViewController {

    @FXML
    private VBox teamViewVBox;

    @FXML
    private Label noTeamLabel;
    
    @FXML
    private Label teamNameLabel;

    private Player player;
    
    private TeamList teamlist;
    
    private PlayerList playerlist;
      
    private String teamID;


    @FXML
    private void displayTeam() throws SQLException {
    	
    	 if (noTeamLabel != null) {
             teamViewVBox.getChildren().remove(noTeamLabel); 
             noTeamLabel = null; 
         }
    	if(teamID!=null) {
    	for(Player player : teamlist.getTeamByID(teamID).getTeamRoster()) {
            HBox newMemberBox = new HBox();
            newMemberBox.setPrefHeight(28);
            newMemberBox.setPrefWidth(463);
            VBox.setMargin(newMemberBox, new Insets(0, 0, 15, 0));

            Label memberLabel = new Label(player.getFirstName()+" " + player.getLastName());
            memberLabel.setPrefHeight(28);
            memberLabel.setPrefWidth(390);
            memberLabel.setPadding(new Insets(0, 0, 0, 30));
            memberLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI'; -fx-font-size: 16;");

            Button moreButton = new Button();
            moreButton.setPrefHeight(32);
            moreButton.setPrefWidth(74);
            moreButton.setStyle("-fx-background-color: transparent;");
            HBox.setMargin(moreButton, new Insets(0, 30, 0, 0));

            ImageView moreImageView = new ImageView(new Image(getClass().getResourceAsStream("../assets/img/more.png")));
            moreImageView.setFitHeight(25);
            moreImageView.setFitWidth(30);
            moreImageView.setPickOnBounds(true);
            moreImageView.setPreserveRatio(true);
            moreButton.setGraphic(moreImageView);

           
            Popup playerInfoPopup = new Popup();
            VBox popupContent = new VBox(5);
            playerInfoPopup.getContent().add(popupContent);
            popupContent.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-border-color: gray;");

            moreButton.setOnMouseEntered(event -> {
               
                popupContent.getChildren().clear(); 
                Label nameLabel = new Label("Name: " + player.getFirstName() + " " + player.getLastName());
                Label positionLabel = new Label("Position: " + player.getPosition());
                Label goalsLabel = new Label("Goals: " + Integer.toString(player.getGoalsScored()));
                Label assistsLabel = new Label("Assists: " + Integer.toString(player.getAssistsMade()));
                popupContent.getChildren().addAll(nameLabel, positionLabel, goalsLabel, assistsLabel);

                
                Bounds bounds = moreButton.localToScreen(moreButton.getBoundsInLocal()); 
                double x = bounds.getMaxX();
                double y = bounds.getMinY(); 
                playerInfoPopup.show(moreButton.getScene().getWindow(), x, y);
            });

            moreButton.setOnMouseExited(event -> {
                playerInfoPopup.hide();
            });    
            
            newMemberBox.getChildren().addAll(memberLabel, moreButton);

      
            teamViewVBox.getChildren().add(newMemberBox);
            
    	}
    	}
   	 
    }
    
    
    
    public void initialize() throws SQLException {
    	
    	
    	
    	player = (Player) FactoryClass.getInstance().getUserManagerInstance().getUser();
    	teamlist = FactoryClass.getInstance().getTeamListInstance();
    	playerlist = FactoryClass.getInstance().getPlayerListInstance();
    	teamID = playerlist.getPlayerTeamID(player.getUserID());
    	
    	if(teamID==null) {
    		
    		noTeamLabel.setText("You dont have a team yet");
            noTeamLabel.setFont(new Font("Arial", 18)); // Set font (optional)
            noTeamLabel.setStyle("-fx-padding: 20;"); // Add some padding (optional)
  
    	}
    	else
    	{
    		
    		teamViewVBox.getChildren().remove(noTeamLabel);
    		
    		teamNameLabel.setText(teamlist.getTeamByID(teamID).getTeamName());
    		displayTeam();
    		
    		
    	}
    	
    	
    	
    	}

    private void showPlayerProfilePopup() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilePopup.fxml")); 
        Parent root = loader.load();

       
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); 
        popupStage.setTitle("Player Profile");
        popupStage.setScene(new Scene(root));

        // Show the pop-up
        popupStage.show();
    }
}

