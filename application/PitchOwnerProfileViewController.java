package application;

import commonground.*;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;


public class PitchOwnerProfileViewController {

    @FXML
    private ImageView profileImageView;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userEmailLabel;

    @FXML
    private Label userPhoneLabel;

    @FXML
    private Label userAddressLabel;
   
    @FXML
    private Button logoutButton;
    
    private PitchOwner pitchowner;
    
    public void initialize() throws SQLException {
    	
    	pitchowner = (PitchOwner)FactoryClass.getInstance().getUserManagerInstance().getUser();
  
    	logoutButton.setOnAction(event -> logout());
    
        updateUserData();
       
    }
    
    void updateUserData() {
    	
        
        setUserData(pitchowner.getFirstName(), pitchowner.getEmail(), pitchowner.getPhoneNumber()); 
    }

    public void logout() {
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			Stage stage = (Stage) ((Node) logoutButton).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
    }
   
    public void setUserData(String name, String email, String phone) {
        userNameLabel.setText(name);
        userEmailLabel.setText(email);
        userPhoneLabel.setText(phone);
       

        
    }
    
    

}

