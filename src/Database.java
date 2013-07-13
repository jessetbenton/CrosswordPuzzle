import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;


public class Database {
	
	private ArrayList<Player> playerData;
	private GlobalHighScores globalHighScores;
	
	public String userSection;
	public String globalSection;
	
	public Database() {
		
		setPlayerData(null);
		setGlobalHighScores(null);
		
	}
	
	
	//returns a player if the username(ignoredCase) matches the loggedIn player's username. Returns Null if not found.
	public Player getPlayer( String playerUserName, String password ) {
		//find player name and return player obj
		
		for( Player p : playerData ) {
			if( p.getUserName().equalsIgnoreCase( playerUserName ) && p.getPassword().equals(password)) {
				return p;
			}
		}
		
		return null;
	}

	public void setPlayerData(ArrayList<Player> playerData) {
		this.playerData = playerData;
	}

	public ArrayList<Player> getPlayerData() {
		return playerData;
	}

	public void setGlobalHighScores(GlobalHighScores globalHighScores) {
		this.globalHighScores = globalHighScores;
	}

	public GlobalHighScores getGlobalHighScores() {
		return globalHighScores;
	}
	
	public String toString() {
		String out = "";
		//write users and then global stats
		
		//iterate through players
		//USERS: for each user: Name, registration and every game
		Player p = null;
		for( int i = 0; i < playerData.size(); i++ ) {
			p = playerData.get(i);
			out += p.toString();
			//Add the end of player marker
			if( i < playerData.size() - 1 ) {
				out += "##\n";
			}
			
		}
		
		//end of the USER section
		out += "###\n";
		
		//write global stats
		out += "first 3 2 500 0 4m~\nsecond 3 2 400 0 3m~\nthird 3 3 300 0 0m 16s~\nfourth 3 3 200 0 0m~\nfifth 3 2 100 0 2m~";
		return out;
	}
	public void writeToFile() {
		try{
			// Create file 
			FileWriter fstream = new FileWriter("CrosswordDB.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(this.toString());

			//Close the output stream
			out.close();
		}catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}		
	}
	
	//Add a player to the database
	public void addUser( Player user ) throws UserNameException {
		//check for invalid characters in name
		String username = user.getUserName();
		
		//Check if name is already registered
		for( Player p : playerData ) {
			if( p.getUserName().equalsIgnoreCase(username) ) {
				throw new UserNameException();
			}
		}
		playerData.add(user);
	}
	
	public void findTopPlayers() {
		ScoreStructure[] topPlayers = globalHighScores.getTopScores();
		
		for( Player p : playerData ) {
			if( playerData.isEmpty() ) {
				topPlayers[0] = new ScoreStructure(p);
			}
			else {
				ScoreStructure playerScoreStructure = new ScoreStructure(p); 
				for( int i = 4; i >= 0; i-- ) {
					//compare the player to each topPlayer
					//if the player is better than the topPlayer, shift everyone down
					if( topPlayers[i].compareTo(playerScoreStructure) == playerScoreStructure ) {
						
						//shift the lesser player down
						if( i != 4 ) {
							topPlayers[ i + 1 ] = topPlayers[i];
						}
						topPlayers[i] = playerScoreStructure;
					}
				}
			}
			
		}
		
	}
	
}
