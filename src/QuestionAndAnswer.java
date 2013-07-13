
public class QuestionAndAnswer {
	private String answer;
	private int startingSquare;
	private String direction;
	private String question;
	private int length;
	
	public QuestionAndAnswer() {
		answer = "";
		startingSquare = 0;
		direction = "";
		question = "";
		length = 0;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getStartingSquare() {
		return startingSquare;
	}

	public void setStartingSquare(int startingSquare) {
		this.startingSquare = startingSquare;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public String toString() {
		String toReturn = "";
		toReturn += startingSquare + " " + direction;		
		return toReturn;
	}
	
	String out = "";
	

	
}
