import java.util.ArrayList;
import java.util.Calendar;


public class Player {
	private String password;
	private ArrayList<Game> gameHistory;
	private String userName;
	private long registrationDate;
	
	private int gamesCompleted;
	private int gamesStarted;
	private int totalScore;
	private String bestTime;
	
	
	
	public Player() {
		userName = "";
		registrationDate = 0;
		gameHistory = new ArrayList<Game>();
		registrationDate = Calendar.getInstance().getTimeInMillis();
		
		
	}
	
	public Player( String username, String password ) {
		gameHistory = new ArrayList<Game>();
		this.userName = username;
		this.password = password;
		registrationDate = Calendar.getInstance().getTimeInMillis();
	}
	//Auto-generated Getters and Setters

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(long registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setGameHistory(ArrayList<Game> gameHistory) {
		this.gameHistory = gameHistory;
	}

	public ArrayList<Game> getGameHistory() {
		return gameHistory;
	}
	
	public void addGame( Game game ) {
		gameHistory.add(game);
	}
	
	public Game getGame( int index ) {
		return gameHistory.get(index);
	}
	
	//this method returns the game at the index of the selectedGame. This means
	//that we need to ignore any completed games in the history.
	public Game getSavedGame( int index ) {
		int count = 0;
		for( Game g: gameHistory ) {
			if( g.isCompleted() == false ) {
				if( index == count ) {
					return g;
				}
				else {
					count++;
				}
				
			}
		}
		return null;

	}
	public String toString() {
		

		String out = "";
		
		out += this.userName + " " + this.password + "~\n";

		out += registrationDate + "~\n";
		
		
		
		Game g = null;
		//iterate through player's games
		if( gameHistory != null ) {
			for( int j = 0; j < getGameHistory().size(); j++ ) {
				//get game at index j
				g = getGameHistory().get(j);
				out += g.toString();


				if( j != getGameHistory().size() - 1 ) {
					//Not the last game of user.
					out += "#\n";
				}


			}
		}
		
		
		
		
		return out;
	}
	
	
	//returns the string of the fastest time. **Timer counts DOWN. This method accounts for that fact.
	public String findFastestGame() {
		
		int fastestGame = 0;
		String gameTime = "10m 0s";
		for( Game g: gameHistory ) {
			CountdownClock clock = g.getClock();
			int totalSeconds = clock.getMinute() * 60 + clock.getSecond();
			if( totalSeconds > fastestGame ) {
				fastestGame = totalSeconds;
				gameTime = (9 - clock.getMinute()) + "m " + (60 - clock.getSecond()) + "s";
			}
		}
		
		return gameTime;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
	public void deleteGame( int index ) {
		gameHistory.remove(index);
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setGamesStarted(int gamesStarted) {
		this.gamesStarted = gamesStarted;
	}

	public int getGamesStarted() {
		return gamesStarted;
	}

	public void setGamesCompleted(int gamesCompleted) {
		this.gamesCompleted = gamesCompleted;
	}

	public int getGamesCompleted() {
		return gamesCompleted;
	}

	public void setBestTime() {
		bestTime = findFastestGame();
	}

	public String getBestTime() {
		return bestTime;
	}

}
