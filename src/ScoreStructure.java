

public class ScoreStructure {
		private Player player;
		private String username;
		private int gamesStarted;
		private int gamesCompleted;
		private int points;
		private long avgTime;
		private String bestTime;
		private long totalTime;
		
		
		public ScoreStructure(Player player) {
			this.player = player;
			username = player.getUserName();
			gamesStarted = 0;
			gamesCompleted = 0;
			bestTime = player.findFastestGame();
			
			countGames();
		}
		
		public ScoreStructure( String line ) {
			String[] data = line.split(" ");
			this.player = null;
			username = data[0];
			gamesStarted = Integer.parseInt(data[1]);
			gamesCompleted = Integer.parseInt(data[2]);
			points = Integer.parseInt(data[3]);
			avgTime = Long.parseLong(data[4]);
			bestTime = data[5];
		}
		
		public void countGames() {
			for( Game g : player.getGameHistory() ) {
				//games started and completed
				gamesStarted++;
				if( g.isCompleted() ) {
					gamesCompleted++;
				}
				
				//Lifetime Points
				points += g.getScore();
				
				//Total Time
				totalTime += g.getTime();
				
			}
			
			//Average Time
			if( gamesCompleted > 0 ) {
				avgTime = totalTime / gamesCompleted;
			}
		}
		
		public int getPoints() {
			return this.points;
		}
		
		//compares two players. returns the one with the highest points
		public ScoreStructure compareTo( ScoreStructure player2 ) {
			if( this.points > player2.getPoints() ) {
				return this;
			}
			else {
				return player2;
			}
		}
		
		
		//output the same format as is in the CrosswordDB.txt file
		public String toString() {
			String out = "";
			out += username + " ";
			out += gamesStarted + " ";
			out += gamesCompleted + " ";
			out += points + " ";
			out += avgTime + " ";
			out += bestTime + "~\n";
			
			return out;
		}
		
		public String getBestTime() {
			return bestTime;
		}
		public int getGamesCompleted() {
			return gamesCompleted;
		}
		public int getGamesStarted() {
			return gamesStarted;
		}
		public int getScore() {
			return points;
		}
		public String getUsername() {
			return username;
		}
		
	}