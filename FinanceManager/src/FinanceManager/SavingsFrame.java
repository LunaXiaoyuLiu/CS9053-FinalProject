package FinanceManager;

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
import java.text.SimpleDateFormat;

import javax.swing.JButton;
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
	JPanel SavingDetailPanel;
	JTextField inputAmount;
	JLabel errorMessage = new JLabel();
	String totalSavings;
	
	String date1;
	String amount1;
	String date2;
	String amount2;
	String date3;
	String amount3;
	String date4;
	String amount4;
	
	public SavingsFrame(String username) throws UnknownHostException, IOException {
		
		super("Finance Manager -- Savings");
		
		this.username = username;
		
		getValues();
		createRootPanel();
		this.add(rootPanel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 400);
		
		this.setVisible(true);
		
	}

	private void createRootPanel() {
		// TODO Auto-generated method stub
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
		createSavingDetailPanel();
		constraints.gridx = 1;
		constraints.gridy = 4;
		rootPanel.add(SavingDetailPanel, constraints);
		createButtonPanel();
		constraints.gridx = 1;
		constraints.gridy = 5;
		rootPanel.add(buttonPanel, constraints);
		
	}

	private void createButtonPanel() {
		// TODO Auto-generated method stub
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
		
	private void createSavingDetailPanel() {
		// TODO Auto-generated method stub
		SavingDetailPanel = new JPanel();
		SavingDetailPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		JLabel latestSavingsLabel = new JLabel("Latest Savings");
		constraints.gridx = 1;
        constraints.gridy = 1;
        SavingDetailPanel.add(latestSavingsLabel, constraints);
        
        JLabel timeLabel = new JLabel("Saving Time");
		constraints.gridx = 1;
        constraints.gridy = 2;
        SavingDetailPanel.add(timeLabel, constraints);
        
        JLabel savingAmountLabel = new JLabel("Saving Amount");
		constraints.gridx = 2;
        constraints.gridy = 2;
        SavingDetailPanel.add(savingAmountLabel, constraints);
        
        JLabel timeLabel1 = new JLabel(date1);
		constraints.gridx = 1;
        constraints.gridy = 3;
        SavingDetailPanel.add(timeLabel1, constraints);
        
        JLabel savingAmountLabel1 = new JLabel(amount1);
        constraints.gridx = 2;
        constraints.gridy = 3;
        SavingDetailPanel.add(savingAmountLabel1, constraints);
        
        JLabel timeLabel2 = new JLabel(date2);
        constraints.gridx = 1;
        constraints.gridy = 4;
        SavingDetailPanel.add(timeLabel2, constraints);
        
        JLabel savingAmountLabel2 = new JLabel(amount2);
        constraints.gridx = 2;
        constraints.gridy = 4;
        SavingDetailPanel.add(savingAmountLabel2, constraints);
        
        JLabel timeLabel3 = new JLabel(date3);
        constraints.gridx = 1;
        constraints.gridy = 5;
        SavingDetailPanel.add(timeLabel3, constraints);
        
        JLabel savingAmountLabel3 = new JLabel(amount3);
        constraints.gridx = 2;
        constraints.gridy = 5;
        SavingDetailPanel.add(savingAmountLabel3, constraints);
        
        JLabel timeLabel4 = new JLabel(date4);
        constraints.gridx = 1;
        constraints.gridy = 6;
        SavingDetailPanel.add(timeLabel4, constraints);
        
        JLabel savingAmountLabel4 = new JLabel(amount4);
        constraints.gridx = 2;
        constraints.gridy = 6;
        SavingDetailPanel.add(savingAmountLabel4, constraints);
		
	}

	private void createDisplayPanel() {
		// TODO Auto-generated method stub
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		JLabel savingAmountLabel = new JLabel("Total Saving Amount: ");
		constraints.gridx = 1;
        constraints.gridy = 1;
        displayPanel.add(savingAmountLabel, constraints);
        
        JLabel savingAmount = new JLabel(totalSavings);
		constraints.gridx = 2;
        constraints.gridy = 1;
        displayPanel.add(savingAmount, constraints);
		
	}

	private void createInputPanel() {
		// TODO Auto-generated method stub
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		JLabel inputAmountLabel = new JLabel("Saving Amount");
		constraints.gridx = 1;
		constraints.gridy = 1;
		inputPanel.add(inputAmountLabel, constraints);
		
		inputAmount = new JTextField(10);
		constraints.gridx = 2;
		constraints.gridy = 1;
		inputPanel.add(inputAmount, constraints);
		
		JButton confirmButton = new JButton("Confirm");
		ConfirmActionListener confirmListener = new ConfirmActionListener(this);
		confirmButton.addActionListener(confirmListener);
		constraints.gridx = 3;
		constraints.gridy = 1;
		inputPanel.add(confirmButton, constraints);
		
	}
	
	private class ConfirmActionListener implements ActionListener {
		
		JFrame currentFrame;
		
		public ConfirmActionListener(JFrame currentFrame) {
            this.currentFrame = currentFrame;
        }

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			String amount = inputAmount.getText();
			
			// TODO Auto-generated method stub
			DecimalFormat df = new DecimalFormat("#.##");
			df.setParseBigDecimal(true);
			
			try {
				float floatValue = df.parse(amount).floatValue();
				
				int port = 9993;
				String ip = "localhost";
				
				Socket socket;
				try {
					
					socket = new Socket(ip, port);
					OutputStream os = socket.getOutputStream();
					PrintWriter pw = new PrintWriter(os);
					pw.println(username);
					pw.println(amount);
					pw.flush();
					
					pw.close();
					socket.close();
					
					JFrame frame = new SavingsFrame(username);
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

	private void getValues() throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		int port = 9992;
		String ip = "localhost";
		
		Socket socket = new Socket(ip, port);
		OutputStream os = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(os);
		pw.println(username);
		pw.flush();
		
		InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        totalSavings = br.readLine();
        
        date1 = br.readLine();
        amount1 = br.readLine();
        
        date2 = br.readLine();
        amount2 = br.readLine();
        
        date3 = br.readLine();
        amount3 = br.readLine();
        
        date4 = br.readLine();
        amount4 = br.readLine();

        br.close();
		pw.close();
		socket.close();
		
	}

}
