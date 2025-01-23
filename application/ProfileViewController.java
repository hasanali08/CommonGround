package application;

import commonground.*;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import managers.PlayerList;
import managers.UserManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ProfileViewController {

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
    private Label skillTagsLabel;
    
    @FXML
    private Button addSkillsButton;
    
    @FXML
    private ComboBox skillTagsComboBox;
    
    private Player player;
    
    private UserManager userManager;
    
    private PlayerList playerlist;
    
    
    
    @FXML
    private Button logoutButton;
    
    public void initialize() throws SQLException {
    	
    	player = (Player)FactoryClass.getInstance().getUserManagerInstance().getUser();
    	playerlist = FactoryClass.getInstance().getPlayerListInstance();
    	
    	logoutButton.setOnAction(event -> logout());
    	addSkillsButton.setOnAction(event -> addSkillsButtonPressed());
        this.skillTagsComboBox.setVisible(false);
        
        String skills = "";
        for(String string: player.getSkills()) {
        	skills+=" "+string;
        }
        
        updateUserData();
       
    }
    
    void updateUserData() {
    	String skills = "";
        for(String string: player.getSkills()) {
        	skills+=" "+string;
        }
        
        setUserData(player.getFirstName(), player.getEmail(), player.getPhoneNumber(), player.getEmail(), skills); 
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
    
	@SuppressWarnings("unchecked")
	public void addSkillsButtonPressed() {

    	skillTagsComboBox.setVisible(true);
        List<String> skillTags = Arrays.asList("Goal Machine", "Assist King", "Speed Demon", "Artist", "Playmaker", "Leader", "Maestro");

        skillTagsComboBox.getItems().setAll(skillTags);
        skillTagsComboBox.setPromptText("Select a skill tag");

      
        skillTagsComboBox.setOnAction(event -> {
        	
        	String selectedSkill = skillTagsComboBox.getValue().toString(); 
            if (selectedSkill != null) {
            	ArrayList<String> skills  = player.getSkills();
            	skills.add(selectedSkill);
            	player.setSkills(skills);
            	
                updateUserData();
                playerlist.updatePlayer(player);
                skillTagsComboBox.setVisible(false); 
            }
            
        });
    }
    	
    	
    public void setUserData(String name, String email, String phone, String address, String skills) {
        userNameLabel.setText(name);
        userEmailLabel.setText(email);
        userPhoneLabel.setText(phone);
        skillTagsLabel.setText(skills);

        
    }
    
    

}

