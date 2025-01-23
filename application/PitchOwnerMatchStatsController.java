package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import commonground.FactoryClass;
import commonground.Match;
import commonground.Pitch;
import commonground.PlayerStats;
import commonground.Team;
import dbhandlers.PlayerStatsDBHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import managers.PlayerStatsList;

public class PitchOwnerMatchStatsController implements Initializable {
	
	@FXML
    private TableView<String> playerStatsTable; 
    @FXML
    private TableColumn<String, String> playerNameColumn;
    @FXML
    private TableColumn<String, String> playerTeamColumn; 
    @FXML
    private TableColumn<String, String> goalsColumn; 
    @FXML
    private TableColumn<String, String> assistsColumn; 
    @FXML
    private TableColumn<String, String> ratingColumn; 
    @FXML
    private TableColumn<String, String> minutesColumn; 

    @FXML
    private TextField goalsField;
    @FXML
    private TextField assistsField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField minutesField;
    @FXML
    private Button addStatsButton;

    private PlayerStatsList playerstats; 
    
    public Team hometeam;
    
    public Team awayteam;
    
    public Match match;
    
    public Pitch pitch;
    
    
    private ObservableList<String> playerStatsData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    	try {
			playerstats = FactoryClass.getInstance().getPlayerStatsListInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Initialize player stats table
    	    	
        playerNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(",")[0]));
        playerTeamColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(",")[1]));
        goalsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(",")[2]));
        assistsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(",")[3]));
        ratingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(",")[4]));
        minutesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().split(",")[5]));
        
        playerStatsData.add("Muhammad Ali,Home Team,2,1,8.5,90");
        playerStatsData.add("Abdul Rafay,Away Team,1,0,7.2,90");
        playerStatsData.add("Shariq Khan,Home Team,0,2,8.0,85");
        playerStatsData.add("Junaid Ahmed,Away Team,1,1,7.8,90");

        playerStatsTable.setItems(playerStatsData);
        
        


    }

    public Team getHometeam() {
		return hometeam;
	}

	public void setHometeam(Team hometeam) {
		this.hometeam = hometeam;
	}

	public Team getAwayteam() {
		return awayteam;
	}

	public void setAwayteam(Team awayteam) {
		this.awayteam = awayteam;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Pitch getPitch() {
		return pitch;
	}

	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
	}

	@FXML
    private void addPlayerStats(ActionEvent event) {
        
        String playerName = null ; 
        String playerTeam = null; 
        int goals = Integer.parseInt(goalsField.getText());
        int assists = Integer.parseInt(assistsField.getText());
        float rating = Float.parseFloat(ratingField.getText());
        int minutes = Integer.parseInt(minutesField.getText());

      
    }
}