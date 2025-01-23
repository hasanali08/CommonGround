package application;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import commonground.Booking;
import commonground.FactoryClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import managers.BookingList;

public class BookingViewController {

    // Reference to the VBox for ongoing and past bookings
    @FXML
    private VBox ongoingBookingsBox;

    @FXML
    private VBox pastBookingsBox;
    
    private BookingList bookinglist;
    
    private ArrayList<Booking> ongoingBookings;
    
    private ArrayList<Booking> pastBookings;

    // Method to initialize the controller
    @FXML
    public void initialize() throws SQLException {
    	 bookinglist = FactoryClass.getInstance().getBookingListInstance();
         if(bookinglist!=null) {
         ongoingBookings = bookinglist.getOngoingBookings(FactoryClass.getInstance().getUserManagerInstance().getUser().getUserID());
         pastBookings = bookinglist.getPastBookings(FactoryClass.getInstance().getUserManagerInstance().getUser().getUserID());
         if(ongoingBookings!=null) {
         	for(Booking booking : ongoingBookings) {
         		if(booking!=null) {
         			String value = "Booking #" + booking.getBookingID() + ": " + booking.getBookingDate() + ": "+booking.getBookingStatus();
         			addOngoingBooking(value);
         		}
         	}
         }
         }
         else {
         	JOptionPane.showMessageDialog(null, "Failed to load bookings", "Booking retrieval failed", JOptionPane.ERROR_MESSAGE);
         }
        
    }

    // Method to add a new ongoing booking
    public void addOngoingBooking(String bookingDetails) {
        // Create a new HBox for the ongoing booking
        HBox bookingItem = createBookingItem(bookingDetails);

        // Add the new HBox to the ongoingBookingsBox VBox
        ongoingBookingsBox.getChildren().add(bookingItem);
    }

    // Method to add a new past booking
    public void addPastBooking(String bookingDetails) {
        // Create a new HBox for the past booking
        HBox bookingItem = createBookingItem(bookingDetails);

        // Add the new HBox to the pastBookingsBox VBox
        pastBookingsBox.getChildren().add(bookingItem);
    }

    // Helper method to create a booking item as an HBox
    private HBox createBookingItem(String bookingDetails) {
        // Create a new HBox
        HBox bookingItem = new HBox();
        bookingItem.setSpacing(20);
        bookingItem.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-padding: 10;");

        // Create a Label with the booking details
        Label bookingLabel = new Label(bookingDetails);
        bookingLabel.setStyle("-fx-font-size: 14px;");

        // Create a "View Details" button
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setStyle("-fx-background-color: #3c791b; -fx-text-fill: WHITE;");

        // Optionally, add an action listener to the button
        viewDetailsButton.setOnAction(event -> {
            // Handle the action when "View Details" is clicked
            System.out.println("Viewing details for: " + bookingDetails);
        });

        // Add the Label and Button to the HBox
        bookingItem.getChildren().addAll(bookingLabel, viewDetailsButton);

        return bookingItem;
    }
}

