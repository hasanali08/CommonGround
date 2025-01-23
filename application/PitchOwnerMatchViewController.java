package application;

import java.sql.SQLException;
import java.util.ArrayList;

import commonground.FactoryClass;
import commonground.Match;
import commonground.Pitch;
import commonground.PitchOwner;
import commonground.Team;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import managers.MatchList;
import managers.PitchList;
import managers.PlayerList;
import managers.TeamList;

public class PitchOwnerMatchViewController {

    @FXML
    private VBox matchHistoryBox;
    
    private MatchList matchlist;
    
    private TeamList teamlist;
    
    private PitchList pitchlist;
    
    private PlayerList playerlist;
    
    ArrayList<Match> matches;
    
    ArrayList<Pitch> pitches;
    
    PitchOwner owner;

    @FXML
    public void initialize() throws SQLException {
        matches = new ArrayList<Match>();
    	owner = (PitchOwner) FactoryClass.getInstance().getUserManagerInstance().getUser();
    	
    	matchlist = FactoryClass.getInstance().getMatchListInstance();
    	pitchlist = FactoryClass.getInstance().getPitchListInstance();
    	teamlist = FactoryClass.getInstance().getTeamListInstance();
    	pitches = pitchlist.getPitchesByOwner(owner.getUserID());
    	
    	
    	for(Pitch pitch : pitches) {
    		matches.addAll(matchlist.getUpcomingMatchesForPitch(pitch.getPitchID()));
    	}
    	
    	if(!matches.isEmpty()) {
	    	for(Match match: matches) {
	    		Team hometeam = teamlist.getTeamByID(match.getHomeTeamID());
	    		Team awayteam = teamlist.getTeamByID(match.getAwayTeamID());
	    		Pitch pitch = pitchlist.getPitchByID(match.getPitchID());
	    		this.addMatch(match, hometeam, awayteam, pitch);
	    	}
    	}
    	else {
    		 Label noMatchesLabel = new Label("No upcoming matches found.");
             noMatchesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #888;"); 
             matchHistoryBox.getChildren().add(noMatchesLabel);
    	}
    	
    	
    	

    }

    public void addMatch(Match match, Team hometeam, Team awayteam, Pitch pitch) {
        HBox matchItem = createMatchItem(match, hometeam, awayteam, pitch);
        matchHistoryBox.getChildren().add(matchItem);
    }

    private HBox createMatchItem(Match match, Team hometeam, Team awayteam, Pitch pitch) {
    	
    	
        HBox matchItem = new HBox();
        matchItem.setSpacing(20);
        matchItem.setAlignment(Pos.CENTER_LEFT);
        matchItem.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #e0e0e0; -fx-padding: 10;");

        VBox matchDetailsBox = new VBox();
        matchDetailsBox.setSpacing(5);
        matchDetailsBox.setAlignment(Pos.TOP_LEFT);

 
        Label titleLabel = new Label(match.getMatchType() + " " + "Match");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label homeTeamLabel = new Label("Home Team: " + hometeam.getTeamName());
        Label awayTeamLabel = new Label("Away Team: " + awayteam.getTeamName());
        Label locationLabel = new Label("Location: " + pitch.getPitchName() + " " + pitch.getPitchLocation());
        Label dateLabel = new Label("Date: " + match.getMatchDate());
        

  
        matchDetailsBox.getChildren().addAll(titleLabel, locationLabel, dateLabel);

  
        Button statsButton = new Button("Add Match Stats");
        statsButton.setOnAction(e -> {
        	 try {
        	        
        	        FXMLLoader loader = new FXMLLoader(getClass().getResource("PitchOwnerMatchStats.fxml")); 
        	        Parent root = loader.load();

        	        PitchOwnerMatchStatsController controller = loader.getController(); 
        	        controller.setHometeam(hometeam);
        	        controller.setAwayteam(awayteam);
        	        controller.setMatch(match);
        	        controller.setPitch(pitch);
        	        
        	        Stage statsStage = new Stage();
        	        statsStage.initModality(Modality.APPLICATION_MODAL);
        	        statsStage.setTitle("Add Match Stats"); 
        	        statsStage.setScene(new Scene(root));

        	    
        	        statsStage.show();

        	    } catch (Exception ex) {
        	        ex.printStackTrace();
        	    }
        });
        matchDetailsBox.getChildren().add(statsButton);

  
        Button completeButton = new Button("Mark as Complete");
        completeButton.setOnAction(e -> {

            
            matchItem.setStyle("-fx-background-color: #e0f0e0; -fx-border-color: #e0e0e0; -fx-padding: 10;"); 
        });
        matchDetailsBox.getChildren().add(completeButton);

        matchItem.getChildren().add(matchDetailsBox);

        return matchItem;
    }
}
