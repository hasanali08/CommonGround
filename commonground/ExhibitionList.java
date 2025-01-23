package commonground;

import java.util.ArrayList;

public class ExhibitionList {
	
		private ArrayList<Match> list;
		
		ExhibitionList() {
			list = new ArrayList<Match>();
		}
		
		public Match getMatchByDate(String matchDate) {
			
			for(Match match: list) {
				if(match.getMatchDate().equals(matchDate)) {
					return match;
				}
			}
			
			return null;
			
		}
		
		

	


}
