package application;

import java.io.IOException;
import java.sql.SQLException;

import commonground.*;

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

public class PitchOwnerPortalController {

	
	
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
	    private Button view_pitches;
	    
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
//	      this.loadView("PlayerHomeView.fxml", true);
	      
	      
	      

	      // Initialize the sidebar menu actions
	      initializeMenuActions();
	    
	      
	        
	      menu_button.setOnAction(event->togglePanel());
	    }
	    
	    
	    private void loadView(String fxmlFile, boolean centerContent) {
	        try {
	            // Load the FXML file dynamically based on the provided file name
	            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
	            Parent view = loader.load();
	            
	            // Clear any previous content from viewPane
	            viewPane.getChildren().clear();

	            if (centerContent) {
	                // Wrap the view in a StackPane to enable centering
	                StackPane stackPane = new StackPane(view);
	                
	                // Add the StackPane to the viewPane
	                viewPane.getChildren().add(stackPane);

	                // Bind the size of the StackPane to the viewPane for dynamic centering
	                stackPane.layoutXProperty().bind(viewPane.widthProperty().subtract(stackPane.widthProperty()).divide(2));
	                stackPane.layoutYProperty().bind(viewPane.heightProperty().subtract(stackPane.heightProperty()).divide(2));
	            } else {
	                // Simply add the view without any wrapping or centering
	                viewPane.getChildren().add(view);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


	    
	    
	    
	    
	    
	    // Centralized method to initialize sidebar actions
	    private void initializeMenuActions() {
	        // Profile Button action to show the Profile View
	        profile_button.setOnAction(event -> loadView("PitchOwnerProfileView.fxml", false));
//	        bookings_button.setOnAction(event -> loadView("BookingView.fxml", false));
	        pitches_button.setOnAction(event -> loadView("CreatePitchView.fxml", false));
	        matches_button.setOnAction(event -> loadView("PitchOwnerMatchView.fxml", false));
	        view_pitches.setOnAction(event -> loadView("PitchOwnerPitchView.fxml", false));
	        
//	        home_button.setOnAction(event -> loadView("PlayerHomeView.fxml", true));

	    }
	    

	    private void adjustLayoutToFullscreen(Stage stage) {
	        // Get the current bounds (width and height) of the stage
	        double width = stage.getWidth();
	        double height = stage.getHeight();

	        // Update the background pane size to cover the entire stage
	        backgroundPane.setPrefWidth(width);
	        backgroundPane.setPrefHeight(height);

	        // Adjust the center pane (image) size to fit the new width and height
	        centerPane.setPrefWidth(width);
	        centerPane.setPrefHeight(height);
	        
	        centerPane.getChildren().forEach(node -> {
	            node.autosize();
	        });
	        
	        viewPane.setPrefHeight(height);
	        viewPane.setPrefWidth(width);
	        
	        
	        
	        
	        
	        titlePane.setPrefWidth(width);
	        titlePane.setPrefHeight(38.0); 

	        // Optionally, adjust the slider size or position
	        slider.setPrefHeight(height);
	    }
	    
	    @FXML
	    private void toggleFullscreen() {
	        // Get the current window (Stage) from any Node, e.g., a button
	        Stage stage = (Stage) ((Node) centerPane).getScene().getWindow();
	        
	        // Toggle fullscreen mode
	        stage.setFullScreen(!stage.isFullScreen());

	        // Adjust layout whenever fullscreen mode is toggled
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
