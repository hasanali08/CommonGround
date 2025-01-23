package commonground;

public class Pitch {
	
	private String pitchID;
	private String pitchName;
	private String pitchLocation;
	private String pitchType;
	private String pitchImage;
	private Boolean pitchAvailable;
	private Double bookingPrice;
	
	public Pitch() {
		
		pitchID = pitchName = pitchImage = null;
		pitchAvailable = true;
		bookingPrice = null;
	}
	
	public Pitch(String pitchID, String pitchName, String pitchLocation, String pitchType, String pitchImage, Boolean pitchAvailable, Double bookingPrice ) {
		
		this.pitchID = pitchID;
		this.pitchName = pitchName;
		this.pitchLocation = pitchLocation;
		this.pitchType = pitchType;
		this.pitchImage = pitchImage;
		this.pitchAvailable = pitchAvailable;
		this.bookingPrice = bookingPrice;
		
	}

	public String getPitchType() {
		return pitchType;
	}

	public void setPitchType(String pitchType) {
		this.pitchType = pitchType;
	}

	public String getPitchID() {
		return pitchID;
	}

	public void setPitchID(String pitchID) {
		this.pitchID = pitchID;
	}

	public String getPitchName() {
		return pitchName;
	}

	public void setPitchName(String pitchName) {
		this.pitchName = pitchName;
	}
	
	public String getPitchLocation() {
		return pitchLocation;
	}

	public void setPitchLocation(String pitchLocation) {
		this.pitchLocation = pitchLocation;
	}

	public String getPitchImage() {
		return pitchImage;
	}

	public void setPitchImage(String pitchImage) {
		this.pitchImage = pitchImage;
	}

	public Boolean getPitchAvailable() {
		return pitchAvailable;
	}

	public void setPitchAvailable(Boolean pitchAvailable) {
		this.pitchAvailable = pitchAvailable;
	}

	public Double getBookingPrice() {
		return bookingPrice;
	}

	public void setBookingPrice(Double bookingPrice) {
		this.bookingPrice = bookingPrice;
	}
	
	
	
	
	

}
