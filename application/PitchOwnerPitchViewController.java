package application;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import commonground.Booking;
import commonground.FactoryClass;
import commonground.Match;
import commonground.Player;
import commonground.Team;
import commonground.Pitch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import managers.BookingList;
import managers.MatchList;
import managers.PitchList;
import javafx.scene.layout.Pane;


public class PitchOwnerPitchViewController {
	

	@FXML
	private HBox pitchView;
	@FXML
	private Pane pitchImagePanel;
	@FXML
	private ImageView pitchImage;
	@FXML
	private Pane pitchInfoPanel;
	@FXML
	private VBox pitchInfoBox;
	@FXML
	private Label pitchNameLabel;
	@FXML
	private Label pitchLocationLabel;
	@FXML
	private Label pitchTypeLabel;
	@FXML
	private Label pitchPriceLabel;
	
	@FXML
	private Button next_button;
	
	@FXML
	private Button previous_button;
	
	@FXML
	private Label noPitchLabel;
		
	private Pitch currentPitch;
	
	private PitchList pitchlist;
		
	private ArrayList<Pitch> availablePitches;
	
	private int pitchindex;
		
	public void initialize() throws SQLException {
		noPitchLabel = new Label("No pitches available");
        noPitchLabel.setStyle("-fx-font-size: 18px; -fx-padding: 20px;");
		System.out.println(FactoryClass.getInstance().getUserManagerInstance().getUser().getUserID());
		
		pitchindex = 0;
		pitchlist = FactoryClass.getInstance().getPitchListInstance();
		
		availablePitches = pitchlist.getPitchesByOwner(FactoryClass.getInstance().getUserManagerInstance().getUser().getUserID());
		next_button.setOnAction(event -> moveToNextPitch());
		previous_button.setOnAction(event -> moveToPreviousPitch());
		
		
		setPitchView();
		
	}
	
	
	
	public void moveToNextPitch() {
		
		if(availablePitches!=null && availablePitches.size()==0) {
			
		}
		else {
			if(pitchindex<availablePitches.size()-1) {
				
				pitchindex+=1;
				currentPitch = availablePitches.get(pitchindex);
				if(currentPitch!=null) {
					setPitchView();
				}
				
			}
			else {
				pitchindex = 0;
				setPitchView();
			}
		}
	}
	
	public void moveToPreviousPitch() {
		
		if(availablePitches!= null && availablePitches.size()==0) {
			
		}
		else {
			if(pitchindex>0) {
				pitchindex-=1;
			currentPitch = availablePitches.get(pitchindex);
			if(currentPitch!=null) {
				setPitchView();
			}
			}
		}
		
	}
	
	public void setPitchView() {
		
		if(availablePitches!=null && availablePitches.size()>0) {
		currentPitch = availablePitches.get(pitchindex);
		
		if(currentPitch!=null) {
			
			Image image;
		    if (currentPitch.getPitchImage().startsWith("/") || currentPitch.getPitchImage().startsWith("file:")) {
		        
		        image = new Image(currentPitch.getPitchImage()); 
		    } else {
		        
		        image = new Image(getClass().getResource(currentPitch.getPitchImage()).toExternalForm());
		    }
		    pitchImage.setImage(image);
//			pitchImage.setImage(new Image(getClass().getResource(currentPitch.getPitchImage()).toExternalForm()));
			pitchNameLabel.setText(currentPitch.getPitchName());
			pitchTypeLabel.setText(currentPitch.getPitchType());
			pitchPriceLabel.setText(Double.toString(currentPitch.getBookingPrice()));
			pitchLocationLabel.setText(currentPitch.getPitchLocation());
		
		}
		}
		else {
			
			
			pitchInfoPanel.setVisible(false);
			pitchImagePanel.setVisible(false);
            
            pitchView.getChildren().clear();
            pitchView.getChildren().add(noPitchLabel);
           
			
			
		}
	}

}
