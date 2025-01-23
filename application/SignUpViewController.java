package application;

import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import commonground.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.RadioButton;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import managers.UserManager;

public class SignUpViewController {
	@FXML
	private Pane signupPane;
	@FXML
	private Button next_button;
	@FXML
	private TextField username_field;
	@FXML
	private TextField password_field;
	@FXML
	private RadioButton player_button;
	@FXML
	private RadioButton manager_button;
	@FXML
	private RadioButton pitch_owner_button;
	@FXML
	private TextField email_field;
	@FXML
	private TextField last_name_field;
	@FXML
	private TextField first_name_field;
	@FXML
	private TextField phone_number_field;
	@FXML
	private Button login_button;
	
	private UserManager userManager;
	
	public void initialize() throws SQLException {
		
		userManager = FactoryClass.getInstance().getUserManagerInstance();
		login_button.setOnAction(event -> {
			try {
				loadView("MainMenu.fxml");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		});
		
		next_button.setOnAction(event -> {
            try {
                
            	try {
					if(!this.validateFullName() || !this.validateUsername() || !this.validateEmail() || !this.validatePhoneNumber() || !this.validatePassword()) {
						return;
					}
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                String userType = getUserType();
                System.out.println(userType);
                
                User user = createUser(userType);
                if (user == null) {              
                	JOptionPane.showMessageDialog(null, "Failed to signup", "SignUp Failed", JOptionPane.ERROR_MESSAGE);
                	return;
                }
                user.setUserName(username_field.getText());
                user.setFirstName(first_name_field.getText());
                user.setLastName(last_name_field.getText());
                user.setEmail(email_field.getText());
                user.setPhoneNumber(phone_number_field.getText());
                user.setUserType(userType);

 
                boolean success = userManager.signup(user, username_field.getText(), password_field.getText());
                if (success) {
                   
                    if(user instanceof Player) {
                    	
                    	loadView("PlayerPortal.fxml");
                    }
                    else if(user instanceof TeamManager) {
                    	loadView("TeamManagerPortal.fxml");
                    }
                    else if(user instanceof PitchOwner) {
                    	System.out.println("PitchOwner signed in!");
                    	loadView("PitchOwnerPortal.fxml");
                    }
                } else {
                    
                }
            } catch (SQLException | IOException ex) {
               
                ex.printStackTrace(); 
            }
        });
	}
	
	public boolean validateFullName() throws Exception, SQLException {
		if(first_name_field.getText().isBlank() || last_name_field.getText().isBlank()) {
    		JOptionPane.showMessageDialog(null, "Full Name not entered", "SignUp Failed", JOptionPane.ERROR_MESSAGE);
            return false;
    	}
    	return true;
	}
	
	public boolean validatePassword() throws HeadlessException, SQLException {
		if(!password_field.getText().isBlank()) {
    		String password = password_field.getText();
    		
				if(!FactoryClass.getInstance().getUserManagerInstance().isValidPassword(password)) {
					JOptionPane.showMessageDialog(null, "Invalid Password entered, Password should be of 5 or more characters and must have at least one number and one special character", "Invalid Password ", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				
					return true;
    	}
    	else {
    		JOptionPane.showMessageDialog(null, "Password not entered", "Invalid Password", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}

	}
	
	public boolean validateUsername() throws HeadlessException, SQLException {
		 if (!username_field.getText().isBlank()) {
             String username = username_field.getText();

             
             if (!FactoryClass.getInstance().getUserManagerInstance().isValidUsername(username)) {
                 JOptionPane.showMessageDialog(null, "Invalid username format. Username must contain at only alphanumeric characters and underscores.", "Invalid Username", JOptionPane.ERROR_MESSAGE);
                 return false; 
             }

             if (FactoryClass.getInstance().getUserManagerInstance().userExistsByUsername(username)) {
                 JOptionPane.showMessageDialog(null, "Username already exists. Enter a new username.", "Invalid Username", JOptionPane.ERROR_MESSAGE);
                 return false; 
             }
             
             return true;

             
         }
         else {
         	JOptionPane.showMessageDialog(null, "User Name not entered", "Invalid Username", JOptionPane.ERROR_MESSAGE);
         	return false;
         }
	}
	
	public boolean validateEmail() throws HeadlessException, SQLException {
		if(!email_field.getText().isBlank()) {
            String email = email_field.getText();
            if (!FactoryClass.getInstance().getUserManagerInstance().isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email format.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
                return false; 
            } 
            return true;
        }
        else {
        	
            	JOptionPane.showMessageDialog(null, "Email not entered", "Invalid Email", JOptionPane.ERROR_MESSAGE);
            	return false;
            
        }
	}
	
	public boolean validatePhoneNumber() throws HeadlessException, SQLException {
		if(!phone_number_field.getText().isBlank()) {
       	 String phone = phone_number_field.getText();
            if (!FactoryClass.getInstance().getUserManagerInstance().isValidPhoneNumber(phone)) {
                JOptionPane.showMessageDialog(null, "Invalid phone number.", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                return false; 
            } 
            
            return true;
       }
       else {
       	
       	JOptionPane.showMessageDialog(null, "Phone number not entered", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
       	return false;
       }
	}
	
	public void loadView(String string) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(string));
		Stage stage = (Stage) ((Node) login_button).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
	}
	
	private String getUserType() {
        if (player_button.isSelected()) {
            return "Player";
        } else if (manager_button.isSelected()) {
            return "TeamManager";
        } else if (pitch_owner_button.isSelected()) {
            return "PitchOwner"; 
        } else {
            return null; 
        }
    }
	
	private User createUser(String userType) {
        switch (userType) {
            case "Player":
                return new Player(); 
            case "TeamManager":
                return new TeamManager();
            case "PitchOwner":
                return new PitchOwner(); 
            default:
                return null;
        }
    }

}
