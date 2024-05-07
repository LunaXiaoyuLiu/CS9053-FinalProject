package FinanceManager;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InvestmentFrame extends JFrame {
	
	String username;
	JPanel rootPanel;
	JPanel inputPanel;
	JPanel buttonPanel;
	JPanel displayPanel;
	JTextField inputName;
	JTextField inputAmount;
	JTextField inputExpectedYield;
	JTextField inputDueDate;
	JLabel errorMessage = new JLabel();
	String yield;
	String investment;
	
	public InvestmentFrame(String username) throws UnknownHostException, IOException {
		
		super("Finance Manager -- Investments");
		
		this.username = username;
		
		getValues();
		createRootPanel();
		this.add(rootPanel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 500);
		
		this.setVisible(true);
		
	}
	
	public void createRootPanel() {
		
		rootPanel = new JPanel();
		rootPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		rootPanel.add(errorMessage, constraints);
		createInputPanel();
		constraints.gridx = 1;
		constraints.gridy = 2;
		rootPanel.add(inputPanel, constraints);
		createDisplayPanel();
		constraints.gridx = 1;
		constraints.gridy = 3;
		rootPanel.add(displayPanel, constraints);
		createButtonPanel();
		constraints.gridx = 1;
		constraints.gridy = 4;
		rootPanel.add(buttonPanel, constraints);
		
	}
	
	public void createInputPanel() {
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		JLabel inputNameLabel = new JLabel("Investment Name: ");
		constraints.gridx = 1;
		constraints.gridy = 1;
		inputPanel.add(inputNameLabel, constraints);
		
		inputName = new JTextField(10);
		constraints.gridx = 1;
		constraints.gridy = 2;
		inputPanel.add(inputName, constraints);
		
		JLabel inputAmountLabel = new JLabel("Invest Amount: ");
		constraints.gridx = 2;
		constraints.gridy = 1;
		inputPanel.add(inputAmountLabel, constraints);
		
		inputAmount = new JTextField(10);
		constraints.gridx = 2;
		constraints.gridy = 2;
		inputPanel.add(inputAmount, constraints);
		
		JLabel inputExpectedYieldLabel = new JLabel("Expected Yield: ");
		constraints.gridx = 3;
		constraints.gridy = 1;
		inputPanel.add(inputExpectedYieldLabel, constraints);
		
		inputExpectedYield = new JTextField(10);
		constraints.gridx = 3;
		constraints.gridy = 2;
		inputPanel.add(inputExpectedYield, constraints);
		
		JLabel inputDueDateLabel = new JLabel("Investment Date (yyyy-MM-dd)");
		constraints.gridx = 4;
		constraints.gridy = 1;
		inputPanel.add(inputDueDateLabel, constraints);
		
		inputDueDate = new JTextField(10);
		constraints.gridx = 4;
		constraints.gridy = 2;
		inputPanel.add(inputDueDate, constraints);

		JButton confirmButton = new JButton("Confirm");
		ConfirmActionListener confirmListener = new ConfirmActionListener(this);
		confirmButton.addActionListener(confirmListener);
		constraints.gridx = 4;
		constraints.gridy = 3;
		inputPanel.add(confirmButton, constraints);
		
	}
	
	public void createDisplayPanel() {
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		JLabel investAmountLabel = new JLabel("Total Investment Amount: ");
		constraints.gridx = 1;
        constraints.gridy = 1;
        displayPanel.add(investAmountLabel, constraints);
        
        JLabel investAmount = new JLabel(investment);
		constraints.gridx = 2;
        constraints.gridy = 1;
        displayPanel.add(investAmount, constraints);
        
        JLabel yieldAmountLabel = new JLabel("Total Future Yield: ");
		constraints.gridx = 1;
        constraints.gridy = 2;
        displayPanel.add(yieldAmountLabel, constraints);
        
        JLabel yieldAmount = new JLabel(yield);
		constraints.gridx = 2;
        constraints.gridy = 2;
        displayPanel.add(yieldAmount, constraints);
		
	}
	
	public void getValues() throws UnknownHostException, IOException {
		
		int port = 9994;
		String ip = "localhost";
		
		Socket socket = new Socket(ip, port);
		OutputStream os = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(os);
		pw.println(username);
		pw.flush();
		
		InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        investment = br.readLine();
        yield = br.readLine();
        
        br.close();
		pw.close();
		socket.close();
		
	}
	
	public void createButtonPanel() {
		
		buttonPanel = new JPanel();
		
		JButton backButton = new JButton("Back to Dashboard");
		buttonPanel.add(backButton);
		
		BackActionListener registerListener = new BackActionListener(this);
		backButton.addActionListener(registerListener);
		
	}
	
	private class ConfirmActionListener implements ActionListener {
		
		JFrame currentFrame;
		
		public ConfirmActionListener(JFrame currentFrame) {
            this.currentFrame = currentFrame;
        }

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			String amount = inputAmount.getText();
			String yield = inputExpectedYield.getText();
			String dateString = inputDueDate.getText();
			String name = inputName.getText();
			
			// TODO Auto-generated method stub
			DecimalFormat df = new DecimalFormat("#.##");
			df.setParseBigDecimal(true);
			java.util.Date date = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				float floatInvest = df.parse(amount).floatValue();
				float floatYield = df.parse(yield).floatValue();
				date = dateFormat.parse(dateString);
				
				int port = 9995;
				String ip = "localhost";
				
				Socket socket;
				try {
					
					socket = new Socket(ip, port);
					OutputStream os = socket.getOutputStream();
					PrintWriter pw = new PrintWriter(os);
					pw.println(username);
					pw.println(amount);
					pw.println(yield);
					pw.println(name);
					pw.println(dateString);
					pw.flush();
					
					pw.close();
					socket.close();
					
					JFrame frame = new InvestmentFrame(username);
					currentFrame.dispose();
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				errorMessage.setText("Error: Invalid input string");
				System.out.println("Error: Invalid input string");
			}			
		}
		
	}
	
	private class BackActionListener implements ActionListener {
		
		private JFrame currentFrame;
		
		public BackActionListener(JFrame currentFrame) {
            this.currentFrame = currentFrame;
        }

		@Override
		public void actionPerformed(ActionEvent e) {

			JFrame frame = new DashboardFrame(username);
			currentFrame.dispose();
			
		}
		
	}

}
