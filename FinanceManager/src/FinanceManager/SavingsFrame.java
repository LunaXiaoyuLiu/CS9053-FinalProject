package FinanceManager;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SavingsFrame extends JFrame {
	
	String username;
	JPanel rootPanel;
	JPanel inputPanel;
	JPanel buttonPanel;
	JPanel displayPanel;
	JTextField inputAmount;
	JLabel errorMessage = new JLabel();
	String yield;
	
	public SavingsFrame(String username) {
		
		super("Finance Manager -- Savings");
		
		this.username = username;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 300);
		
		this.setVisible(true);
		
	}

}
