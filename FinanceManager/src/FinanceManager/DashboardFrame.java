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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DashboardFrame extends JFrame {
	
	JPanel Panel;
	String netIncome = "0.00";
	String investment = "0.00"; 
	String savings = "0.00"; 
	String username;
	
	public DashboardFrame(String username) throws UnknownHostException, IOException {
		
		super("Finance Manager -- Dashboard");
		
		this.username = username;
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 300);
		
		getValues();
		Panel();
		this.add(Panel);
		
		this.setVisible(true);
		
	}
	
	public void getValues() throws UnknownHostException, IOException {
		
		int port = 9991;
		String ip = "localhost";
		
		Socket socket = new Socket(ip, port);
		OutputStream os = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(os);
		pw.println(username);
		pw.flush();
		
		System.out.println("reached here");
		
		InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        netIncome = br.readLine();
        investment = br.readLine();
		savings = br.readLine();
		
		br.close();
		pw.close();
		socket.close();
	}
	
	public void Panel() {
		
		Panel = new JPanel();
		Panel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		JLabel welcomeMessage = new JLabel("Hi " + username + "!");
		constraints.gridx = 1;
        constraints.gridy = 1;
		Panel.add(welcomeMessage, constraints);
		
		JLabel netIncomeLabel = new JLabel("Net Income (this month):  ");
		constraints.gridx = 1;
        constraints.gridy = 2;
		Panel.add(netIncomeLabel, constraints);
		
		JLabel netIncomeNumberLabel = new JLabel(netIncome);
		constraints.gridx = 2;
        constraints.gridy = 2;
		Panel.add(netIncomeNumberLabel, constraints);
		
		JButton netIncomeButton = new JButton("View Net Income Details");
		constraints.gridx = 3;
        constraints.gridy = 2;
        ViewDetailsActionListener1 Listener1 = new ViewDetailsActionListener1(this);
		netIncomeButton.addActionListener(Listener1);
		Panel.add(netIncomeButton, constraints);
		
		JLabel investmentLabel = new JLabel("Total Investment Amount: ");
		constraints.gridx = 1;
        constraints.gridy = 3;
		Panel.add(investmentLabel, constraints);
		
		JLabel investmentAmountNumberLabel = new JLabel(investment);
		constraints.gridx = 2;
        constraints.gridy = 3;
		Panel.add(investmentAmountNumberLabel, constraints);
		
		JButton investmentButton = new JButton("View Investment Details");
		constraints.gridx = 3;
        constraints.gridy = 3;
        ViewDetailsActionListener2 Listener2 = new ViewDetailsActionListener2(this);
        investmentButton.addActionListener(Listener2);
		Panel.add(investmentButton, constraints);
		
		JLabel savingsLabel = new JLabel("Total Savings Amount: ");
		constraints.gridx = 1;
        constraints.gridy = 4;
		Panel.add(savingsLabel, constraints);
		
		JLabel savingsNumberLabel = new JLabel(savings);
		constraints.gridx = 2;
        constraints.gridy = 4;
		Panel.add(savingsNumberLabel, constraints);
		
		JButton savingsButton = new JButton("View Savings Details");
		constraints.gridx = 3;
        constraints.gridy = 4;
        ViewDetailsActionListener3 Listener3 = new ViewDetailsActionListener3(this);
        savingsButton.addActionListener(Listener3);
		Panel.add(savingsButton, constraints);
		
	}
	
	private class ViewDetailsActionListener1 implements ActionListener {
		
		private JFrame currentFrame;
		
		public ViewDetailsActionListener1(JFrame currentFrame) {
            this.currentFrame = currentFrame;
        }

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				JFrame frame = new NetIncomeFrame(username);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			currentFrame.dispose();
			
		}
		
	}

	private class ViewDetailsActionListener2 implements ActionListener {
		
		private JFrame currentFrame;
		
		public ViewDetailsActionListener2(JFrame currentFrame) {
            this.currentFrame = currentFrame;
        }

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				JFrame frame = new InvestmentFrame(username);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			currentFrame.dispose();
			
		}
		
	}
	
	private class ViewDetailsActionListener3 implements ActionListener {
		
		private JFrame currentFrame;
		
		public ViewDetailsActionListener3(JFrame currentFrame) {
            this.currentFrame = currentFrame;
        }

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				JFrame frame = new SavingsFrame(username);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			currentFrame.dispose();
			
		}
		
	}
	
}
