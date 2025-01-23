package application;

import java.io.IOException;
import java.sql.SQLException;

import commonground.*;

//
//import commonground.FactoryClass;
//
//import commonground.PitchList;
//
//import java.util.ArrayList;
//
//import javafx.animation.TranslateTransition;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//
//import javafx.scene.control.Button;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.text.Text;
//import javafx.util.Duration;
//
//public class PlayerPortalController {
//	
//	@FXML
//	private AnchorPane slider;
//	
//	@FXML
//	private Button menu_button;
//	
//	@FXML
//	private BorderPane mainBorderPane;
//	
//	private ArrayList<String> pitchNames;
//	
//	private PitchList pitchlist;
//	
//	private String currentPitchID;
//	
//	private boolean isPanelOpen = false; // Track the state of the panel
//	
//	public void initialize() {
//		
//		
//		slider.setTranslateX(-200); // Hide the panel initially (adjust width as needed)
//		mainBorderPane.setLeft(null);
//
//		
//        menu_button.setOnAction(event -> togglePanel());
//
//       
//		
//		pitchlist = FactoryClass.getInstance().getPitchListInstance();
//		pitchlist.refreshList();
//		pitchlist.getPitchByID("1").setPitchImage("/assets/img/turf_background.jpg");
//		pitchlist.updatePitch("1");
//		
//		
//	    Image image = new Image(getClass().getResource(pitchlist.getAvailablePitches().getFirst().getPitchImage()).toExternalForm());
//	            
//	    
////	    for(Pitch pitch :pitchlist.getAvailablePitches()) {
////	    	System.out.println(pitch.getPitchName());
////	    }
//		 
//	}
//	
//	private void togglePanel() {
//        TranslateTransition slideTransition = new TranslateTransition();
//        slideTransition.setNode(slider);
//        slideTransition.setDuration(Duration.millis(300)); // Animation duration in milliseconds
//        
//        if (isPanelOpen) {
//            // Close the panel
//            slideTransition.setToX(-200); // Slide to hide the panel (adjust based on your panel width)
//            slideTransition.setOnFinished(event -> {
//                mainBorderPane.setLeft(null); // Remove the left region to expand the centerPane
//            });
//                 } else {
//            // Open the panel
//            mainBorderPane.setLeft(slider);
//            slider.setTranslateX(-200);
//            slideTransition.setToX(0); // Slide to show the panel
//           
//        }
//        
//        slideTransition.play();
//        isPanelOpen = !isPanelOpen; // Toggle the state
//    }
//
//}
//
//
//



import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import managers.UserManager;

public class PlayerPortalController {

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
    private Button notification_button;
    
    @FXML
    private Parent pitchView; 
    
    @FXML
    private Pane titlePane;

    private boolean isPanelOpen = false;
    
    private UserManager userManager;
    
    private Player player;

    @FXML
    public void initialize() throws SQLException {
    	
    	userManager = FactoryClass.getInstance().getUserManagerInstance();
    	player = (Player) userManager.getUser();
    	
    	Platform.runLater(() -> {
            Stage stage = (Stage) ((Node) backgroundPane).getScene().getWindow();
            
            
            // Add a listener to detect when the window is resized
            stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                adjustLayoutToFullscreen(stage);
            });

            stage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                adjustLayoutToFullscreen(stage);
            });
        });
    	
      slider.setTranslateX(-200);
      this.loadView("PlayerHomeView.fxml", true);
      
      
      

 
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


    
    
    
    
    
    // Centralized method to initialize sidebar actions
    private void initializeMenuActions() {
        // Profile Button action to show the Profile View
        profile_button.setOnAction(event -> loadView("ProfileView.fxml", false));
        team_button.setOnAction(event -> loadView("TeamView.fxml", false));
        bookings_button.setOnAction(event -> loadView("BookingView.fxml", false));
        pitches_button.setOnAction(event -> loadView("PitchView.fxml", false));
        matches_button.setOnAction(event -> loadView("MatchView.fxml", false));
        home_button.setOnAction(event -> loadView("PlayerHomeView.fxml", true));
        notification_button.setOnAction(event -> loadView("PlayerNotificationView.fxml", false));

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


