package dbhandlers;

import java.sql.SQLException;
import java.util.ArrayList;

import commonground.Pitch;

public interface PitchDataHandler {
	public String generateNewPitchID() throws SQLException;
	public ArrayList<Pitch> getAllPitches();
	public boolean addPitch(Pitch pitch, String userID);
	public ArrayList<Pitch> getPitchesByOwner(String pitchOwnerID);
	public String getPitchOwner(String pitchID);
	public boolean updatePitch(Pitch pitch);
	public boolean deletePitch(String pitchID); 
}
