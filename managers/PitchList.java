package managers;

import java.sql.SQLException;
import java.util.ArrayList;

import commonground.Pitch;
import dbhandlers.PitchDataHandler;

public class PitchList {
	
	private ArrayList<Pitch> list;
	private ArrayList<Pitch> availablePitches;
	private PitchDataHandler DBHandler;
	
	public PitchList(PitchDataHandler DBHandler) {
		list = new ArrayList<Pitch>();
		availablePitches =  new ArrayList<Pitch>();
		this.DBHandler = DBHandler;
		this.refreshList();
		
	}
	
	public Pitch createPitch(String pitchName, String pitchLocation, String pitchType, String pitchImage, Boolean pitchAvailable, Double bookingPrice ) throws SQLException {
		
		Pitch pitch = new Pitch(DBHandler.generateNewPitchID(), pitchName, pitchLocation, pitchType, pitchImage, pitchAvailable, bookingPrice);
		return pitch;
	}
	
	public boolean addPitch(Pitch pitch, String ownerID) {
		
		try {
				
			if (DBHandler.addPitch(pitch, ownerID)) {
				System.out.println("Pitch added to DB");
	            list.add(pitch);
	            return true;
	            
	        } 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getPitchOwner(String pitchID) {
		return this.DBHandler.getPitchOwner(pitchID);
	}
	
	public ArrayList<Pitch> getPitchesByOwner(String ownerID) throws SQLException{
		return this.DBHandler.getPitchesByOwner(ownerID);
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
			if(pitch.getPitchName().equals(PitchName)) {
				
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
