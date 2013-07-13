public class Puzzle {
	private int size;
	private int puzzleNumber;
	private int numberOfQuestions;
	private QuestionAndAnswer[] across;
	private QuestionAndAnswer[] down;
	private char[] solution;
	private String gameCategory;
	private String puzzleID;
	
	
	public Puzzle() {
		across = null;
		down = null;
		size = 0;
		puzzleID = "";
		puzzleNumber = 0;
		gameCategory = "";
		solution = null;
		
	}
	
	//Auto-generated Getters and Setters
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
		across = new QuestionAndAnswer[ size * size + 1 ];
		down = new QuestionAndAnswer[ size * size + 1 ];
		solution = new char[ size * size + 1 ];
	}

	public QuestionAndAnswer[] getAcross() {
		return across;
	}

	public void setAcross(QuestionAndAnswer[] across) {
		this.across = across;
	}

	public QuestionAndAnswer[] getDown() {
		return down;
	}

	public void setDown(QuestionAndAnswer[] down) {
		this.down = down;
	}

	public String getGameCategory() {
		return gameCategory;
	}
	public void setGameCategory(String gameCategory) {
		this.gameCategory = gameCategory;
	}
	
	public void setPuzzleNumber( String number ) {
		puzzleNumber = Integer.parseInt(number);
	}
	public int getPuzzleNumber() {
		return puzzleNumber;
	}
	public void setPuzzleID() {
		puzzleID += gameCategory + " ";
		puzzleID += puzzleNumber + " ";
		puzzleID += size;
	}

	public String getPuzzleID() {
		return puzzleID;
	}

	public void setSolution() {
		//initialize char[] with a '~' to denote black square
		for( int i = 0; i < size * size + 1; i++ ) {
			solution[i] = '~';
		}
		
		
		//write across words
		for( QuestionAndAnswer word : across ) {
			if( word != null ) {
				int starting = word.getStartingSquare();
				int length = word.getLength();
				//write each letter of the word, from startingSquare
				int count = 0;
				for( int i = starting; i < starting + length; i++ ) {
					solution[i] = word.getAnswer().charAt(count);
					count++;
				}
				numberOfQuestions++;
			}
			
		}
		//write down
		for( QuestionAndAnswer word : down ) {
			if( word != null ) {
				int starting = word.getStartingSquare();
				int length = word.getLength();
				//write each letter of the word, from startingSquare
				int currentSquare = starting;
				for( int count = 0; count < length; count++ ) {
					solution[currentSquare] = word.getAnswer().charAt(count);
					currentSquare += size;
				}
				numberOfQuestions++;
			}
			
		}
		
	}

	public char[] getSolution() {
		return solution;
	}
	
	public String toString() {
		String out = "";
		out += puzzleID + "\n";
		for( QuestionAndAnswer a: across ) {
			if( a != null )
			out += a.getQuestion() + ": " + a.getAnswer() + "\n";
		}
		for( QuestionAndAnswer d: down ) {
			if( d != null )
				out += d.getQuestion() + ": " + d.getAnswer() + "\n";
		}
		return out;
	}
	
	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}
	
	
}
