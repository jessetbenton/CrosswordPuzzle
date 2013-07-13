import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class HighScoresTable extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HighScoresTable( ScoreStructure[] topScores ) {
        super(new GridLayout(1,0));
        
        String[] columnNames = {"Name",
                "Score",
                "Games Started",
                "Games Completed",
                "Best Time"};
        
        Object[][] data = new Object[topScores.length][5];
        for( int i = 0; i < topScores.length; i++ ) {
        	data[i][0] = topScores[i].getUsername();
        	data[i][1] = topScores[i].getScore();	
        	data[i][2] = topScores[i].getGamesStarted();
        	data[i][3] = topScores[i].getGamesCompleted();
        	data[i][4] = topScores[i].getBestTime();

        }
        
        
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        //create Scroll pane, add table to it
        JScrollPane scrollPane = new JScrollPane(table);
        //add scrollPane to this panel
        add(scrollPane);
        
        
        
	}
	
}
