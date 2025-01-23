package application;

import java.sql.SQLException;

import commonground.FactoryClass;
import commonground.Team;
import commonground.TeamManager;
import javafx.fxml.FXML;

import javafx.scene.control.Label;

public class ManagerHomeViewController {
	@FXML
	private Label teamNameLabel;
	@FXML
	private Label winsLabel;
	@FXML
	private Label lossesLabel;
	
	TeamManager manager;
	
	public void initialize() throws SQLException {
		manager = (TeamManager)FactoryClass.getInstance().getUserManagerInstance().getUser();
		Team team = FactoryClass.getInstance().getTeamListInstance().getTeamByManager(manager.getUserID());
		
		if(team!=null) {
			teamNameLabel.setText(team.getTeamName());
			winsLabel.setText(Integer.toString( team.getWins()));
			lossesLabel.setText(Integer.toString( team.getLosses()));
		}
	}

}
