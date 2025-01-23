package application;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CircularPreloaderController {
	 	@FXML
	    private StackPane progressCircle;

	    @FXML
	    private Circle dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8;
	    
	    @FXML
	    private ImageView backgroundImage;
	    
	    @FXML
	    private Image turf;

	    public void initialize() {
	        
	        double radius = 15;
	        Circle[] dots = {dot1, dot2, dot3, dot4, dot5, dot6, dot7, dot8};
	        for (int i = 0; i < dots.length; i++) {
	            double angle = 2 * Math.PI * i / dots.length;
	            dots[i].setTranslateX(radius * Math.cos(angle));
	            dots[i].setTranslateY(radius * Math.sin(angle));
	        }

	        // Rotate the entire progressCircle StackPane for the spinning effect
	        RotateTransition rotate = new RotateTransition(Duration.seconds(2), progressCircle);
	        rotate.setByAngle(360);
	        rotate.setCycleCount(RotateTransition.INDEFINITE);
	        rotate.play();
	    }
}

