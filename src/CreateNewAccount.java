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


public class CreateNewAccount extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField cUsernameTF;
	private JPasswordField cPassTF;
	private JPasswordField cPassTFConfirm;
	private JButton RegSubmitButton;
	private JButton regBackButton;
	private JLabel regInsLabel1;
	private JLabel regPassLabel;
	private JLabel regConLabel;
	private JLabel regUserLabel;
	private JLabel existingLogin;
	private JLabel badFormat; 
	private JLabel passwordMismatch;
	private JLabel passwordLengthMismatch;
	private JLabel invalidUsername;
	private JLabel invalidPassword;
	
	public void init() {
		this.setLayout(null);
		cUsernameTF = new JTextField();
		cUsernameTF.setBounds(253, 186, 215, 25);
		cPassTF = new JPasswordField();
		cPassTF.setBounds(253, 226, 215, 25);
		cPassTFConfirm = new JPasswordField();
		cPassTFConfirm.setBounds(253, 266, 215, 25);		
		
		RegSubmitButton = new JButton();
		RegSubmitButton.setText("Submit and Login");
		RegSubmitButton.setBounds(297, 340, 135, RegSubmitButton.getPreferredSize().height);

		regInsLabel1 = new JLabel();
		regInsLabel1.setText("Lets create your new account!!");
		regInsLabel1.setFont(new Font("Impact", Font.PLAIN, 24));
		regInsLabel1.setBounds(new Rectangle(new Point(190, 105), regInsLabel1.getPreferredSize()));	
		
		regBackButton = new JButton();
		regBackButton.setText("Back");
		regBackButton.setBounds(297, 371, 130, regBackButton.getPreferredSize().height);
		regBackButton.addActionListener(new BackListener(this));
		
		regUserLabel = new JLabel();
		regUserLabel.setText("User Name");
		regUserLabel.setBounds(new Rectangle(new Point(185, 191), regUserLabel.getPreferredSize()));
		
		regPassLabel = new JLabel();
		regPassLabel.setText("Password");
		regPassLabel.setBounds(new Rectangle(new Point(185, 231), regPassLabel.getPreferredSize()));		

		regConLabel = new JLabel();
		regConLabel.setText("Confirm");
		regConLabel.setBounds(new Rectangle(new Point(185, 271), regConLabel.getPreferredSize()));
		
		existingLogin = new JLabel();
		existingLogin.setText("Username Already Exists");
		existingLogin.setForeground(Color.RED);
		existingLogin.setBounds(475, 187, 190, 25);
		existingLogin.setVisible(false);
		
		badFormat = new JLabel();
		badFormat.setText("Please only use the characters A-Z a-z 0-9. No Spaces");
		badFormat.setForeground(Color.RED);
		badFormat.setBounds(165, 145, 350, 50);
		badFormat.setVisible(false);
		
		invalidUsername = new JLabel();
		invalidUsername.setText("Invalid characters in username");
		invalidUsername.setBounds(475, 187, 200, 25);
		invalidUsername.setForeground(Color.RED);
		invalidUsername.setVisible(false);
		
		invalidPassword = new JLabel();
		invalidPassword.setText("Invalid characters in password");
		invalidPassword.setBounds(475, 246, 250, 25);
		invalidPassword.setForeground(Color.RED);
		invalidPassword.setVisible(false);
				
		passwordMismatch = new JLabel();
		passwordMismatch.setText("Passwords do not match");
		passwordMismatch.setForeground(Color.RED);
		passwordMismatch.setBounds(475, 246, 200, 25);
		passwordMismatch.setVisible(false);
		
		passwordLengthMismatch = new JLabel();
		passwordLengthMismatch.setText("Passwords are not the same length");
		passwordLengthMismatch.setForeground(Color.RED);
		passwordLengthMismatch.setBounds(475, 246, 250, 25);
		passwordLengthMismatch.setVisible(false);
		
		add(invalidPassword);
		add(invalidUsername);
		add(passwordLengthMismatch);
		add(badFormat);
		add(passwordMismatch);
		add(existingLogin);
		add(cUsernameTF);
		add(cPassTF);
		add(cPassTFConfirm);
		add(RegSubmitButton);
		add(regBackButton);
		add(regInsLabel1);
		add(regPassLabel);
		add(regConLabel);
		add(regUserLabel);
		this.setSize(800,600);
			
		RegSubmitButton.addActionListener(new SubmitButtonListener(this));
			
	}
	
	class BackListener implements ActionListener {

		CreateNewAccount pane;	
		public BackListener( CreateNewAccount pane ) {
			this.pane = pane;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//firstScreen for back button
			((CrosswordGUI) pane.getParent()).hideCreateUserPanel();
			((CrosswordGUI) pane.getParent()).loginPanel.setVisible(true);
		}
		
	}
	
	class SubmitButtonListener implements ActionListener {

		CreateNewAccount currentPane;
		public SubmitButtonListener( CreateNewAccount currentPane ) {
			this.currentPane = currentPane;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			try {
				if( verifyPass() ) {
					invalidUsername.setVisible(false);
					badFormat.setVisible(false);
					passwordMismatch.setVisible(false);
					existingLogin.setVisible(false);
					badFormat.setVisible(false);
					passwordLengthMismatch.setVisible(false);
					
					
					String username = cUsernameTF.getText();
					String pass = "";
					for( int i = 0; i < cPassTF.getPassword().length; i++ ) {
						pass += cPassTF.getPassword()[i];
					}
					
					if( validCharacterCheck(username.toCharArray()) ) {
						invalidUsername.setVisible(false);
						if( validCharacterCheck(cPassTF.getPassword()) ) {
							invalidPassword.setVisible(false);
							CrosswordGUI parent = (CrosswordGUI) currentPane.getParent();
							Player createdPlayer = new Player( username, pass);
							parent.database.addUser(createdPlayer);
							parent.hideCreateUserPanel();
							parent.loginPanel.setCreatedUser(username, pass);
							parent.loginPanel.setVisible(true);
						}
						else {
							//password contains invalid characters
							//"asdf" is returning bad password?
							invalidPassword.setVisible(true);
							erasePasswords();
							cPassTF.requestFocus();
							badFormat.setVisible(true);
						}
					}
					else {
						//username contains invalid characters
						invalidUsername.setVisible(true);
						cUsernameTF.requestFocus();
						badFormat.setVisible(true);
					}
				}
				else {
					//put up label in red that says passwords do not match
					invalidUsername.setVisible(false);
					badFormat.setVisible(false);
					passwordMismatch.setVisible(true);
					existingLogin.setVisible(false);
					badFormat.setVisible(false);
					passwordLengthMismatch.setVisible(false);
					cPassTF.requestFocus();
					erasePasswords();
				}
			} catch (PasswordException e1) {
				//put up label in red that says, passwords wrong length
				passwordMismatch.setVisible(false);
				existingLogin.setVisible(false);
				invalidUsername.setVisible(false);
				badFormat.setVisible(false);
				passwordLengthMismatch.setVisible(true);
				cPassTF.requestFocus();
				erasePasswords();
				
			} catch (UserNameException e2) {
				//Username already exists
				//no username listed
				//invalid characters in username
				cUsernameTF.requestFocus();
				invalidUsername.setVisible(false);
				badFormat.setVisible(false);
				passwordMismatch.setVisible(false);
				badFormat.setVisible(false);
				passwordLengthMismatch.setVisible(false);
				System.out.println("UsernameException");
				existingLogin.setVisible(true);
				cUsernameTF.requestFocus();
			} catch (Exception e3) {
				e3.printStackTrace();
			}

		}
		
	}
	public boolean verifyPass() throws Exception{
		char[] pass1 = cPassTF.getPassword();
		char[] pass2 = cPassTFConfirm.getPassword();
		if( cUsernameTF.getText().length() == 0 ) {
			throw new UserNameException();
		}
		if( pass1.length != pass2.length ) {
			throw new PasswordException();
		}
		
		for( int i = 0; i < pass1.length; i++ ) {
			if( pass1[i] != pass2[i]) {
				return false;
			}
				
		}
		
		return true;
		
	}
	public void erasePasswords() {
		cPassTF.setText("");
		cPassTFConfirm.setText("");
	}
	
	public boolean validCharacterCheck( char[] someString ) {
		for( int character = 0; character < someString.length; character++ ) {
			//enforce naming convention. Must be 0-9 A-Z or a-z
			if( (someString[character] >= '0' && someString[character] <= '9' ) 
					|| (someString[character] >= 'A' && someString[character] <= 'Z')
					|| (someString[character] >= 'a' && someString[character] <= 'z') ) {
				//okay
			}
			else {
				return false;
			}
		}
		return true;
	}
	

}
