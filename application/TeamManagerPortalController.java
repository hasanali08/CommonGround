package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TeamManagerPortalController {

		@FXML
		private Button notification_button;
	
	 	@FXML
	    private Button menu_button;

	    @FXML
	    private AnchorPane slider; 

	    @FXML
	    private Pane centerPane; 
	    
	    @FXML
	    private Pane backgroundPane;
	    
	    @FXML
	    private Pane viewPane;
	    
	    @FXML
	    private Button profile_button;
	    
	    @FXML
	    private Button bookings_button;
	    
	    @FXML
	    private Button team_button;
	    
	    @FXML
	    private Button matches_button;
	    
	    @FXML
	    private Button home_button;
	    
	    @FXML
	    private Button pitches_button;
	    
	    @FXML
	    private Parent pitchView; 
	    
	    @FXML
	    private Pane titlePane;

	    private boolean isPanelOpen = false;

	    @FXML
	    public void initialize() throws SQLException {
	    	
	    	
	    	Platform.runLater(() -> {
	            Stage stage = (Stage) ((Node) backgroundPane).getScene().getWindow();
	            
	            
	           
	            stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
	                adjustLayoutToFullscreen(stage);
	            });

	            stage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
	                adjustLayoutToFullscreen(stage);
	            });
	        });
	    	
	      slider.setTranslateX(-200);
	      this.loadView("ManagerHomeView.fxml", true);
	      
	      initializeMenuActions();
	       
	      menu_button.setOnAction(event->togglePanel());
	    }
	    
	    
	    private void loadView(String fxmlFile, boolean centerContent) {
	        try {
	      
	            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
	            Parent view = loader.load();
	            
	            
	            viewPane.getChildren().clear();
	            
	            

	            if (centerContent) {
	          
	                StackPane stackPane = new StackPane(view);
	                
	        
	                viewPane.getChildren().add(stackPane);

	             
	                stackPane.layoutXProperty().bind(viewPane.widthProperty().subtract(stackPane.widthProperty()).divide(2));
	                stackPane.layoutYProperty().bind(viewPane.heightProperty().subtract(stackPane.heightProperty()).divide(2));
	            } else {
	      
	                viewPane.getChildren().add(view);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private void initializeMenuActions() {
	   
	        profile_button.setOnAction(event -> loadView("ProfileView.fxml", false));
	        bookings_button.setOnAction(event -> loadView("BookingView.fxml", false));
	        pitches_button.setOnAction(event -> loadView("PitchView.fxml", false));
	        matches_button.setOnAction(event -> loadView("ManagerMatchView.fxml", false));
	        team_button.setOnAction(event -> loadView("ManagerTeamView.fxml", false));
            home_button.setOnAction(event -> loadView("ManagerHomeView.fxml", true));
	        notification_button.setOnAction(event -> loadView("ManagerNotificationView.fxml", false));

	    }
	    

	    private void adjustLayoutToFullscreen(Stage stage) {
	        
	        double width = stage.getWidth();
	        double height = stage.getHeight();

	        backgroundPane.setPrefWidth(width);
	        backgroundPane.setPrefHeight(height);

	        centerPane.setPrefWidth(width);
	        centerPane.setPrefHeight(height);
	        
	        centerPane.getChildren().forEach(node -> {
	            node.autosize();
	        });
	        
	        viewPane.setPrefHeight(height);
	        viewPane.setPrefWidth(width);
	       
	        titlePane.setPrefWidth(width);
	        titlePane.setPrefHeight(38.0); 

	        slider.setPrefHeight(height);
	    }
	    
	    @FXML
	    private void toggleFullscreen() {

	        Stage stage = (Stage) ((Node) centerPane).getScene().getWindow();

	        stage.setFullScreen(!stage.isFullScreen());

	
	        adjustLayoutToFullscreen(stage);
	    }

	   
	    private void togglePanel() {
	        TranslateTransition slideTransition = new TranslateTransition();
	        slideTransition.setNode(slider);
	        slideTransition.setDuration(Duration.millis(300)); 

	        if (isPanelOpen) {
	            
	            slideTransition.setToX(-200);

	        } else {
	         
	            slideTransition.setToX(0); 
	        }

	        slideTransition.play();
	        isPanelOpen = !isPanelOpen; 
	    }

}
