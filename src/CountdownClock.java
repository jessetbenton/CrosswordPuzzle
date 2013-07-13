import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

class CountdownClock extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	javax.swing.Timer m_t;
	private double bonus;
	private int timerMinute = 9;
	private int timerSecond = 59;
	private JTextField clock;
	private JTextField bonusDisp;
	private boolean paused;

	public CountdownClock( int min, int second, double bonus ) {		
		
		//... Set some attributes.
		timerMinute = min;
		timerSecond = second;
		this.bonus = bonus;
		
		
		
		clock = new JTextField();
		clock.setBorder(BorderFactory.createEmptyBorder());
		clock.setBackground(new Color(238,238,238));
		clock.setFont(new Font("Impact", Font.PLAIN, 24));
		clock.setForeground(Color.BLACK);
		clock.setEditable(false);
		clock.setColumns(12);
		clock.setText(" Time Remaining " + timerMinute + ":" + timerSecond);
		
		bonusDisp = new JTextField();
		bonusDisp.setForeground(Color.BLACK);
		bonusDisp.setFont(new Font("Impact", Font.PLAIN, 24));
		bonusDisp.setEditable(false);
		bonusDisp.setColumns(12);
		bonusDisp.setBorder(BorderFactory.createEmptyBorder());
		bonusDisp.setBackground(new Color(238,238,238));
		DecimalFormat df = new DecimalFormat("#");
		bonusDisp.setText(" Bonus: " + df.format(bonus));

		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(clock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
					.addComponent(bonusDisp, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(clock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(bonusDisp, 0, 0, Short.MAX_VALUE))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

		//... Create a 1-second timer.
		m_t = new javax.swing.Timer(1000, new ClockTickAction());
	}

	/////////////////////////////////////////// inner class listener
	private class ClockTickAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			//only count down until you hit 0:00
			if( timerMinute > -1 && timerSecond > 0) {
				timerSecond--;
				bonus = bonus - ( 1000. / 600 );
			}
			
			if( timerSecond == 00 && timerMinute > 0 )
			{
				timerMinute--;
				
				timerSecond = 59;

			}
			if( timerMinute == 0 && timerSecond == 00 ) {
				bonus = 0;
				changeTextColor();
			}
			clock.setText(" Time Remaining " + timerMinute + ":" + timerSecond);
			DecimalFormat df = new DecimalFormat("#");
			bonusDisp.setText(" Bonus: " + df.format(bonus) );


		}
	}
	public void changeTextColor() {
		clock.setForeground(Color.red);
		bonusDisp.setForeground(Color.red);
	}
	public String getTime() {
		return "" + timerMinute + ":" + timerSecond;
	}
	public int getMinute() {
		return timerMinute;
	}
	public int getSecond() {
		return timerSecond;
	}
	public void stop() {
		paused = true;
		m_t.stop();
	}
	public void resume() {
		paused = false;
		m_t.start();
	}
	//returns only the CLOCK as a string
	public String toString() {
		DecimalFormat df = new DecimalFormat("#");
		return "" + timerMinute + " " + timerSecond + " " + df.format(bonus);
	}
	public void setBonus( String bonus ) {
		this.bonus = Double.parseDouble(bonus);
	}
	public int getBonus() {
		return Integer.parseInt(bonusDisp.getText().substring(8));
	}
	public void setMinute( String minute ) {
		timerMinute = Integer.parseInt(minute);
	}
	public void setSecond( String seconds ) {
		timerSecond = Integer.parseInt(seconds);
	}
	public boolean isPaused() {
		return paused;
	}
}
