package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class MainMenuController {
	@FXML
	private Button login_button;
	
	@FXML
	private Button signup_button;
	
	public void initialize() {
		login_button.setOnAction(event -> {
			try {
				loadView("LoginView.fxml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		signup_button.setOnAction(event -> {
			try {
				loadView("SignUpView.fxml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	public void loadView(String string) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(string));
		Stage stage = (Stage) ((Node) login_button).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
	}
	
}
