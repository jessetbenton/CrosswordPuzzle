import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int TEXTFIELD_WIDTH = 215;
	private static int TEXTFIELD_HEIGHT = 25;
	private static Rectangle FIRSTPLAYER_ALONE = new Rectangle(260,220,TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
	private static Rectangle FIRSTPASS_ALONE = new Rectangle(260,250, TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
	private static Rectangle FIRSTPLAYER_COOP = new Rectangle(260,157, TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
	private static Rectangle FIRSTPASS_COOP = new Rectangle(260,192, TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
	private static Rectangle SECONDPLAYER = new Rectangle(260,241, TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
	private static Rectangle SECONDPLAYER_PASS = new Rectangle(260,276, TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
	
	
	private JLabel instructionLabel_Upper;
	private JLabel instructionLabel_Lower;
	private JLabel incorrectLogin1;
	private JLabel incorrectLogin2;
	private JTextField username1;
	private JTextField username2;
	private JPasswordField password1;
	private JPasswordField password2;
	private JLabel pass1Label;
	private JLabel pass2Label;
	private JLabel username1Label;
	private JLabel username2Label;
	private JButton login;
	private JButton back;
	private JButton createAccount;
	private boolean onePlayerMode;
	
	
	public void init( boolean onePlayerMode ) {		
		this.onePlayerMode = onePlayerMode;
		this.setLayout(null);
		this.setSize(800, 600);
		
		//instructionLabel Upper
		instructionLabel_Upper = new JLabel();
		instructionLabel_Upper.setText("Please enter your username and password to play!");
		instructionLabel_Upper.setFont(new Font("Impact", Font.PLAIN, 24));
		instructionLabel_Upper.setBounds(new Rectangle(new Point(120, 105), instructionLabel_Upper.getPreferredSize()));
		add(instructionLabel_Upper);
		
		
		//instructionLabel Lower
		instructionLabel_Lower = new JLabel();
		instructionLabel_Lower.setText("Dont have an account? Create one to Play!");
		instructionLabel_Lower.setFont(new Font("Impact", Font.PLAIN, 24));
		instructionLabel_Lower.setBounds(new Rectangle(new Point(160, 365), instructionLabel_Lower.getPreferredSize()));
		add(instructionLabel_Lower);

		
		//---One player---\\
		//username
		username1 = new JTextField();
		username1.setBounds(FIRSTPLAYER_ALONE);
		add(username1);
		
		//password
		password1 = new JPasswordField();
		password1.setBounds(FIRSTPASS_ALONE);
		add(password1);
	
		//Incorrect login label
		incorrectLogin1 = new JLabel();
		incorrectLogin1.setText("Incorrect User Login");
		incorrectLogin1.setForeground(Color.RED);
		incorrectLogin1.setBounds(300, 190, 150, 25);
		incorrectLogin1.setVisible(false);
		add(incorrectLogin1);
		
		//username Label
		username1Label = new JLabel();
		username1Label.setText("User Name");
		username1Label.setBounds(180, 220, 90, 25);
		add(username1Label);
		
		//password Label
		pass1Label = new JLabel();
		pass1Label.setText("Password");
		pass1Label.setBounds(180, 252, 90, 20);
		add(pass1Label);
		
		//login and play button
		login = new JButton();
		login.setText("Login and Play");
		login.setBounds(300, 330, 135, 25);
		login.addActionListener( new LoginListener(this) );
		add(login);
		
		//back button
		back = new JButton();
		back.setText("Back");
		back.setBounds(200, 425, 135, 25);
		back.addActionListener( new BackListener(this) );
		add(back);
		
		//create new username button
		createAccount = new JButton();
		createAccount.setText("Create New Account");
		createAccount.setBounds(385, 425,170,25);
		createAccount.addActionListener(new CreateNewAccountListener(this));
		add(createAccount);
		
		//---Two Players---\\
		if( !onePlayerMode ) {
			//player 1
			username1.setBounds(FIRSTPLAYER_COOP);
			password1.setBounds(FIRSTPASS_COOP);
			username1Label.setText("Player 1: (Across)");
			username1Label.setBounds(143,157,140,20);
			pass1Label.setBounds(180,193,80,20);
			incorrectLogin1.setBounds(500, 175, 150, 25);
			incorrectLogin1.setVisible(false);
			
			//player2
			//username
			username2 = new JTextField();
			username2.setBounds(SECONDPLAYER);
			add(username2);
			
			//password
			password2 = new JPasswordField();
			password2.setBounds(SECONDPLAYER_PASS);
			add(password2);
			
			//Incorrect login label Player 2
			incorrectLogin2 = new JLabel();
			incorrectLogin2.setText("Incorrect User Login");
			incorrectLogin2.setForeground(Color.RED);
			incorrectLogin2.setBounds(500, 257, 150, 25);
			incorrectLogin2.setVisible(false);
			add(incorrectLogin2);
			
			//username label
			username2Label = new JLabel();
			username2Label.setText("Player 2: (Down)");
			username2Label.setBounds(150,253,140,20);
			add(username2Label);
			
			//password label
			pass2Label = new JLabel();
			pass2Label.setText("Password");
			pass2Label.setBounds( 180, 287, 80, 20	);
			add(pass2Label);
			
		}
		this.setVisible(true);
		
	}
	public void setCreatedUser( String username, String password ) {
		if( onePlayerMode == false && !username1.getText().equals(username) ) {
			username2.setText(username);
			password2.setText(password);
		}
		else {
			username1.setText(username);
			password1.setText(password);
		}
		
	}
	
	class CreateNewAccountListener implements ActionListener {
		LoginPanel pane;
		CrosswordGUI parent;
		public CreateNewAccountListener( LoginPanel pane ) {
			this.pane = pane;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			parent = ((CrosswordGUI) pane.getParent());
			parent.createNewAccount = new CreateNewAccount();
			parent.createNewAccount.init();
			parent.createNewAccount.setVisible(true);
			parent.hideLoginPanel();
			parent.add(parent.createNewAccount);
			
		}
		
	}
	
	class LoginListener implements ActionListener {
		LoginPanel pane;
		CrosswordGUI parent;
		public LoginListener( LoginPanel pane ) {
			this.pane = pane;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			parent = ((CrosswordGUI) pane.getParent());
			parent.choosePuzzles = new ChoosePuzzlePane();
			try {
				String pass = passToString(password1.getPassword());
				parent.player1 = parent.database.getPlayer(username1.getText(), pass);
				
				boolean loginComplete = false;
				//single player game play
				if( onePlayerMode ) {
					if( parent.player1 != null ) {

						//after successful login
						incorrectLogin1.setVisible(false);
						parent.choosePuzzles.setLoggedInUser(parent.player1);
						loginComplete = true;

					}
					else {
						//player 1 incorrect login
						incorrectLogin1.setVisible(true);
					}
				}
				else {
					pass = passToString(password1.getPassword());
					parent.player1 = parent.database.getPlayer(username1.getText(), pass);
					
					if( parent.player1 != null ) {
						incorrectLogin1.setVisible(false);
						parent.choosePuzzles.setLoggedInUser(parent.player1);
					}
					else {
						//player 1 incorrect login
						incorrectLogin1.setVisible(true);
					}
					
					pass = passToString(password2.getPassword());
					parent.player2 = parent.database.getPlayer(username2.getText(), pass);
					
					if( parent.player2 != null ) {
						incorrectLogin2.setVisible(false);
						parent.choosePuzzles.addPlayerTwo(parent.player2);
					}
					else {
						//player 2 incorrect login
						incorrectLogin2.setVisible(true);

					}
					
					if( parent.player1 != null && parent.player2 != null ) {
						loginComplete = true;
					}
					
				}
				
				//only move onto the game if all players have input correct information
				//this method accounts for both the single and multiplayer games
				if( loginComplete ) {
					parent.choosePuzzles.setDB(parent.database);
					parent.choosePuzzles.init();
					parent.choosePuzzles.setVisible(true);
					parent.hideLoginPanel();
					parent.add(parent.choosePuzzles);
				}
				
			}
				
			catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}
	
	class BackListener implements ActionListener {

		LoginPanel pane;	
		public BackListener( LoginPanel pane ) {
			this.pane = pane;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//firstScreen for back button
			((CrosswordGUI) pane.getParent()).hideLoginPanel();
			((CrosswordGUI) pane.getParent()).welcomePane.setVisible(true);
		}
		
	}
	
	public Player findPlayer(JTextField usernameField, JPasswordField passwordField) throws PasswordException {
		Database db = ((CrosswordGUI) this.getParent()).database;
		for( Player existingPlayer : db.getPlayerData() ) {
			if( existingPlayer.getUserName().equals(usernameField.getText()) ) {
				String pass = "";
				for( int i = 0; i < passwordField.getPassword().length; i++ ) {
					pass += passwordField.getPassword()[i];
				}
				
				if( existingPlayer.getPassword().equals(pass) ) {
					return existingPlayer;
					
				}
				else {
					new PasswordException();
				}
				
			}
		}
		
		return null;
	}
	
	public String passToString( char[] password ) {
		String pass = "";
		for( int i = 0; i < password.length; i++ ) {
			pass += password[i];
		}
		return pass;
	}

}
