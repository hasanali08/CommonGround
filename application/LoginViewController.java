package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import commonground.FactoryClass;
import commonground.PitchOwner;
import commonground.Player;
import commonground.TeamManager;
import commonground.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import managers.UserManager;

public class LoginViewController {
	@FXML
	private Pane loginPane;
	@FXML
	private Button login_button;
	
	@FXML
	private Button back_button;
	
	@FXML
	private TextField username_field;
	@FXML
	private TextField password_field;
	
	private UserManager usermanager;
	
	public void initialize() throws SQLException {
		usermanager = FactoryClass.getInstance().getUserManagerInstance();
		username_field.setOnAction(event -> {});
		back_button.setOnAction(event -> {
			try {
				loadView("MainMenu.fxml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		login_button.setOnAction(event -> {
			try {
				User user = usermanager.login(username_field.getText(), password_field.getText());
				if(user!=null) {
					
					if(user instanceof Player) {
						loadView("PlayerPortal.fxml");
					}
					else if(user instanceof TeamManager) {
						loadView("TeamManagerPortal.fxml");
					}
					else if(user instanceof PitchOwner) {
						loadView("PitchOwnerPortal.fxml");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Failed to login", "Login Failed", JOptionPane.ERROR_MESSAGE);
				}
				
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	public void loadView(String string) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(string));
		Stage stage = (Stage) ((Node) loginPane).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
	}

}
