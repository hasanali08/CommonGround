package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import commonground.FactoryClass;
import commonground.Pitch;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import managers.PitchList;
import javafx.scene.layout.Pane;

public class CreatePitchViewController {
	@FXML
	private HBox pitchCreationBox;
	@FXML
	private Pane pitchInfoPanel;
	@FXML
	private VBox pitchInfoBox;
	@FXML
	private Label pitchNameLabel;
	@FXML
	private TextField pitchName;
	@FXML
	private Label pitchLocationLabel;
	@FXML
	private TextField pitchLocation;
	@FXML
	private Label pitchTypeLabel;
	@FXML
	private TextField pitchType;
	@FXML
	private Label pitchPriceLabel;
	@FXML
	private TextField pitchPrice;
	@FXML
	private Button add_pitch_image_button;
	@FXML
	private Pane pitchImagePanel;
	@FXML
	private ImageView pitchImage;
	@FXML
	private Button confirm_button;
	@FXML
	private Label pitchImageSelectionLabel;
	
	private String newPitchName;
	
	private String newPitchLocation;
	
	private String newPitchType;
	
	private String newPitchPrice;
	
	private String newPitchImagePath;
	
	
	
	public void initialize() {
		add_pitch_image_button.setOnAction(event -> this.handleAddPitchImageButtonAction());
		confirm_button.setOnAction(event -> { 
			if(pitchName.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Enter pitch name", "Pitch addition failed", JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				newPitchName = pitchName.getText();
			}
			
			if (pitchLocation.getText().isBlank()) {
			    JOptionPane.showMessageDialog(null, "Enter pitch location", "Pitch addition failed", JOptionPane.ERROR_MESSAGE);
			    return;
			} else {
			    newPitchLocation = pitchLocation.getText();
			}

			if (pitchType.getText().isBlank()) {
			    JOptionPane.showMessageDialog(null, "Enter pitch type", "Pitch addition failed", JOptionPane.ERROR_MESSAGE);
			    return;
			} else {
			    newPitchType = pitchType.getText();
			}

			if (pitchPrice.getText().isBlank()) {
			    JOptionPane.showMessageDialog(null, "Enter pitch price", "Pitch addition failed", JOptionPane.ERROR_MESSAGE);
			    return;
			} else {
			    newPitchPrice = pitchPrice.getText();
			}

			
			if (newPitchImagePath == null || newPitchImagePath.isBlank()) {
			    JOptionPane.showMessageDialog(null, "Select a pitch image", "Pitch addition failed", JOptionPane.ERROR_MESSAGE);
			    return;
			} 
			
			try {
				this.addNewPitch();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
	}
	
	public void addNewPitch() throws SQLException {
		PitchList pitchlist = FactoryClass.getInstance().getPitchListInstance();
		Pitch new_pitch = pitchlist.createPitch(newPitchName, newPitchLocation, newPitchType, newPitchImagePath,true, Double.parseDouble(newPitchPrice));
		if(pitchlist.addPitch(new_pitch,FactoryClass.getInstance().getUserManagerInstance().getUser().getUserID())) {
			JOptionPane.showMessageDialog(null, "Pitch added successfully!", "Pitch Owner Portal", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	
	 private void handleAddPitchImageButtonAction() {
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Select Pitch Image");
	        fileChooser.getExtensionFilters().addAll(
	                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
	        );

	        File selectedFile = fileChooser.showOpenDialog(pitchCreationBox.getScene().getWindow()); 


	        if (selectedFile != null) {
	            newPitchImagePath = selectedFile.getAbsolutePath();
	            pitchImageSelectionLabel.setVisible(false);

	            // Load and display the image
	            this.newPitchImagePath = "file:" + this.newPitchImagePath;
	            Image image = new Image(this.newPitchImagePath);
	            pitchImage.setImage(image);
	        }
	        
	       
	    }

}
