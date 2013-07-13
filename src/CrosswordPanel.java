import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;



public class CrosswordPanel extends JPanel {
	public CrosswordPanel() {
	}

	private static final long serialVersionUID = 1L;

	static int CENTER_X = 205;
	static int CENTER_Y = 264;
	static int GRIDSIZE = 25;
	static int GRID_WIDTH = 25;
	static int GRID_HEIGHT = 25;
	static int QUESTIONBOX_WIDTH = 265;
	static int QUESTIONBOX_HEIGHT = 165;
	static Color COLOR = new Color(125,160,193);

	private Puzzle puzzle;
	private Game game;
	private String[] across;
	private String[] down;
	private JList across_JList;
	private JList down_JList;
	private JButton saveQuitButton; 
	private JButton pauseButton;
	private JButton finishedPuzzleButton;
	private JButton backButton;
	private JScrollPane acrossAnswerPane; 
	private JScrollPane downAnswerPane;
	private Vector<String> acrossList;
	private Vector<String> downList;
	private JTextField grid[]; 
	private JLabel downLabel;
	private JLabel acrossLabel;
	private int clueStarting;
	private int textFieldFocus;
	private MouseListener downListen;
	private MouseListener acrossListen;
	private JLabel pauseLabel;
	private JLabel scoreLabel;
	private JLabel instructionLabel;
	
	private String selectedDirection;
	private CountdownClock clock;

	//initializing the pane
	public void init( Game game ) {
		
		
		this.setLayout(null);
		this.game = game;
		this.puzzle = game.getPuzzle();
		grid = new JTextField[ puzzle.getSize() * puzzle.getSize() + 1 ];
		this.clock = game.getClock();
		
		if( this.clock == null ) {
			this.clock = new CountdownClock(10, 0, 1000);
		}
		
		
		//Establish Questions Lists to be inserted
		acrossList = answersToVector(puzzle.getAcross(), "ACROSS");
		downList = answersToVector(puzzle.getDown(), "DOWN");
		across_JList = new JList(acrossList);
		across_JList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		acrossListen =  new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				removeAcrossQuestionSelection();
				String question = "";
				JList list = (JList) arg0.getSource();
				
				removeBackgroundHighlight();
				question = list.getSelectedValue().toString();
				clueStarting = startingSquare(question);
				textFieldFocus = startingSquare(question);
				selectedDirection = "ACROSS";
				highlightBackground();	
				selectLetter(clueStarting);
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {	
			}
			@Override
			public void mousePressed(MouseEvent arg0) {	
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {				
			}
			
		};
		across_JList.addMouseListener( acrossListen );
		down_JList = new JList(downList);
		down_JList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		downListen = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				removeDownQuestionSelection();
				String question = "";
				JList list = (JList) arg0.getSource();

				removeBackgroundHighlight();
				question = list.getSelectedValue().toString();
				clueStarting = startingSquare(question);
				textFieldFocus = startingSquare(question);
				selectedDirection = "DOWN";
				highlightBackground();		
				selectLetter(clueStarting);
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {	
			}
			@Override
			public void mousePressed(MouseEvent arg0) {	
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {				
			}
			
		};
		down_JList.addMouseListener(downListen);
		
		// ScoreLabel
		scoreLabel = new JLabel();
		scoreLabel.setFont(new Font("Impact", Font.PLAIN, 24));
		scoreLabel.setForeground(Color.red);
		add(scoreLabel);
		scoreLabel.setVisible(false);
		

		//---- instructionLabel ----
		instructionLabel = new JLabel();
		instructionLabel.setText("Select clue to enter word");
		instructionLabel.setFont(new Font("Courier New", Font.ITALIC, 15));
		add(instructionLabel);
		instructionLabel.setBounds(new Rectangle(new Point(125, 73), instructionLabel.getPreferredSize()));
		
		
		
		//Clock
		clock.setBounds(new Rectangle(new Point(125, 25), clock.getPreferredSize()));
		add(clock);	
		clock.resume();
		
		
		//Pause Button
		pauseButton = new JButton();
		pauseButton.setText("Pause");
		pauseButton.setBounds(190, 475, 90, 30);
		pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if( clock.isPaused() ) {
					pauseButton.setText("Pause");
					pauseLabel.setVisible(false);
					pauseButton.setForeground(Color.black);
					enableCrosswordInteraction();
					clock.resume();
				}
				else {
					pauseButton.setText("Continue");
					pauseLabel.setVisible(true);
					pauseButton.setForeground(new Color(204, 0, 51));
					disableCrosswordInteraction();
					clock.stop();
				}
				
			}
			
		});
		add(pauseButton);
		
		
		
		//Save&Quit Button
		saveQuitButton = new JButton();
		saveQuitButton.setText("Save/Quit");
		saveQuitButton.setBounds(pauseButton.getX() + 120, pauseButton.getY(), 90, 30);
		saveQuitButton.addActionListener(new SaveQuitListener(this));
		add(saveQuitButton);
		
		
		//Back Button
		backButton = new JButton();
		backButton.setText("Back");
		backButton.setBounds(pauseButton.getX() - 120, pauseButton.getY(), 90, 30);
		backButton.setVisible(false);
		class BackButtonListener implements ActionListener {
			CrosswordPanel pane;
			public BackButtonListener( CrosswordPanel pane ) {
				this.pane = pane;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				backToParent(pane);
			}
			
		}
		backButton.addActionListener(new BackButtonListener(this));
		add(backButton);

		//Finish Puzzle Button
		finishedPuzzleButton = new JButton();
		finishedPuzzleButton.setText("Finish");
		finishedPuzzleButton.setBounds(pauseButton.getX() - 120, pauseButton.getY(), 90, 30);
		finishedPuzzleButton.addActionListener(new FinishPuzzleListener(this) );
		add(finishedPuzzleButton);
		
		//Across Answer Scrollable Pane
		acrossAnswerPane = new JScrollPane();
		acrossAnswerPane = new JScrollPane(across_JList);
		acrossAnswerPane.setBounds(470, 105, QUESTIONBOX_WIDTH, QUESTIONBOX_HEIGHT);
		
		add(acrossAnswerPane);
		
		//Down Answer Scrollable Pane
		downAnswerPane = new JScrollPane();
		downAnswerPane = new JScrollPane(down_JList);
		downAnswerPane.setBounds(470, 310, QUESTIONBOX_WIDTH, QUESTIONBOX_HEIGHT);
		add(downAnswerPane);
		
		//Username Labels
		acrossLabel = new JLabel();
		downLabel = new JLabel();
		//2 player game, add player names to board
		if( game.getPlayer2() != null ) {
			acrossLabel.setText("Across: ( " + game.getPlayer1() + " )" );
			downLabel.setText("Down: ( " + game.getPlayer2().getUserName() + " )");

		}
		else {
			//Across Answer Label
			acrossLabel.setText("Across:");
			//Down Answer Label
			downLabel.setText("Down:");
		}
		
		acrossLabel.setBounds(new Rectangle(new Point(470, 85), acrossLabel.getPreferredSize()));
		add(acrossLabel);
		


		//downLabel.setFont(new Font("Impact", Font.PLAIN, 14));
		downLabel.setBounds(new Rectangle(new Point(470, 290), downLabel.getPreferredSize()));
		downLabel.setVisible(true);
		add(downLabel);




		
		//draw grid and blackout squares not in use
		crosswordGRID();
		blackOutEmptySquares();
		
		//resume state of board, if loading saved game
		//ACROSS words
		for( int a = 0; a < game.getAcross().length; a++ ) {
			String word = game.getAcross()[a];
			if( word != null && word.length() > 0 ) {
				for( int i = 0; i < word.length(); i++ ) {
					grid[a + i].setText("" + word.charAt(i));
				}
			}
		}
		
		//DOWN words
		for( int d = 0; d < game.getDown().length; d++ ) {
			String word = game.getDown()[d];
			if( word != null && word.length() > 0 ) {
				for( int i = 0; i < word.length(); i++ ) {
					grid[d + i * puzzle.getSize() ].setText("" + word.charAt(i));
				}
			}
		}
		
		
		
		//Pause Label
		pauseLabel = new JLabel("PAUSED");
		pauseLabel.setFont(new Font( "Imact", Font.PLAIN, 30 ));
		pauseLabel.setVisible(false);
		pauseLabel.setForeground(new Color(204, 0, 51));
		pauseLabel.setBounds( CENTER_X - 25, 25, 200, 200);
		add(pauseLabel);
		
		
		//showSolution();

		this.setSize(800,650);
		
		

	}
	
	//returns as a Vector String, a list of questions in the QandA obj format
	//to be inserted into the question boxes
	public Vector<String> answersToVector( QuestionAndAnswer list[], String acrossDown ) {
		Vector<String> out = new Vector<String>();
		for(int i = 0; i < puzzle.getSize() * puzzle.getSize() + 1; i++ ) {
			QuestionAndAnswer[] all;
			if( acrossDown.equals("ACROSS") ) {
				all = puzzle.getAcross();
				if( all[i] != null ) {
					out.add( i + ". " + puzzle.getAcross()[i].getQuestion() );
				}
			}
			else {
				all = puzzle.getDown();
				if( all[i] != null ) {
					out.add( i + ". " + puzzle.getDown()[i].getQuestion() );
				}
			}
		}
		
		return out;
	}

	//Draws the grid. Bounds set to the GRIDSIZE, GRID_WIDTH, and GRID_HEIGHT 
	//defined at the top of the class. 	
	public void crosswordGRID() {
		int numberOfSquares = puzzle.getSize() * puzzle.getSize() + 1;
		grid = new JTextField[ numberOfSquares ];

		int starting_X = CENTER_X - ( GRIDSIZE * ( puzzle.getSize() / 2 ) );
		int starting_Y = CENTER_Y - ( GRIDSIZE * ( puzzle.getSize() / 2 ) );

		int offset = 1;
		for( int col = 1; col <= puzzle.getSize(); col++ ) {
			for( int row = 1; row <= puzzle.getSize(); row++ ) {
				grid[offset] = new JTextField();
				grid[offset].setBounds( starting_X + row*GRIDSIZE + 4, starting_Y + col*GRIDSIZE + 4, GRID_WIDTH, GRID_HEIGHT);
				grid[offset].setBorder(BorderFactory.createLineBorder(Color.black));
				grid[offset].setHorizontalAlignment(JTextField.CENTER);
				grid[offset].setCaretColor(Color.RED);
				grid[offset].setFont(new Font("Lucida Grande",Font.BOLD, 13));
				grid[offset].getDocument().addDocumentListener(new TextFieldListener());
				add(grid[offset]);
				offset++;

			}
		}

	}

	//Shows the solution to the puzzle
	public void showSolution() {
		//skip adding the '~' as it represents a black square
		char[] solution = puzzle.getSolution();
		for( int i = 1; i < solution.length; i++ ) {
			if( solution[i] != '~' ) {
				grid[i].setText("" + solution[i]);
			}
		}	
	}
	//blacks out the squares that aren't used. Denoted as a '~' in the solution char[]
	public void blackOutEmptySquares() {
		char[] solution = puzzle.getSolution();
		for( int i = 1; i < solution.length; i++ ) {
			if( solution[i] == '~' ) {
				grid[i].setBackground(Color.black);
				grid[i].setEditable(false);
			}
		}
	}
	

	class SaveQuitListener implements ActionListener {

		CrosswordPanel pane;	
		public SaveQuitListener( CrosswordPanel pane ) {
			this.pane = pane;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//save
			save();
			//quit. Take back to choose puzzle screen
			backToParent(pane);
		} 



	}
	
	class FinishPuzzleListener implements ActionListener{

		CrosswordPanel pane;

		public FinishPuzzleListener( CrosswordPanel cp ) {
			pane = cp;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			game.setCompleted();			
			//Save MUST come first. 
			//save sets across[] and down[] needed to calculate score
			save();
			calculateScore();
			scoreLabel.setText( "Final Score: " +(Integer.toString(game.getScore())));
			scoreLabel.setBounds(new Rectangle(new Point(490, 485), scoreLabel.getPreferredSize()));
			scoreLabel.setVisible(true);
			finishedPuzzleButton.setVisible(false);
			saveQuitButton.setVisible(false);
			pauseButton.setVisible(false);
			
			backButton.setVisible(true);	
			
		}
		
	}
	
		
	class TextFieldListener implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent arg0) {
			if( selectedDirection != null ) {
				int length;
				if( selectedDirection.equals("ACROSS") ) {
					//don't go past length of word
					length = puzzle.getAcross()[clueStarting].getLength();
					if( textFieldFocus - clueStarting < length - 1 ) {
						textFieldFocus++;
						selectLetter(textFieldFocus);
					}
					else {
						//remove focus
						acrossAnswerPane.requestFocus();
						
					}
				}
				else if( selectedDirection.equals("DOWN") ) {
					length = puzzle.getDown()[clueStarting].getLength();
					if( ( textFieldFocus - clueStarting ) / puzzle.getSize() < length - 1 ) {
								
						textFieldFocus += puzzle.getSize();
						selectLetter(textFieldFocus);
					}
					else {
						//remove focus	
						downAnswerPane.requestFocus();
					}				
				}
			}		}

		//for deleting letters and advancing
		@Override
		public void removeUpdate(DocumentEvent arg0) {
			
		}
		@Override
		public void changedUpdate(DocumentEvent arg0) {
			
		}
		
	}
	
	//returns the starting square based on the clue
    public int startingSquare( String clue ) {
    	int num = clue.indexOf('.');
    	return Integer.parseInt(clue.substring(0, num ));
    }
    
    //returns the starting square from a grid square clicked
    public int startingSquare( int index ) {
    	
    	return 0;
    }
    
    public void highlightBackground() {
    	if( selectedDirection.equals("ACROSS") ) {
    		int length = puzzle.getAcross()[clueStarting].getLength();
        	for( int i = clueStarting; i < clueStarting + length; i++ ) {
        		grid[i].setBackground(COLOR);
        	}
    	}
    	else if( selectedDirection.equals("DOWN") ) {
    		int length = puzzle.getDown()[clueStarting].getLength();
        	for( int i = 0; i < length; i++ ) {
        		grid[ i * puzzle.getSize() + clueStarting ].setBackground(COLOR);
        	}
    	}
    }
    
    public void highlightAcross() {
    	int length = puzzle.getAcross()[clueStarting].getLength();
    	for( int i = clueStarting; i < clueStarting + length; i++ ) {
    		grid[i].setBackground(COLOR);
    	}
    }
    
    public void highlightDown() {
    	int length = puzzle.getDown()[clueStarting].getLength();
    	for( int i = 0; i < length; i++ ) {
    		grid[ i * puzzle.getSize() + clueStarting ].setBackground(COLOR);
    	}
    	
    }
    public void removeBackgroundHighlight() {
    	if( clueStarting != 0 && selectedDirection != null ) {
    		int length = -1;
    		if( selectedDirection.equals("ACROSS") ) {
    			length = puzzle.getAcross()[clueStarting].getLength();
        		for( int i = clueStarting; i < clueStarting + length; i++ ) {
        			grid[i].setBackground(Color.white);
        		}
    		}
    		else if( selectedDirection.equals("DOWN") ) {
    			length = puzzle.getDown()[clueStarting].getLength();
    			for( int i = 0; i < length; i++ ) {
    	    		grid[ i * puzzle.getSize() + clueStarting ].setBackground(Color.white);
    	    	}
    		}
    	}
    }
    
    //pull user data from grid and add it to the game object
    public void save() {
    	
    	clock.stop();
    	
    	across = new String[puzzle.getSize() * puzzle.getSize() + 1];
    	down = new String[puzzle.getSize() * puzzle.getSize() + 1];
    	int length = -1;
    	String input = "";
    	
    	//across

    	//work through the acrossList, pulling out each character, adding it to a string
    	int num = across_JList.getModel().getSize(); 
    	for( int i = 0; i < num; i++ ) {
    		input = "";
    		int startingSquare = startingSquare((String) across_JList.getModel().getElementAt(i));
    		length = puzzle.getAcross()[startingSquare].getLength();
    		//iterate over each square that the clue corresponds to.
    		for( int j = startingSquare; j < startingSquare + length; j++ ) {
    			input += grid[j].getText();
    		} 		
        	//if the string is the same length as the answer, we accept it as a complete answer.
    		if( input.length() == puzzle.getAcross()[startingSquare].getLength() ) {
    			across[startingSquare] = input;
    		}
    		else {
    			across[startingSquare] = "";
    		}
    		
    	}
    		
    	
    	
    	
    	
    	//down
    	
    	//work through the acrossList, pulling out each character, adding it to a string
    	num = down_JList.getModel().getSize(); 
    	for( int i = 0; i < num; i++ ) {
    		input = "";
    		int startingSquare = startingSquare((String) down_JList.getModel().getElementAt(i));
    		length = puzzle.getDown()[startingSquare].getLength();
    		//iterate over each square that the clue corresponds to.
    		for( int j = 0; j < length; j++ ) {
    			input += grid[ j * puzzle.getSize() + startingSquare ].getText();;
    		}
        	//if the string is the same length as the answer, we accept it as a complete answer.
    		if( input.length() == puzzle.getDown()[startingSquare].getLength() ) {
    			down[startingSquare] = input;
    		}
    		else {
    			down[startingSquare] = "";
    		}
    		
    	}
    	
    	//write to game object
    	game.setAcross(across);
    	game.setDown(down);
    	game.setScore(calculateScore());
    	((crosswordGUI) this.getParent()).getDB().findTopPlayers();
    	((crosswordGUI) this.getParent()).getDB().writeToFile();
    	
    	
    	
    	
    }
    
	public void selectLetter( int textField ) {
		grid[textField].requestFocus();
		grid[textField].setSelectedTextColor(new Color(250,0,0));
		grid[textField].setSelectionColor(COLOR);
		grid[textField].selectAll();
	}
    

	public int calculateScore() {
		disableCrosswordGrid();
		removeBackgroundHighlight();
		int score = 0;
		String userAnswer = "";
		//calculate across score
		for( int acrossIndex = 0; acrossIndex < across.length; acrossIndex++ ) {
			if( across[acrossIndex] != null ) {
				userAnswer = getAcrossWord(acrossIndex);
				if( userAnswer.equalsIgnoreCase(puzzle.getAcross()[acrossIndex].getAnswer())) {
					score += 200;
					highlightCorrectAnswer("ACROSS", acrossIndex);
				}
				else {
					highlightWrongAnswer( "ACROSS", acrossIndex );
				}
			}
		}
		
		//calculate down score
		for( int downIndex = 0; downIndex < down.length; downIndex++ ) {
			if( down[downIndex] != null ) {
				userAnswer = getDownWord(downIndex);
				if( userAnswer.equalsIgnoreCase(puzzle.getDown()[downIndex].getAnswer()) ) {
					score += 200;
					highlightCorrectAnswer("DOWN", downIndex);
				}
				else {
					highlightWrongAnswer( "DOWN", downIndex );
				}
			}
		}
		
		
		
		//count the number of words entered by the user
		int numberOfWords = 0;
		for( int a = 0; a < game.getAcross().length; a++ ) {
			if( across[a] != null && !across[a].equals("") ) {
				numberOfWords++;
			}	
		}
		
		for( int d = 0; d < game.getDown().length; d++ ) {
			if( down[d] != null && !down[d].equals("") ) {
				numberOfWords++;
			}	
		}
		
		
		
		if( numberOfWords == game.getPuzzle().getNumberOfQuestions() ) {
			score += clock.getBonus();
		}
		
		return score;
	}
	
	public String getAcrossWord( int startingIndex ) {
		String out = "";
		int length = across[startingIndex].length();
		for( int i = startingIndex; i < startingIndex + length; i++ ) {
			out += grid[i].getText();
		}
		
		return out;
	}
	
	public String getDownWord( int startingIndex ) {
		String out = "";
		int length = down[startingIndex].length();
		for( int i = 0; i < length; i++ ) {
			out += grid[ startingIndex + puzzle.getSize() * i ].getText();
		}
		return out;
	}
	public void highlightWrongAnswer( String direction, int startingIndex ) {
		Color wrongColor = new Color(245,118,118);
		if( direction.equals("ACROSS") ) {
    		int length = puzzle.getAcross()[startingIndex].getLength();
        	for( int i = startingIndex; i < startingIndex + length; i++ ) {
        		grid[i].setBackground(wrongColor);
        	}
    	}
    	else if( direction.equals("DOWN") ) {
    		int length = puzzle.getDown()[startingIndex].getLength();
        	for( int i = 0; i < length; i++ ) {
        		grid[ i * puzzle.getSize() + startingIndex ].setBackground(wrongColor);
        	}
    	}
	}
	public void highlightCorrectAnswer( String direction, int startingIndex ) {
		Color correctColor = new Color(8,32,209);
		if( direction.equals("ACROSS") ) {
    		int length = puzzle.getAcross()[startingIndex].getLength();
        	for( int i = startingIndex; i < startingIndex + length; i++ ) {
        		grid[i].setForeground(correctColor);
        	}
    	}
    	else if( direction.equals("DOWN") ) {
    		int length = puzzle.getDown()[startingIndex].getLength();
        	for( int i = 0; i < length; i++ ) {
        		grid[ i * puzzle.getSize() + startingIndex ].setForeground(correctColor);
        	}
    	}
	}
	public void disableCrosswordGrid() {
		//diable clicking and editing of crossword grid
		for( int i = 1; i < grid.length; i++ ) {
			grid[i].setEditable(false);
		}
		
		
		//diable clicking of questions
		across_JList.removeMouseListener(acrossListen);
		down_JList.removeMouseListener(downListen);
		
	}
	
	public void removeAcrossQuestionSelection() {
	
		if( down_JList.isSelectionEmpty() == false ) {
			down_JList.clearSelection();

		}
	}
	public void removeDownQuestionSelection() {
		if( across_JList.isSelectionEmpty() == false ) {
			across_JList.clearSelection();

		}
	}
	public void backToParent( CrosswordPanel pane) {
		crosswordGUI parent = ((crosswordGUI) pane.getParent());
		parent.hideGame();
		parent.showPuzzleChooser();
		
	}
	
	//disables the ability to edit the crossword answers
	public void disableCrosswordInteraction() {
		for( int i = 1; i < grid.length; i++ ) {
			grid[i].setEditable(false);
		}
	}
	//enables the ability to edit the crossword answers
	public void enableCrosswordInteraction() {
		for( int i = 1; i < grid.length; i++ ) {
			grid[i].setEditable(true);
		}
	}
}




