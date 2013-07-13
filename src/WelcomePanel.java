import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class WelcomePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel welcomeLabel;
	private JButton singlePlayerButton;
	private JButton twoPlayerButton;

	public void init() {
		this.setLayout(null);
		//welcome label
		welcomeLabel = new JLabel();
		welcomeLabel.setText("Crossword Puzzle ");
		welcomeLabel.setFont(new Font("Impact", Font.PLAIN, 48));
		welcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		welcomeLabel.setBounds(195, 95, 420, 50);
		add(welcomeLabel);




		//single Player button
		OnePlayerListener onePlayerListener = new OnePlayerListener( this );
		singlePlayerButton = new JButton();
		singlePlayerButton.setText("One Player");
		singlePlayerButton.setBounds(280, 225, 165, 50);
		singlePlayerButton.addActionListener( onePlayerListener );
		add(singlePlayerButton);

		//two Player Button 
		TwoPlayerListener twoPlayerListener = new TwoPlayerListener( this );
		twoPlayerButton = new JButton();
		twoPlayerButton.setText("Two Player");
		twoPlayerButton.setBounds(280, 280, 165, 50);
		twoPlayerButton.addActionListener( twoPlayerListener );
		add(twoPlayerButton);

		this.setSize(750,600);
	}


	class OnePlayerListener implements ActionListener {
		WelcomePanel pane;
		public OnePlayerListener( WelcomePanel panel ) {

			this.pane = panel;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			crosswordGUI parent = ((crosswordGUI) pane.getParent()); 

			//single player game

			parent.hideWelcomePanel();
			parent.loginPanel = new LoginPanel();
			parent.loginPanel.init(true);
			parent.loginPanel.setVisible(true);
			pane.getParent().add(parent.loginPanel);
		}


	}
	class TwoPlayerListener implements ActionListener {
		WelcomePanel pane;
		public TwoPlayerListener( WelcomePanel panel ) {
			this.pane = panel;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			crosswordGUI parent = ((crosswordGUI) pane.getParent()); 
			parent.hideWelcomePanel();
			parent.loginPanel = new LoginPanel();
			parent.loginPanel.init(false);
			parent.loginPanel.setVisible(true);
			pane.getParent().add(parent.loginPanel);
		}
		

	}
}
