import javax.swing.JFrame;

public class Crossword {

	static JFrame frame;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		frame = new JFrame("CSE360 crossword Puzzle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CrosswordGUI panel = new CrosswordGUI();
		frame.getContentPane().add(panel);
		frame.setSize(750, 600);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}