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
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class NetIncomeFrame extends JFrame {
	
	String username;
	JPanel rootPanel;
	JPanel displayPanel;
	JPanel buttonPanel;
	JPanel inputPanel;
	JPanel totalNetIncomePanel;
	JPanel latestTransactionPanel;
	JTextField transaction;
	JLabel errorMessage;
	JTextField notes;
	JLabel daily = new JLabel();
	JLabel monthly = new JLabel();
	JLabel yearly = new JLabel();
	
	JLabel transaction1Amount = new JLabel("");
	JLabel transaction1Time = new JLabel("");
	JLabel transaction1Note = new JLabel("");
	JLabel transaction2Amount = new JLabel("");
	JLabel transaction2Time = new JLabel("");
	JLabel transaction2Note = new JLabel("");
	JLabel transaction3Amount = new JLabel("");
	JLabel transaction3Time = new JLabel("");
	JLabel transaction3Note = new JLabel("");
	JLabel transaction4Amount = new JLabel("");
	JLabel transaction4Time = new JLabel("");
	JLabel transaction4Note = new JLabel("");
	
	public NetIncomeFrame(String username) throws UnknownHostException, IOException {
		
		super("Finance Manager -- Net Income");
		
		this.username = username;
		
		getValues();
		
		createRootPanel();
		this.add(rootPanel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 400);
		
		this.setVisible(true);
		
	}
	
	public void getValues() throws UnknownHostException, IOException {
		
		int port = 9996;
		String ip = "localhost";
		
		Socket socket = new Socket(ip, port);
		OutputStream os = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(os);
		pw.println(username);
		pw.flush();
		
		InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String dailyNetIncome = br.readLine();
        String monthlyNetIncome = br.readLine();
        String yearlyNetIncome = br.readLine();
        String transaction1 = br.readLine();
        String note1 = br.readLine();
        String time1 = br.readLine();
        String transaction2 = br.readLine();
        String note2 = br.readLine();
        String time2 = br.readLine();
        String transaction3 = br.readLine();
        String note3 = br.readLine();
        String time3 = br.readLine();
        String transaction4 = br.readLine();
        String note4 = br.readLine();
        String time4 = br.readLine();
        
        daily.setText(dailyNetIncome);
        monthly.setText(monthlyNetIncome);
        yearly.setText(monthlyNetIncome);
        
        transaction1Amount.setText(transaction1);
    	transaction1Time.setText(time1);
    	transaction1Note.setText(note1);
    	
    	transaction2Amount.setText(transaction2);
    	transaction2Time.setText(time2);
    	transaction2Note.setText(note2);
    	
    	transaction3Amount.setText(transaction3);
    	transaction3Time.setText(time3);
    	transaction3Note.setText(note3);
    	
    	transaction4Amount.setText(transaction4);
    	transaction4Time.setText(time4);
    	transaction4Note.setText(note4);
		
        br.close();
		pw.close();
		socket.close();
		
	}
	
	public void createRootPanel() {
		
		rootPanel = new JPanel();
		rootPanel.setLayout(new BorderLayout());
		
		createDisplayPanel();
		createButtonPanel();
		
		rootPanel.add(displayPanel, BorderLayout.NORTH);
		rootPanel.add(buttonPanel, BorderLayout.SOUTH);
		
	}
	
	public void createDisplayPanel() {
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		createTotalNetIncomePanel();
		constraints.gridx = 1;
		constraints.gridy = 1;
		displayPanel.add(totalNetIncomePanel, constraints);
		
		createInputPanel();
		constraints.gridx = 1;
        constraints.gridy = 2;
		displayPanel.add(inputPanel, constraints);
		
		createLatestTransactionPanel();
		constraints.gridx = 1;
        constraints.gridy = 3;
		displayPanel.add(latestTransactionPanel, constraints);
		
	}
	
	public void createInputPanel() {
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		JLabel recordPrompt = new JLabel("Record a transaction: ");
		constraints.gridx = 1;
        constraints.gridy = 1;
		inputPanel.add(recordPrompt, constraints);
		
		transaction = new JTextField(10);
		constraints.gridx = 2;
        constraints.gridy = 1;
        inputPanel.add(transaction, constraints);
        
        errorMessage = new JLabel();
        constraints.gridx = 3;
        constraints.gridy = 1;
		inputPanel.add(errorMessage, constraints);
		
		JLabel notesPrompt = new JLabel("Add notes: ");
		constraints.gridx = 1;
        constraints.gridy = 2;
		inputPanel.add(notesPrompt, constraints);
		
		notes = new JTextField(10);
		constraints.gridx = 2;
        constraints.gridy = 2;
        inputPanel.add(notes, constraints);
        
        JButton confirmButton = new JButton("confirm");
        ConfirmActionListener confirmListener = new ConfirmActionListener(this);
		confirmButton.addActionListener(confirmListener);
		constraints.gridx = 3;
        constraints.gridy = 2;
        inputPanel.add(confirmButton, constraints);
		
	}
	
	public void createTotalNetIncomePanel() {
		
		totalNetIncomePanel = new JPanel();
		totalNetIncomePanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		JLabel dailyLabel = new JLabel("Daily");
		constraints.gridx = 1;
        constraints.gridy = 1;
        totalNetIncomePanel.add(dailyLabel, constraints);
        
		constraints.gridx = 1;
        constraints.gridy = 2;
        totalNetIncomePanel.add(daily, constraints);
		
		JLabel monthlyLabel = new JLabel("Monthly");
		constraints.gridx = 2;
        constraints.gridy = 1;
        totalNetIncomePanel.add(monthlyLabel, constraints);
        
        constraints.gridx = 2;
        constraints.gridy = 2;
        totalNetIncomePanel.add(monthly, constraints);
		
		JLabel yearlyLabel = new JLabel("Yearly");
		constraints.gridx = 3;
        constraints.gridy = 1;
        totalNetIncomePanel.add(yearlyLabel, constraints);
        
        constraints.gridx = 3;
        constraints.gridy = 2;
        totalNetIncomePanel.add(yearly, constraints);
		
	}
	
	public void createLatestTransactionPanel() {
		
		latestTransactionPanel = new JPanel();
		latestTransactionPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		JLabel latestTransactionLabel = new JLabel("Latest Transactions: ");
		constraints.gridx = 1;
        constraints.gridy = 1;
        latestTransactionPanel.add(latestTransactionLabel, constraints);
        
        JLabel transactionAmount = new JLabel("Transaction Amount ");
		constraints.gridx = 1;
        constraints.gridy = 2;
        latestTransactionPanel.add(transactionAmount, constraints);
        
		constraints.gridx = 1;
        constraints.gridy = 3;
        latestTransactionPanel.add(transaction1Amount, constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 4;
        latestTransactionPanel.add(transaction2Amount, constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 5;
        latestTransactionPanel.add(transaction3Amount, constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 6;
        latestTransactionPanel.add(transaction4Amount, constraints);
        
        JLabel transactionNotes = new JLabel("Notes");
		constraints.gridx = 2;
        constraints.gridy = 2;
        latestTransactionPanel.add(transactionNotes, constraints);
        
		constraints.gridx = 2;
        constraints.gridy = 3;
        latestTransactionPanel.add(transaction1Note, constraints);
        
        constraints.gridx = 2;
        constraints.gridy = 4;
        latestTransactionPanel.add(transaction2Note, constraints);
        
        constraints.gridx = 2;
        constraints.gridy = 5;
        latestTransactionPanel.add(transaction3Note, constraints);
        
        constraints.gridx = 2;
        constraints.gridy = 6;
        latestTransactionPanel.add(transaction4Note, constraints);
        
        JLabel transactionTime = new JLabel("Time");
		constraints.gridx = 3;
        constraints.gridy = 2;
        latestTransactionPanel.add(transactionTime, constraints);
        
		constraints.gridx = 3;
        constraints.gridy = 3;
        latestTransactionPanel.add(transaction1Time, constraints);
        
        constraints.gridx = 3;
        constraints.gridy = 4;
        latestTransactionPanel.add(transaction2Time, constraints);
        
        constraints.gridx = 3;
        constraints.gridy = 5;
        latestTransactionPanel.add(transaction3Time, constraints);
        
        constraints.gridx = 3;
        constraints.gridy = 6;
        latestTransactionPanel.add(transaction4Time, constraints);
		
	}
	
	public void createButtonPanel() {
		
		buttonPanel = new JPanel();
		
		JButton backButton = new JButton("Back to Dashboard");
		buttonPanel.add(backButton);
		
		BackActionListener registerListener = new BackActionListener(this);
		backButton.addActionListener(registerListener);
		
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
	
	private class ConfirmActionListener implements ActionListener {
		
		String notesInput;
		JFrame currentFrame;
		
		public ConfirmActionListener(JFrame currentFrame) {
            this.currentFrame = currentFrame;
        }
		
		public void actionPerformed(ActionEvent e) {

			String input = transaction.getText();
			
			if (notes.getText().isEmpty()) {
				notesInput = "";
			}
			else {
				notesInput = notes.getText();
			}
			
			DecimalFormat df = new DecimalFormat("#.##");
			df.setParseBigDecimal(true);
			
			try {
				float floatValue = df.parse(input).floatValue();
				int port = 9997;
				String ip = "localhost";
				try {
					Socket socket = new Socket(ip, port);
					OutputStream os = socket.getOutputStream();
					PrintWriter pw = new PrintWriter(os);
					pw.println(username);
					pw.println(input);
					pw.println(notesInput);
					pw.flush();
					
                    pw.close();
                    socket.close();
					
					JFrame frame = new NetIncomeFrame(username);
					currentFrame.dispose();
					
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				errorMessage.setText("Error: Invalid input string");
			}
			
			
		}
		
	}

}
