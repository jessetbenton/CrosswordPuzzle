import java.awt.Color;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class PuzzleIndividualPanel extends JPanel {
	
	static int WIDTH = 125;
	static int HEIGHT = 125;
	static private String ICON_MUSIC = "music.png";
	static private String ICON_ANIMALS = "animals.png";
	static private String ICON_CARS = "cars.png";
	static private String ICON_GENERAL = "crossword.png";
	static private String ICON_FOOD = "food.png";
	static private String ICON_SPORTS = "sports.png";
	static private String ICON_DEFAULT = "crossword.png";
	private JLabel title;
	private JLabel title2;
	private String iconPath;
	private Puzzle puzzle;
	private int playerPuzzleNumber;
	private Game game;
	private JLabel icon;
	public boolean isNewGame;
	
	private static final long serialVersionUID = 1L;

	
	
	//instantiator for new puzzles
	public PuzzleIndividualPanel( Puzzle puzzle ) {
		isNewGame = true;
		this.playerPuzzleNumber = -1;
		this.setSize(WIDTH, HEIGHT);
		this.setBackground(Color.white);
		this.game = new Game();
		game.setPuzzle( puzzle );
		this.puzzle = puzzle;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		chooseIcon();
		title = new JLabel(this.toString());
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(icon);
		this.add(title);
	}
	
	
	//instantiators for existing puzzles
	public PuzzleIndividualPanel( Game game, int playerPuzzleNumber ) {
		isNewGame = false;
		this.playerPuzzleNumber = playerPuzzleNumber;
		this.setSize(getPreferredSize());
		this.setBackground(Color.white);
		this.game = game;
		this.puzzle = game.getPuzzle();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		chooseIcon();
		title = new JLabel( game.formatLabel() );
		title.setAlignmentX( Component.CENTER_ALIGNMENT );
		title2 = new JLabel( game.getClock().getTime() );
		title2.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.add(icon);
		this.add(title);
		this.add(title2);
	}
	
	
	//Determines which Icon to use based on the Puzzle Object
	public void chooseIcon() { 	
		icon = new JLabel();
		if( puzzle.getGameCategory().equals("Animals") ) {
			iconPath = ICON_ANIMALS;
		}
		else if( puzzle.getGameCategory().equals("Music") ) {
			iconPath = ICON_MUSIC;
		}
		else if( puzzle.getGameCategory().equals("Cars") ) {
			iconPath = ICON_CARS;
		}
		else if( puzzle.getGameCategory().equals("General") ) {
			iconPath = ICON_GENERAL;
		}
		else if( puzzle.getGameCategory().equals("Food") ) {
			iconPath = ICON_FOOD;
		}
		else if( puzzle.getGameCategory().equals("Sports") ) {
			iconPath = ICON_SPORTS;
		}
		else {
			iconPath = ICON_DEFAULT;
		}
		
		icon.setIcon(new ImageIcon(getClass().getResource("resources/" + iconPath)));
		icon.setVisible(true);
		icon.setBounds(45, 30, 100, 100);
		icon.setAlignmentX(Component.CENTER_ALIGNMENT);
		
	}
	
	public Puzzle getPuzzle() {
		return puzzle;
	}
	public Game getGame() {
		return game;
	}
	public int getPlayerPuzzleNumber() {
		return playerPuzzleNumber;
	}
	
	public String toString() {
		String out = "";
		out += puzzle.getGameCategory() + " ";
		out += puzzle.getPuzzleNumber() + " ";
		out += puzzle.getSize() + "x" + puzzle.getSize();
		return out;
	}
}
