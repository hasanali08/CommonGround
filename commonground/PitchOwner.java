package commonground;

import java.util.ArrayList;

public class PitchOwner extends User{
	ArrayList<Pitch> pitches;
	
	public PitchOwner() {
		pitches = new ArrayList<Pitch>();
	}
	
	public void setpitches(ArrayList<Pitch> pitches) {
		
		this.pitches = pitches;
		
	}
	

}
