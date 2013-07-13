import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class CompletedGameStats extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	public CompletedGameStats( Game g ) {
		
		CountdownClock clock = g.getClock();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 50, 50, 54, 85, 59, 0, 62, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		//Set the Puzzle ID
		JLabel puzzleIdLabel = new JLabel(g.getPuzzle().getGameCategory() + " " + g.getPuzzle().getPuzzleNumber());
		puzzleIdLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		GridBagConstraints gbc_puzzleIdLabel = new GridBagConstraints();
		gbc_puzzleIdLabel.fill = GridBagConstraints.VERTICAL;
		gbc_puzzleIdLabel.insets = new Insets(0, 0, 5, 5);
		gbc_puzzleIdLabel.gridx = 0;
		gbc_puzzleIdLabel.gridy = 1;
		add(puzzleIdLabel, gbc_puzzleIdLabel);
		
		
		//Set the Size Label
		JLabel sizeLabel = new JLabel(g.getPuzzle().getSize() + "x" + g.getPuzzle().getSize());
		sizeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		GridBagConstraints gbc_sizeLabel = new GridBagConstraints();
		gbc_sizeLabel.anchor = GridBagConstraints.NORTH;
		gbc_sizeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sizeLabel.gridx = 1;
		gbc_sizeLabel.gridy = 1;
		add(sizeLabel, gbc_sizeLabel);
		
		//set the number of words correct
		String wordsCorrect = "" + g.countWordsCorrect();
		String totalWords = "" + g.getPuzzle().getNumberOfQuestions();
		JLabel wordCompleteRatio = new JLabel(wordsCorrect + "/" + totalWords);
		wordCompleteRatio.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		GridBagConstraints gbc_wordCompleteRatio = new GridBagConstraints();
		gbc_wordCompleteRatio.anchor = GridBagConstraints.NORTH;
		gbc_wordCompleteRatio.insets = new Insets(0, 0, 5, 5);
		gbc_wordCompleteRatio.gridx = 2;
		gbc_wordCompleteRatio.gridy = 1;
		add(wordCompleteRatio, gbc_wordCompleteRatio);
		
		//set the percent completed
		String percent = g.getPercentComplete() + "%";
		JLabel percentCompleted = new JLabel(percent);
		percentCompleted.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		GridBagConstraints gbc_percentCompleted = new GridBagConstraints();
		gbc_percentCompleted.anchor = GridBagConstraints.NORTH;
		gbc_percentCompleted.insets = new Insets(0, 0, 5, 5);
		gbc_percentCompleted.gridx = 3;
		gbc_percentCompleted.gridy = 1;
		add(percentCompleted, gbc_percentCompleted);
		
		
		//set the score label
		JLabel lblScore = new JLabel("Score:");
		GridBagConstraints gbc_lblScore = new GridBagConstraints();
		gbc_lblScore.anchor = GridBagConstraints.EAST;
		gbc_lblScore.insets = new Insets(0, 0, 5, 5);
		gbc_lblScore.gridx = 4;
		gbc_lblScore.gridy = 1;
		add(lblScore, gbc_lblScore);
		
		
		JLabel scoreLabel = new JLabel("" + g.getScore());
		scoreLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		GridBagConstraints gbc_scoreLabel = new GridBagConstraints();
		gbc_scoreLabel.insets = new Insets(0, 0, 5, 5);
		gbc_scoreLabel.gridx = 5;
		gbc_scoreLabel.gridy = 1;
		add(scoreLabel, gbc_scoreLabel);
		
		//set the time label
		JLabel lblTime = new JLabel("Time:");
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.anchor = GridBagConstraints.EAST;
		gbc_lblTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblTime.gridx = 6;
		gbc_lblTime.gridy = 1;
		add(lblTime, gbc_lblTime);
		JLabel timeLabel = new JLabel("" + (9 - clock.getMinute()) + "m " + (60 - clock.getSecond()) + "s");
		timeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		GridBagConstraints gbc_timeLabel = new GridBagConstraints();
		gbc_timeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_timeLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_timeLabel.gridx = 7;
		gbc_timeLabel.gridy = 1;
		add(timeLabel, gbc_timeLabel);

	}

}
