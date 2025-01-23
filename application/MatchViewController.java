package application;

import java.sql.SQLException;
import java.util.ArrayList;

import commonground.FactoryClass;
import commonground.Match;
import commonground.Player;
import commonground.Team;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import managers.MatchList;
import managers.PlayerList;
import managers.TeamList;

public class MatchViewController {

    @FXML
    private VBox matchHistoryBox;
    
    Player player;
    
    Team team;
    
    MatchList matchlist;
    
    TeamList teamlist;
    
    PlayerList playerlist;
    
    ArrayList<Match> pastMatches;

    @FXML
    public void initialize() throws SQLException {
    	
    	player = (Player)FactoryClass.getInstance().getUserManagerInstance().getUser();
        matchlist = FactoryClass.getInstance().getMatchListInstance();
        teamlist = FactoryClass.getInstance().getTeamListInstance();
        playerlist = FactoryClass.getInstance().getPlayerListInstance();
        
        team = teamlist.getTeamByID(playerlist.getPlayerTeamID(player.getUserID()));
        pastMatches = matchlist.getPastMatches(playerlist.getPlayerTeamID(player.getUserID()));
        
        if(pastMatches!=null) {
        
        }
        
    }

    public void addMatchHistory(String matchTitle, String minutesPlayed, int goals, int assists, double rating, String date, String duration, String location) {
        HBox matchItem = createMatchItem(matchTitle, minutesPlayed, goals, assists, rating, date, duration, location);
        matchHistoryBox.getChildren().add(matchItem);
    }

    private HBox createMatchItem(String matchTitle, String minutesPlayed, int goals, int assists, double rating, String date, String duration, String location) {
        HBox matchItem = new HBox();
        matchItem.setSpacing(20);
        matchItem.setAlignment(Pos.CENTER_LEFT);
        matchItem.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #e0e0e0; -fx-padding: 10;");
        
        VBox matchDetailsBox = new VBox();
        matchDetailsBox.setSpacing(5);
        matchDetailsBox.setAlignment(Pos.TOP_LEFT);
        
        // Adding details to the match details box
        Label titleLabel = new Label(matchTitle);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Label minutesLabel = new Label("Minutes Played: " + minutesPlayed);
        Label goalsLabel = new Label("Goals Scored: " + goals);
        Label assistsLabel = new Label("Assists Made: " + assists);
        Label ratingLabel = new Label("Match Rating: " + rating);
        Label dateLabel = new Label("Date: " + date);
        Label durationLabel = new Label("Duration: " + duration);
        Label locationLabel = new Label("Location: " + location);
        
        // Add labels to the VBox
        matchDetailsBox.getChildren().addAll(titleLabel, minutesLabel, goalsLabel, assistsLabel, ratingLabel, dateLabel, durationLabel, locationLabel);
        
        matchItem.getChildren().add(matchDetailsBox);
        
        return matchItem;
    }
}

