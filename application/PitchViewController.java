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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import managers.BookingList;
import managers.MatchList;
import managers.PitchList;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PitchViewController {
	

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
	private Button book_button;
	
	@FXML
	private Button previous_button;
	
	@FXML
	private Button selectTeamButton;
	
	@FXML
	private DatePicker matchDate;
	
	@FXML
	private Label noPitchLabel;
	
	private boolean isPitchInfoVisible = true; 
	
	private Pitch currentPitch;
	
	private PitchList pitchlist;
	
	private BookingList bookinglist;
	
	private MatchList matchlist;
	
	private Team selectedTeam;
	
	private ArrayList<Pitch> availablePitches;
	
	private int pitchindex;
	
	private Image pitchImageObject;
	
	public void initialize() throws SQLException {
		noPitchLabel = new Label("No pitches available");
        noPitchLabel.setStyle("-fx-font-size: 18px; -fx-padding: 20px;");
		System.out.println(FactoryClass.getInstance().getUserManagerInstance().getUser().getUserID());
		
		pitchindex = 0;
		pitchlist = FactoryClass.getInstance().getPitchListInstance();
		bookinglist = FactoryClass.getInstance().getBookingListInstance();
		matchlist = FactoryClass.getInstance().getMatchListInstance();
		
//		Pitch new_pitch = new Pitch("1", "ICAS Football Ground", "G-15 Islamabad","8-a-side" ,"/assets/img/pitch1.jpeg",true,(double)3500);
//		pitchlist.addPitch(new_pitch,"1");
//		pitchlist.addPitch(new Pitch("2", "STRIKERS Pitch", "H-13 Islamabad", "7-aside","/assets/img/pitch2.jpg", true, (double)3000),"1");
		availablePitches = pitchlist.getAvailablePitches();
		next_button.setOnAction(event -> moveToNextPitch());
		previous_button.setOnAction(event -> moveToPreviousPitch());
		book_button.setOnAction(event -> {
			try {
				bookPitch();
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		selectTeamButton.setOnAction(event -> {
			try {
				showTeamSelectionPopup();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		setPitchView();
		
		

		
		
	}
	
	public void bookPitch() throws HeadlessException, SQLException {
		
		if(availablePitches!=null && availablePitches.size()>0) {
			if(bookinglist.canPlayerBook(FactoryClass.getInstance().getUserManagerInstance().getUser().getUserID())) {
				if(selectedTeam!=null && matchDate.getValue()!=null) {
					String dateString;
					if(matchDate.getValue().isAfter(LocalDate.now())) {
					dateString = matchDate.getValue().toString(); 
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Incorrect date selected, please select a later date", "Booking Failed", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					Booking booking = bookinglist.createNewBooking(FactoryClass.getInstance().getUserManagerInstance().getUser().getUserID(), currentPitch.getPitchID());
					pitchlist.getPitchByID(currentPitch.getPitchID()).setPitchAvailable(false);
					pitchlist.updatePitch(currentPitch.getPitchID());
					
					Match match = FactoryClass.getInstance().getMatchListInstance().createMatch(dateString, selectedTeam.getTeamID(), selectedTeam.getTeamID(), currentPitch.getPitchID(), "Exhibition", null, null, "Upcoming");
					
					booking.setMatchID(match.getMatchID());
					bookinglist.updateBooking(booking);
					
					availablePitches = pitchlist.getAvailablePitches();
					pitchindex = 0;
					setPitchView();
				}
//			System.out.println(booking.getBookingDate());
			
			}
			else {
				JOptionPane.showMessageDialog(null, "You cannot book a pitch at this time.", "Booking Failed", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			
			JOptionPane.showMessageDialog(null, "No available pitches", "No pitch available", JOptionPane.ERROR_MESSAGE);
		}
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
	
	 private void showTeamSelectionPopup() throws SQLException {
	        // Replace this with your actual team names ArrayList
	        ArrayList<String> teamNames = FactoryClass.getInstance().getTeamListInstance().getAllTeamNames();


	        Stage popupStage = new Stage();
	        popupStage.initModality(Modality.APPLICATION_MODAL);
	        popupStage.setTitle("Select Team");

	        ObservableList<String> teams = FXCollections.observableArrayList(teamNames);
	        ListView<String> teamListView = new ListView<>(teams);
	        teamListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

	        Button selectButton = new Button("Select");
	        selectButton.setOnAction(e -> {
	            String selected = teamListView.getSelectionModel().getSelectedItem();
	            
	           try {
				selectedTeam =  FactoryClass.getInstance().getTeamListInstance().getTeamByName(selected);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	            popupStage.close();
	        });

	        VBox layout = new VBox(10);
	        layout.setPadding(new Insets(20));
	        layout.getChildren().addAll(teamListView, selectButton);

	        Scene scene = new Scene(layout);
	        popupStage.setScene(scene);
	        popupStage.showAndWait();
	    }
	
	
	
	public void setPitchView() {
		
		if(availablePitches!=null && availablePitches.size()>0) {
		currentPitch = availablePitches.get(pitchindex);
		
		if(currentPitch!=null) {
			
			Image image;
		    if (currentPitch.getPitchImage().startsWith("/") || currentPitch.getPitchImage().startsWith("file:")) {
		        // Absolute path (starts with "/" or "file:")
		        image = new Image(currentPitch.getPitchImage()); 
		    } else {
		        // Relative path
		        image = new Image(getClass().getResource(currentPitch.getPitchImage()).toExternalForm());
		    }
		    pitchImage.setImage(image);
//			pitchImage.setImage(new Image(getClass().getResource(currentPitch.getPitchImage()).toExternalForm()));
			pitchNameLabel.setText(currentPitch.getPitchName());
			pitchTypeLabel.setText(currentPitch.getPitchType());
			pitchPriceLabel.setText("Rs " + Double.toString(currentPitch.getBookingPrice()));
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
