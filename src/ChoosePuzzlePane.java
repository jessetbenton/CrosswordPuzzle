import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.DecimalFormat;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;



public class ChoosePuzzlePane extends JPanel {

	private JLabel panelLabel;
	private JLabel categoryLabel;
	private JLabel difficultyLabel;
	private JComboBox categoryComboBox;
	private JComboBox difficultyComboBox;
	private JTabbedPane tabbedPane;
	private JPanel gameNewList;
	private JScrollPane gameNewScroll;
	private JPanel gameSavedList;
	private MyStatsPanel myStatsPanel;
	private JPanel completedGameList;
	private JScrollPane savedScroll;
	private File[] files;
	private JButton exitButton;
	private JButton playButton;
	private PuzzleIndividualPanel selectedPuzzle;
	private Player player1;
	private Player player2;
	private Database database;
	private JScrollPane globalStat;
	private JLabel global_nameLabel = new JLabel();
	private JLabel global_soreLabel = new JLabel();
	private JLabel global_nGameStartedLabel = new JLabel();
	private JLabel global_nGameCompletedLabel = new JLabel();
	private JLabel global_bestTimeLabel = new JLabel();
	private JPanel global_pane = new JPanel();
	private JButton deleteButton;
	private JLabel errorDeleteMessage;
	private JLabel errorPlayMessage;
	private JPanel globalTable;
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void init() {
		
		this.setLayout(null);
		this.setSize(800,600);
			
		//Panel Label
		panelLabel = new JLabel();
		panelLabel.setText("Choose Your Puzzle");
		panelLabel.setFont(new Font("Impact", Font.PLAIN, 24));
		panelLabel.setBounds(new Rectangle(new Point(277, 30), panelLabel.getPreferredSize()));
		add(panelLabel);
		
		//play message error
		errorPlayMessage = new JLabel();
		errorPlayMessage.setText("You need to select a puzzle to play");
		errorPlayMessage.setForeground(Color.red);
		errorPlayMessage.setBounds(275, 425, 350, 25);
		errorPlayMessage.setVisible(false);
		add(errorPlayMessage);	
		
		//delete message error
		errorDeleteMessage = new JLabel();
		errorDeleteMessage.setText("You can't delete a puzzle, only saved games");
		errorDeleteMessage.setForeground(Color.red);
		errorDeleteMessage.setBounds(250,425,350,25);
		errorDeleteMessage.setVisible(false);
		add(errorDeleteMessage);	
		
		//Category Label
		categoryLabel = new JLabel();
		categoryLabel.setText("Category");
		categoryLabel.setBounds(new Rectangle(new Point(415, 80), categoryLabel.getPreferredSize()));
		categoryLabel.setVisible(false);
		add(categoryLabel);
		
		//difficulty Label
		difficultyLabel = new JLabel();
		difficultyLabel.setText("Difficulty");
		difficultyLabel.setBounds(new Rectangle(new Point(150, 80), difficultyLabel.getPreferredSize()));
		difficultyLabel.setVisible(false);
		add(difficultyLabel);
		
		//category combo box
		categoryComboBox = new JComboBox();
		categoryComboBox.setBounds(475, 77, 120, 25);
		categoryComboBox.setBackground(Color.white);
		categoryComboBox.setVisible(false);
		add(categoryComboBox);
		
		//difficulty combo box
		difficultyComboBox = new JComboBox();
		difficultyComboBox.setBounds(211, 77, 120, 25);
		difficultyComboBox.setBackground(Color.white);
		difficultyComboBox.setVisible(false);
		add(difficultyComboBox);	
		
		//delete game button
		deleteButton = new JButton();
		deleteButton.setText("Delete");
		deleteButton.setBounds(320, 460, 115, 30);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				deletePuzzle();
			}
			
		});
		add(deleteButton);
		
		//exit button
		exitButton = new JButton();
		exitButton.setText("Exit");
		exitButton.setBounds(deleteButton.getX() - 150, deleteButton.getY(), 115, 30);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				//save any changes made to game history
				database.writeToFile();
				System.exit(0);
				}
		});
		add(exitButton);
		
		
		
		//play button
		playButton = new JButton();
		playButton.setText("Play");
		playButton.setBounds(deleteButton.getX() + 150, deleteButton.getY(), 115, 30);
		playButton.addActionListener( new PlayButtonListener(this) );
		add(playButton);

		
		//Define Dimensions of pane
		Dimension preferredSize = new Dimension();
		for(int i = 0; i < this.getComponentCount(); i++) {
			Rectangle bounds = this.getComponent(i).getBounds();
			preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
			preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
		}
		Insets insets = this.getInsets();
		preferredSize.width += insets.right;
		preferredSize.height += insets.bottom;
		this.setMinimumSize(preferredSize);
		this.setPreferredSize(preferredSize);	
		
		//----Saved Game Tab----
		//read from DB the saved games.
		//add to new PuzzleIndividualPanel for saved games
		
		//tabbed pane
		tabbedPane = new JTabbedPane();
		
		game_New_Tab();

		game_Saved_Tab();
		
		globalScoresTable();
		
		
		tabbedPane.addTab("   New Game   ", gameNewScroll );
		tabbedPane.addTab("   Saved Games   ", savedScroll );
		tabbedPane.addTab("   My Stats   ", myStatsPanel );
		tabbedPane.addTab("   Global Stats   ", globalTable );
		add(tabbedPane);
		tabbedPane.setBounds(90, 115, 580, 315);

		this.setVisible(true);
	}
	
	
	
	//Mouse listener that visualizes the selection of puzzles
	class SelectedPuzzleMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			errorDeleteMessage.setVisible(false);
			errorPlayMessage.setVisible(false);
			//return previously selected puzzle to white
			if( selectedPuzzle != null ) {
				selectedPuzzle.setBackground(Color.white);
			}
			selectedPuzzle = (PuzzleIndividualPanel) e.getSource();
			
			selectedPuzzle.setBackground(Color.lightGray);	
		}

		public void mouseEntered(MouseEvent e) {	
		}
		public void mouseExited(MouseEvent e) {	
		}
		public void mousePressed(MouseEvent e) {	
		}
		public void mouseReleased(MouseEvent e) {
		}
	}
	
	class PlayButtonListener implements ActionListener {

		ChoosePuzzlePane pane;	
		public PlayButtonListener( ChoosePuzzlePane pane ) {
			this.pane = pane;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {			
			
			if( selectedPuzzle != null ) {
				
				CrosswordPanel game = new CrosswordPanel();

				//new games have -1
				if( selectedPuzzle.getPlayerPuzzleNumber() == -1 ) {
					Game gameToAdd = selectedPuzzle.getGame();
					player1.addGame( gameToAdd );
					if( player2 != null ) {
						gameToAdd.setUserName(player1.getUserName());
						gameToAdd.setPlayer2(player2);
						player2.addGame( gameToAdd );
					}
					game.init( gameToAdd );


				}
				else {
					int puzzleNumber = selectedPuzzle.getPlayerPuzzleNumber();
					
					if( player2 != null ) {
						selectedPuzzle.getGame().setUserName(player1.getUserName());
						player2.addGame(selectedPuzzle.getGame());
						player1.getSavedGame(puzzleNumber).setPlayer2(player2);
					}
					
					game.init( player1.getSavedGame(puzzleNumber) );
				}

				((crosswordGUI) pane.getParent()).hidePuzzleChooser();
				((crosswordGUI) pane.getParent()).puzzle = game;
				((crosswordGUI) pane.getParent()).add(game);
			}
			else {
				errorPlayMessage.setVisible(true);
			}
		} 

	}
	
	
	
	//everything to do with the saved game tab
	public void game_Saved_Tab() {

		gameSavedList = new JPanel();
		gameSavedList.setSize(580, 315);
		GridBagLayout gridBag = new GridBagLayout();
		gameSavedList.setLayout(gridBag);
		gameSavedList.setBackground(Color.white);

		savedScroll = new JScrollPane();
		
		completedGameList = new JPanel();
		BoxLayout boxLayout = new BoxLayout(completedGameList, BoxLayout.Y_AXIS);
		completedGameList.setLayout( boxLayout );
		completedGameList.setSize(535, 150);
		completedGameList.setBackground(Color.white);
		
		
		
		
		
		//add saved games to tab
		updateGames();

		
	}
	public void updateGames() {

		
		//make sure we are starting with an empty panel
		gameSavedList.removeAll();
		
		
		
		PuzzleIndividualPanel tempPanels[] = new PuzzleIndividualPanel[ files.length * files.length + 1 ];
		CompletedGameStats completedGamesPanels[] = new CompletedGameStats[ files.length * files.length + 1];
		GridBagConstraints c = new GridBagConstraints();

		int savedCount = 0;
		int totalScore = 0;
		int gamesCompleted = 0;
		int totalMin = 0;
		int totalSec = 0;
		int gamesStarted = 0;
		
		for( Game g : player1.getGameHistory() ) {

			if( g.isCompleted() == false ) {
				//constraints
				c.fill = GridBagConstraints.BOTH;
				c.gridx = savedCount % 4;
				c.gridy = savedCount / 4;
				c.weightx = 1.0;
				c.weighty = 1.0;

				//create clickable object and add to panel array
				tempPanels[savedCount] = new PuzzleIndividualPanel(g, savedCount);
				tempPanels[savedCount].addMouseListener(new SelectedPuzzleMouseListener());
				gameSavedList.add(tempPanels[savedCount], c);
				savedCount++;
				gamesStarted++;
			}
			//tabulate the completed games scores
			else {
				gamesCompleted++;
				totalScore += g.getScore();
				completedGamesPanels[gamesCompleted] = new CompletedGameStats(g);
				totalMin += g.getClock().getMinute();
				totalSec += g.getClock().getSecond();
				
				gamesStarted++;
			}
		}

		//set up player for global stats comparison
		player1.setGamesCompleted(gamesCompleted);
		player1.setGamesStarted(gamesStarted);
		player1.setTotalScore(totalScore);
		player1.setBestTime();
		
		
		int totalTime = 60 * totalMin + totalSec;
		int averageTime = 0;
		double percent = 0;
		if( gamesStarted != 0 && gamesCompleted != 0 ) {
			averageTime = (totalTime/60)/gamesCompleted;
			percent = ((double)gamesCompleted)/gamesStarted;

		}
		String avgTime = (9-averageTime) + "m " + (60-(totalTime%60)) + "s";
		DecimalFormat df = new DecimalFormat("#.##");
		String percentFormat = "( " + df.format(percent*100) + "% )";
		myStatsPanel = new MyStatsPanel(gamesCompleted, gamesStarted, percentFormat, totalScore, avgTime, player1.findFastestGame());	

		
		//add completed games to the player mystats scroll panel
		if( gamesCompleted > 0 ) {
			for( int i = 1; i <= gamesCompleted; i++ ) {
				completedGameList.add(completedGamesPanels[i]);

			}
		}
	
		myStatsPanel.addToScroll(completedGameList);
		myStatsPanel.setPreferredSize(tabbedPane.getPreferredSize());
	
		savedScroll.setPreferredSize(tabbedPane.getPreferredSize());
		savedScroll.setAutoscrolls(true);
		savedScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		savedScroll.setViewportView(gameSavedList);
		
	}
	
	//everything to do with the new game tab
	public void game_New_Tab() {
		files = Parser.puzzleFinder();
		
		gameNewList = new JPanel();
		gameNewList.setSize(580, 315);
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		gameNewList.setLayout(gridBag);
		gameNewList.setBackground(Color.white);	

		PuzzleIndividualPanel tempPanels[] = new PuzzleIndividualPanel[ files.length * files.length + 1 ];
		for( int i = 0; i < files.length; i++ ) {
			Puzzle temp = null;
			try {
				temp = Parser.parsePuzzle(files[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tempPanels[i] = new PuzzleIndividualPanel(temp);
			
			//constraints
			c.fill = GridBagConstraints.BOTH;
			c.gridx = i % 4;
			c.gridy = i / 4;
			c.weightx = 1.0;
			c.weighty = 1.0;

			//Add Mouse Listener
			tempPanels[i].addMouseListener(new SelectedPuzzleMouseListener());

			//Iterate through categoryCombobox
			int index = 0;
			boolean containsElement = false;
			while( !containsElement && index < categoryComboBox.getItemCount() ) {
				if( categoryComboBox.getItemAt(index) != null 
						&& categoryComboBox.getItemAt(index).equals(temp.getGameCategory()) ) {
					containsElement = true;
				}
				index++;
			}
			//Update the category combobox only with new elements
			if( containsElement == false ) {
				categoryComboBox.addItem( temp.getGameCategory() );
			}

			
			gameNewList.add(tempPanels[i], c);
			
		}
		
		
		gameNewScroll = new JScrollPane();
		gameNewScroll.setPreferredSize(tabbedPane.getPreferredSize());
		gameNewScroll.setAutoscrolls(true);
		gameNewScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		gameNewScroll.setViewportView(gameNewList);
	}
	
	
	public void game_Global_Tab() {
		GlobalHighScores highScores = ((crosswordGUI)this.getParent()).database.getGlobalHighScores();
		ScoreStructure[] topScores = highScores.getTopScores();
		
		String out[] = new String[topScores.length]; 
		String format = "|%1$10s|%2$10d|%3$20d|%4$20d|%5$20s|";
		for( int i = 0; i < topScores.length; i++ ) {
			//"%1$-11s%2$-13d%3$-23d%4$-31d%5$-30s"
			out[i] = String.format(format, topScores[i].getUsername(), topScores[i].getScore(),
				topScores[i].getGamesStarted(), topScores[i].getGamesCompleted(), topScores[i].getBestTime());
			
			
		}



		
		globalStat = new JScrollPane();
		globalStat.setSize(580, 315);
		
		globalStat.setLayout(null);
		globalStat.setBackground(Color.white);
		

		//======== global_scrollpane ========


		globalStat.add(global_pane);
		global_pane.setBounds(0, 20, 575, 265);

		
		global_nameLabel.setText("Name");
		global_nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		globalStat.add(global_nameLabel);
		global_nameLabel.setBounds(30, 1, 27, 14);

		

		//---- global_soreLabel ----
		global_soreLabel.setText("Score");
		global_soreLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		globalStat.add(global_soreLabel);
		global_soreLabel.setBounds(99, 1, 27, 14);
		

		//---- global_nGameStartedLabel ----
		global_nGameStartedLabel.setText("Number of game Started");
		global_nGameStartedLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		globalStat.add(global_nGameStartedLabel);
		global_nGameStartedLabel.setBounds(163, 0, 118, 14);

		//---- global_nGameCompletedLabel ----
		global_nGameCompletedLabel.setText("Number of game Completed");
		global_nGameCompletedLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		globalStat.add(global_nGameCompletedLabel);
		global_nGameCompletedLabel.setBounds(299, 0, 133, 14);

		//---- global_bestTimeLabel ----
		global_bestTimeLabel.setText("Best Time");
		global_bestTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		globalStat.add(global_bestTimeLabel);
		global_bestTimeLabel.setBounds(485, 0, 46, 14);
		
	

	}
	
	
	public void globalScoresTable() {
		database.findTopPlayers();
		globalTable = new JPanel();
		GlobalHighScores highScores = database.getGlobalHighScores();
		ScoreStructure[] topScores = highScores.getTopScores();
		globalTable = new HighScoresTable(topScores);
		
	}
	
	public void setLoggedInUser( Player player ) {
		this.player1 = player;
	}
	public void addPlayerTwo( Player player2 ) {
		this.player2 = player2;
	}
	public void setDB( Database db ) {
		this.database = db;
	}
	public void deletePuzzle() {
		
		if( selectedPuzzle != null && !selectedPuzzle.isNewGame) {
			removeCompletedPuzzle();
			player1.getGameHistory().remove(selectedPuzzle.getPlayerPuzzleNumber());
			

			updateGames();
			selectedPuzzle = null;
			errorDeleteMessage.setVisible(false);
		}
		else {
			errorDeleteMessage.setVisible(true);
		}
	}
	public void clearTabs() {
		myStatsPanel.removeAll();
		gameSavedList.removeAll();
	}
	
	public void removeCompletedPuzzle() {
		if( selectedPuzzle.getPlayerPuzzleNumber() != -1 ) {
			gameSavedList.remove(selectedPuzzle.getPlayerPuzzleNumber());
		}
	}
	public Player getPlayer1() {
		return player1;
	}
	
}
