package application;

import commonground.*;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		  try { 
			  
			  FactoryClass.createDBConnection("jdbc:sqlserver://Ahmed-PC\\SQLEXPRESS;databaseName=CommonGround;encrypt=false;IntegratedSecurity=true");

			  Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			   
			  Scene scene = new Scene(root);
			   
			  primaryStage.setTitle("CommonGround®"); 
			  				
	          
			  primaryStage.setResizable(true);
			  
			  primaryStage.setScene(scene);
			 
			  primaryStage.show();
		  
		  } 
		  catch(Exception e) { e.printStackTrace(); }
		 
		 
	}
	
	@Override
    public void init() {
        
        for (int i = 1; i <= 100; i++) {
            try {
                Thread.sleep(40); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            
            notifyPreloader(new Preloader.ProgressNotification(i / 100.0));
        }
	}
	
	public static void main(String[] args) {
	    System.setProperty("javafx.preloader", MyPreloader.class.getName());
	    Main.launch(Main.class, args);
	}
}
