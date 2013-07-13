import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;


public class MyStatsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private JScrollPane scrollPane;
	
	public MyStatsPanel( int gamesCompleted, int gamesStarted, String percent, int totalScore, String avgTime, String fastestTime ) {
		setSize(580, 315);
		
		JSeparator separator_1 = new JSeparator();
		
		JLabel lblGamesCompleted = new JLabel( "Game Completion Ratio:");
		
		JLabel lblTotalPoints = new JLabel("Total Points:");
		
		JLabel lblAverageGameTime = new JLabel("Average Game Time:");
		
		JLabel lblBestTime = new JLabel("Best Time:");
		
		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel gamesRatio = new JLabel("" + gamesCompleted + "/" + gamesStarted + " " + percent);
		
		JLabel AvgGameLabel = new JLabel(avgTime);
		
		JLabel totalPointsLabel = new JLabel("" + totalScore);
		
		JLabel bestTimeLabel = new JLabel(fastestTime);
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
					.addGap(12))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(74)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAverageGameTime)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(AvgGameLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblGamesCompleted)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(gamesRatio)))
					.addGap(73)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTotalPoints)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(totalPointsLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblBestTime)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bestTimeLabel)))
					.addContainerGap(74, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotalPoints)
						.addComponent(totalPointsLabel)
						.addComponent(lblGamesCompleted)
						.addComponent(gamesRatio))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAverageGameTime)
						.addComponent(AvgGameLabel)
						.addComponent(lblBestTime)
						.addComponent(bestTimeLabel))
					.addGap(20)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);
	}

	
	public void addToScroll( JPanel game ) {
		scrollPane.setViewportView(game);
	}
}
