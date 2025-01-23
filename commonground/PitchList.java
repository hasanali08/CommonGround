package commonground;

import java.util.ArrayList;

public class PitchList {
	
	private ArrayList<Pitch> list;
	private ArrayList<Pitch> availablePitches;
	private PitchDBHandler DBHandler;
	
	PitchList(PitchDBHandler DBHandler) {
		list = new ArrayList<Pitch>();
		availablePitches =  new ArrayList<Pitch>();
		this.DBHandler = DBHandler;
		
	}
	
	public Pitch createPitch(String pitchID, String pitchName, String pitchLocation, String pitchType, String pitchImage, Boolean pitchAvailable, Double bookingPrice ) {
		
		Pitch pitch = new Pitch(pitchID, pitchName, pitchLocation, pitchType, pitchImage, pitchAvailable, bookingPrice);
		return pitch;
	}
	
	public void addPitch(Pitch pitch) {
		
		try {
				
			if (DBHandler.addPitch(pitch)) {
				System.out.println("Pitch added to DB");
	            list.add(pitch);
	            
	        } 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deletePitch(String pitchID) {
		
		try {
			
			if (DBHandler.deletePitch(pitchID)) {
				
	            list.removeIf(pitch -> pitch.getPitchID().equals(pitchID));
	            
	        	} 
		}
			catch(Exception e) {
				e.printStackTrace();
		}
		
		
	}
	
	public Pitch getPitchByID(String PitchID) {
		
		for(Pitch pitch : list) {
			if(pitch.getPitchID().matches(PitchID)) {
				
				return pitch;
				
			}
		}
		
		return null;
		
	}
	
	public Pitch getPitchByName(String PitchName) {
		
		this.refreshList();
		for(Pitch pitch : list) {
			if(pitch.getPitchID().matches(PitchName)) {
				
				return pitch;
				
			}
		}
		
		return null;
		
	}
	
	public Boolean updatePitch(String PitchID) {
		
					
			return this.DBHandler.updatePitch(this.getPitchByID(PitchID));
				
	}
	
	public void refreshList() {
		
		this.list = this.DBHandler.getAllPitches();
	}
	
	public ArrayList<Pitch> getAvailablePitches() {
		
		this.list = this.DBHandler.getAllPitches();
		this.availablePitches.clear();
		for(Pitch pitch : list) {
			if (pitch.getPitchAvailable()) {
				this.availablePitches.add(pitch);
			}
		}
			
		return this.availablePitches;
	}
	
}
