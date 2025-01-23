package managers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import commonground.Booking;
import dbhandlers.BookingDBHandler;

public class BookingList {
    private ArrayList<Booking> bookings;
    private BookingDBHandler DBHandler;
    

    public BookingList(BookingDBHandler DBHandler) {
        bookings = new ArrayList<>();
        this.DBHandler = DBHandler;
        
    }

//    public Booking createBooking(String bookingID, String userID, String pitchID, String bookingDate) {
//    Booking booking = new Booking(pitchID);
//    booking.setBookingID(bookingID);
//    booking.setUserID(userID);
//    booking.setBookingDate(bookingDate);
//    return booking;
//    }
        
    public Boolean addMatchForBooking(String bookingID, String matchID) {
    	this.bookings = DBHandler.getAllBookings();
    	for(Booking booking : bookings) {
    		if(booking.getBookingID().equals(bookingID)) {
    			booking.setMatchID(matchID);
    			DBHandler.updateBooking(booking);
    			return true;
    		}
    	}
    	return false;
    }
    
   public Booking createNewBooking(String userID, String pitchID) {
	   
	   if(this.canPlayerBook(userID)) {
		   Booking booking = new Booking();
		   booking.setBookingID(DBHandler.generateNewBookingID());
		   booking.setUserID(userID);
		   booking.setPitchID(pitchID);
		   booking.setBookingStatus("Ongoing");
		   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		   booking.setBookingDate(LocalDate.now().format(formatter));
		   booking.setMatchID(null);
		   this.addBooking(booking);
		   return booking;
	   }
	   else {
	   
		   return null;
	   }
	   
	   
   }

    public Boolean addBooking(Booking booking) {
        try {
            if (DBHandler.addBooking(booking)) {
                System.out.println("Booking added to DB");
                bookings.add(booking);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteBooking(String bookingID) {
        try {
            if (DBHandler.deleteBooking(bookingID)) {
                bookings.removeIf(booking -> booking.getBookingID().equals(bookingID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    


     public boolean canPlayerBook(String playerID) {
         this.refreshList();
         int count = 0;
         for (Booking booking : this.bookings) {
             if (booking.getUserID()!=null && booking.getUserID().equals(playerID) && booking.getBookingStatus()!=null && !booking.getBookingStatus().equals("Completed")) {
                 count++;
                 if (count >= 2) { 
                     return false; 
                 }
             }
         }
         return true; 
     }

    public Booking getBookingByID(String bookingID) {
    	this.refreshList();
        for (Booking booking : bookings) {
            if (booking.getBookingID().equals(bookingID)) {
                return booking;
            }
        }
        return null;
    }
    
    public boolean setBookingStatus(String bookingID, String bookingStatus) {
    	
    	Booking booking = getBookingByID(bookingID);
    	if(booking != null) {
    		booking.setBookingStatus(bookingStatus);
    		this.updateBooking(booking);
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    public ArrayList<Booking> getPastBookings(String playerID) {
       
       this.refreshList();
       ArrayList<Booking> pastBookings = new ArrayList<Booking>();
       for(Booking booking : this.bookings) {
    	   if(booking.getUserID().equals(playerID) && booking.getBookingStatus().equals("Past")) {
    		   pastBookings.add(booking);
    	   }
       }
       
      return pastBookings;
    }

    public void updateBooking(Booking booking) {
        if (DBHandler.updateBooking(booking)) {
            System.out.println("Booking updated in DB");
        }
    }

    public void refreshList() {
        this.bookings = DBHandler.getAllBookings();
    }

    public ArrayList<Booking> getAllBookings() {
        this.refreshList();
        return bookings;
    }

    public ArrayList<Booking> getBookingsOnDate(String bookingDate) {
    	ArrayList<Booking> bookingsOnDate = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate targetDate = LocalDate.parse(bookingDate, formatter);

            for (Booking booking : bookings) {
                LocalDate bookingLocalDate = LocalDate.parse(booking.getBookingDate(), formatter);
                if (bookingLocalDate.isEqual(targetDate)) {
                    bookingsOnDate.add(booking);
                }
            }
        } catch (Exception e) {
            System.err.println("Invalid date format: " + e.getMessage());
        }

        return bookingsOnDate;
    }

	public ArrayList<Booking> getOngoingBookings(String playerID) {
		this.refreshList();
	       ArrayList<Booking> pastBookings = new ArrayList<Booking>();
	       for(Booking booking : this.bookings) {
	    	   if(booking.getUserID().equals(playerID) && booking.getBookingStatus().equals("Ongoing")) {
	    		   pastBookings.add(booking);
	    	   }
	       }
	       
	      return pastBookings;
	}
}
