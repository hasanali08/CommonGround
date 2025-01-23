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

public class ManagerTeamViewController {

    @FXML
    private VBox teamViewVBox;

    @FXML
    private Button addPlayerButton;
    
    @FXML
    private Button createNewTeamButton;
    
    @FXML
    private Label noTeamLabel;
    
    @FXML
    private Label teamNameLabel;
    
    @FXML
    private Pane playerPane;
    
    @FXML
    private ScrollPane playerviewScrollPane;
    
    @FXML 
    private VBox playerviewVBox;
    
    private TeamManager manager;
    
    private TeamList teamlist;
    
    private PlayerList playerlist;
    
    private PitchList pitchlist;
    
    private String teamName;
    
    private String homePitch;

    @FXML
    private void displayTeam() throws SQLException {
    	
    	 if (noTeamLabel != null) {
             teamViewVBox.getChildren().remove(noTeamLabel); 
             noTeamLabel = null; 
         }
    	for(Player player : teamlist.getTeamByID(teamlist.getTeamByManager(manager.getUserID()).getTeamID()).getTeamRoster()) {
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
 //   	 

    }
    
    private void addPlayer() {
        
        playerviewVBox.getChildren().clear(); 

        
        ArrayList<Player> selectedPlayers = new ArrayList<>();

        if(playerlist.getPlayersWithNoTeam()==null) {
            Label noPlayersLabel = new Label("No players without a team.");
            noPlayersLabel.setFont(Font.font("System", FontWeight.BOLD, 16)); // Set font to bold and size 16

            // Center the label within the VBox
            playerviewVBox.setAlignment(Pos.CENTER); 
            playerviewVBox.getChildren().add(noPlayersLabel);
            return;
        }
        for (Player player : playerlist.getPlayersWithNoTeam()) {
        	HBox playerBox = new HBox(20); 

            Label playerNameLabel = new Label(player.getFirstName() + " " + player.getLastName());
            playerNameLabel.setFont(Font.font("System", FontWeight.BOLD, 14)); 
            CheckBox playerCheckBox = new CheckBox();

            playerCheckBox.setOnAction(event -> {
                if (playerCheckBox.isSelected()) {
                    if (selectedPlayers.size() < 8) {
                        selectedPlayers.add(player);
                    } else {
                        
                        playerCheckBox.setSelected(false); 
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Selection Limit");
                        alert.setHeaderText(null);
                        alert.setContentText("You can only select 8 players.");
                        alert.showAndWait();
                    }
                } else {
                    selectedPlayers.remove(player);
                }
            });

            playerBox.getChildren().addAll(playerNameLabel, playerCheckBox);
            playerviewVBox.getChildren().add(playerBox);
        }

  
        Button submitButton = new Button("Submit Selection");
        playerviewVBox.getChildren().add(submitButton);

        submitButton.setOnAction(event -> {
            if (selectedPlayers.size() <= 8) {
                
            	for(Player player : selectedPlayers) {
                try {
                	
					if(!teamlist.addPlayerToTeam(player, teamlist.getTeamByManager(manager.getUserID()).getTeamID())) {
						JOptionPane.showMessageDialog(null, "Team is full! Cannot add more members", "Team Creation Failed", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	}
            	
            	try {
					displayTeam();
					playerviewVBox.getChildren().clear();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Selection");
                alert.setHeaderText(null);
                alert.setContentText("You cant select more than 8 players.");
                alert.showAndWait();
            }
        });
    }
    
    public void initialize() throws SQLException {
    	
    	this.addPlayerButton.setOnAction(event -> addPlayer());
    	
    	manager = (TeamManager) FactoryClass.getInstance().getUserManagerInstance().getUser();
    	teamlist = FactoryClass.getInstance().getTeamListInstance();
    	playerlist = FactoryClass.getInstance().getPlayerListInstance();
    	pitchlist = FactoryClass.getInstance().getPitchListInstance();
    	
    	if(teamlist.getTeamByManager(manager.getUserID())==null) {
    		noTeamLabel.setText("You dont have a team yet");
            noTeamLabel.setFont(new Font("Arial", 18)); // Set font (optional)
            noTeamLabel.setStyle("-fx-padding: 20;"); // Add some padding (optional)
  
    	}
    	else
    	{
    		teamViewVBox.getChildren().remove(createNewTeamButton);
    		teamViewVBox.getChildren().remove(noTeamLabel);
    		
    		teamNameLabel.setText(teamlist.getTeamByManager(manager.getUserID()).getTeamName());
    		displayTeam();
    		
    		
    	}
    	
    	
    	
    	 createNewTeamButton.setOnAction(event-> {
    	        
             Stage inputStage = new Stage();
             inputStage.setTitle("Create New Team");

        
             TextField teamNameField = new TextField();
             teamNameField.setPromptText("Team Name");

             TextField homePitchField = new TextField();
             homePitchField.setPromptText("Home Pitch Name");

           
             Button submitButton = new Button("Create Team");
             submitButton.setOnAction(submitEvent -> {
            	 if(!teamNameField.getText().isBlank() && !homePitchField.getText().isBlank()) {
            		 
                 teamName = teamNameField.getText();
                 homePitch = homePitchField.getText();
                 if(pitchlist.getPitchByName(homePitch)==null) {
                	 JOptionPane.showMessageDialog(null, "No pitch found", "Team Creation Failed", JOptionPane.ERROR_MESSAGE);
                	 return;
                 }
                 teamlist.createTeam(teamName, manager.getUserID());
                 Team team = null;
				try {
					team = teamlist.getTeamByManager(manager.getUserID());
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				if(team==null) {
					JOptionPane.showMessageDialog(null, "No team found", "Team Creation Failed", JOptionPane.ERROR_MESSAGE);
					return;
				}
                 team.setHomePitchID(pitchlist.getPitchByName(homePitch).getPitchID());
                if(!teamlist.updateTeam(team, manager.getUserID())) {
                	JOptionPane.showMessageDialog(null, "", "Team Creation Failed", JOptionPane.ERROR_MESSAGE);
                	return;
                }
                 
                 
            	 }

                 
            	 teamViewVBox.getChildren().remove(createNewTeamButton);
                 inputStage.close(); 
             });

             
             VBox inputLayout = new VBox(10); 
             inputLayout.getChildren().addAll(teamNameField, homePitchField, submitButton);
             inputLayout.setPadding(new Insets(20)); 

             Scene inputScene = new Scene(inputLayout);
             inputStage.setScene(inputScene);
             inputStage.show();
         	
         	
         });
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
