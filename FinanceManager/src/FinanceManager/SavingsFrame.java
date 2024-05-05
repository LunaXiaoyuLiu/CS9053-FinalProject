package FinanceManager;

import javax.swing.JFrame;

public class SavingsFrame extends JFrame {
	
	String username;
	
	public SavingsFrame(String username) {
		
		super("Finance Manager -- Savings");
		
		this.username = username;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 300);
		
		this.setVisible(true);
		
	}

}
