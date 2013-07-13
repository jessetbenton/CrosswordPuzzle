import java.util.ArrayList;


public class GlobalHighScores {
	final int NUM_PLAYERS = 5;
	private ScoreStructure[] topPlayers;
	private ArrayList<Player> allPlayers;
	
	public GlobalHighScores( ArrayList<Player> playerList ) {
		topPlayers = new ScoreStructure[ NUM_PLAYERS ];
		allPlayers = playerList;
	}
	
	//for reading scores from CrosswordDB.txt
	public GlobalHighScores( ScoreStructure[] playerScores ) {
		topPlayers = playerScores;
		allPlayers = null;	
		
	}
	
	public void findTopPlayers() {
		for( Player p : allPlayers ) {
			if( allPlayers.isEmpty() ) {
				topPlayers[0] = new ScoreStructure(p);
			}
			else {
				ScoreStructure nextPlayer = new ScoreStructure(p); 
				for( int i = 0; i < NUM_PLAYERS; i++ ) {
					//compare the player to each topPlayer
					//if the player is better than the topPlayer, shift everyone down
					if( topPlayers[i].compareTo(nextPlayer).equals(nextPlayer)) {
						
						ScoreStructure pCurrent = nextPlayer;
						ScoreStructure pNext = topPlayers[i];
						
						
						for( int j = i; j < NUM_PLAYERS - 1; j++ ) {
							topPlayers[j] = pCurrent;
							pCurrent = pNext;
							pNext = topPlayers[j + 1];
						}
					
						
					}
				}
			}
			
		}
		
		
	}
	
	//to write to crosswordDB.txt
	public String toString() {
		String out = "";
		for( ScoreStructure s: topPlayers ) {
			out += s.toString();
		}
		
		
		
		return out;
	}
	
	public ScoreStructure[] getTopScores() {
		
		return topPlayers;
	}
	
	
	
 	
	
	
}
