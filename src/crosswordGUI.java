import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.swing.*;

/**
 * @author max Far
 */
@SuppressWarnings("serial")
public class crosswordGUI extends JPanel {

	public CrosswordPanel puzzle;
	public WelcomePanel welcomePane;
	public LoginPanel loginPanel;
	public ChoosePuzzlePane choosePuzzles;
	public PuzzleIndividualPanel[] selectablePuzzles;
	public PuzzleIndividualPanel selectedPuzzle;
	public CreateNewAccount createNewAccount;
	public Database database;
	public Player player1;
	public Player player2;


	public crosswordGUI() {
		setLayout(null);
		firstView();
	}

	public void firstView(){
		
		//read Database from file into Object
		try {
			database = Parser.parseDB();

		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "Problem parsing Database.txt", "Parsing Error", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		
		welcomePane = new WelcomePanel();
		welcomePane.init();
		welcomePane.setVisible(true);
		add(welcomePane);
		this.setSize(750,600);
	}
	
	public void hidePuzzleChooser() {
		choosePuzzles.setVisible(false);
	}
	public void showPuzzleChooser() {
		choosePuzzles.updateGames();
		choosePuzzles.setVisible(true);
	}
	public void hideGame() {
		puzzle.setVisible(false);
	}
	
	public void hideWelcomePanel() {
		welcomePane.setVisible(false);
	}
	public void hideLoginPanel() {
		loginPanel.setVisible(false);
	}
	public void hideCreateUserPanel() {
		createNewAccount.setVisible(false);
	}
	public Database getDB() {
		return database;
	}
	public void refreshDB() {
		try {
			database = Parser.parseDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	//Finds all Puzzle files with the ".p", in the directory of the program.
	public static File[] puzzleFinder(){

		File dir = new File("./");

		return dir.listFiles(new FilenameFilter() { 
			public boolean accept(File dir, String filename)
			{ return filename.endsWith(".p"); }
		} );

	}
	
	public ChoosePuzzlePane getPuzzleChooser() {
		return choosePuzzles;
	}

}
