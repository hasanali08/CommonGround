package commonground;

import java.util.ArrayList;
import java.util.List;

public class KnockoutTournament extends Tournament{
	
    private int tournamentId;
    private String tournamentName;
    private ArrayList<Round> rounds;

    public KnockoutTournament(int tournamentId, String tournamentName) {
        this.tournamentId = tournamentId;
        this.tournamentName = tournamentName;
        this.rounds = new ArrayList<>();
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public void addRound(Round round) {
        rounds.add(round);
    }

    @Override
    public String toString() {
        return "Tournament: " + tournamentName;
    }
	

}
