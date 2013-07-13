import java.text.DecimalFormat;
import java.util.Date;


public class Game {

	private Puzzle puzzle;
	private boolean completed;
	private int wordsCorrect;
	private int score;
	private long time;
	private String[] across;
	private String[] down;
	private String player1;
	private Player player2;
	private Date date;
	private CountdownClock clock;
	
	public Game() {
		puzzle = null;
		completed = false;
		score = 0;
		time = 0;
		across = null;
		down = null;
		player2 = null;
		date = null;
		clock = new CountdownClock( 9, 59, 1000 );
	}
	
	//save game then mark as saved?
	public void pauseGame(){
		clock.stop();
	}
	public void resumeGame() {
		clock.resume();
	}
	//Auto-generated Getters and Setters
	public Puzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
		across = new String[ puzzle.getSize() * puzzle.getSize() + 1 ];
		down = new String[ puzzle.getSize() * puzzle.getSize() + 1 ];
		initializeArray(across);
		initializeArray(down);
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted() {
		this.completed = true;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String[] getAcross() {
		return across;
	}

	public void setAcross(String[] across) {
		this.across = across;
	}

	public String[] getDown() {
		return down;
	}

	public void setDown(String[] down) {
		this.down = down;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public CountdownClock getClock() {
		return clock;
	}
	
	public String toString() {
		String out = "";
		out += puzzle.getPuzzleID() + " " + completed + " " + score + "~\n";
		out += clock.toString() + "~\n";
		out += formatGuess( puzzle.getAcross() );
		out += formatGuess( puzzle.getDown() );
		return out;
	}
	public void initializeArray( String[] array ) {	
		for( int i = 0; i < array.length; i++ ) {
			array[i] = "";
		}
	}
	
	public String formatGuess( QuestionAndAnswer[] line ) {
		String out = "";
		for( QuestionAndAnswer qAndA : line ) {
			if( qAndA != null ) {
				int startingSquare = qAndA.getStartingSquare();
				String direction = qAndA.getDirection();
				out += qAndA.toString() + " ";
				
				if( direction.equals("ACROSS") && across[startingSquare] != null ) {
					out += across[startingSquare] + "~\n";

				}
				else if ( direction.equals("DOWN") && down[startingSquare] != null) {
					out += down[startingSquare] + "~\n";
				}
				else {
					out += "~\n";
				}

			}
		}
		return out;
	}
	public void setClock( String minute, String second, String bonus ) {
		clock = new CountdownClock( Integer.parseInt(minute), Integer.parseInt(second), Double.parseDouble(bonus) );
	}
	public String formatLabel() {
		String out = "";
		out += puzzle.getGameCategory() + " ";
		out += puzzle.getPuzzleNumber() + " ";
		out += puzzle.getSize() + "x" + puzzle.getSize();
		return out;
	}
	
	public String getPercentComplete() {
		DecimalFormat df = new DecimalFormat("###.##");
		return df.format(((double) wordsCorrect)/puzzle.getNumberOfQuestions() * 100);
	}
	public void incrementWordsCorrect() {
		wordsCorrect++;
	}
	
	public int countWordsCorrect() {
		wordsCorrect = 0;
		//across words
		for( int a = 0; a < across.length; a++ ) {
			if( across[a] != null && across[a].equals(puzzle.getAcross()[a].getAnswer())) {
				wordsCorrect++;
			}
		}
		
		//down words
		for( int d = 0; d < down.length; d++ ) {
			if( down[d] != null && down[d].equals(puzzle.getDown()[d].getAnswer())) {
				wordsCorrect++;
			}
		}
		
		return wordsCorrect;
	}
	public void setUserName( String name ) {
		player1 = name;
	}
	public String getPlayer1() {
		return player1;
	}
	
	

}
