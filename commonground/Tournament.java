package commonground;

import java.util.ArrayList;

public abstract class Tournament {
	
	private String tournamentID;
	private String tournamentName;
	private String tournamentStartDate;
	private String tournamentStatus;
	private String tournamentCreator;
	
	
	Tournament() {
		tournamentID = tournamentStartDate = null;
		tournamentStatus = "Not Started";
		tournamentCreator = null;
		
	}

	public String getTournamentID() {
		return tournamentID;
	}

	public void setTournamentID(String tournamentID) {
		this.tournamentID = tournamentID;
	}

	public String getTournamentStartDate() {
		return tournamentStartDate;
	}

	public void setTournamentStartDate(String tournamentStartDate) {
		this.tournamentStartDate = tournamentStartDate;
	}

	public String getTournamentStatus() {
		return tournamentStatus;
	}

	public void setTournamentStatus(String tournamentStatus) {
		this.tournamentStatus = tournamentStatus;
	}

	public String getTournamentCreator() {
		return tournamentCreator;
	}

	public void setTournamentCreator(String tournamentCreator) {
		this.tournamentCreator = tournamentCreator;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
	
	
	
	

}
