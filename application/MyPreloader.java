
  package application;
  
  import javafx.application.Preloader; 
  import javafx.fxml.FXMLLoader; 
  import javafx.scene.Parent; 
  import javafx.scene.Scene; 
  import javafx.stage.Stage;
  import javafx.stage.StageStyle;
  
  
  public class MyPreloader extends Preloader {
  
  private Stage preloaderStage;
  
  @Override public void start(Stage primaryStage) throws Exception {
  this.preloaderStage = primaryStage;
  
  // Load the updated FXML file 
  FXMLLoader loader = new FXMLLoader(getClass().getResource("CircularPreloader.fxml")); 
  Parent root = loader.load();
  
  // Create and set up the scene 
  Scene scene = new Scene(root); //
  primaryStage.initStyle(StageStyle.UNDECORATED);
  primaryStage.setScene(scene);
  primaryStage.setTitle("CommonGround®"); primaryStage.show(); }
  
  @Override public void handleStateChangeNotification(StateChangeNotification evt) {
	  if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
		  preloaderStage.hide();
		  }
	  
	  } 
  }
  
 



