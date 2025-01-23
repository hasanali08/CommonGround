package commonground;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private int roundNumber;
    private String roundName;
    private ArrayList<Match> matches;

    public Round(int roundNumber, String roundName) {
        this.roundNumber = roundNumber;
        this.roundName = roundName;
        this.matches = new ArrayList<>();
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public String getRoundName() {
        return roundName;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    @Override
    public String toString() {
        return "Round " + roundNumber + " - " + roundName;
    }
}
