package FinanceManager;

import javax.swing.JFrame;

public class InvestmentFrame extends JFrame {
	
	String username;
	
	public InvestmentFrame(String username) {
		
		super("Finance Manager -- Investments");
		
		this.username = username;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 300);
		
		this.setVisible(true);
		
	}

}
