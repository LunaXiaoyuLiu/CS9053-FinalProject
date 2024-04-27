package FinanceManager;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LogInFrame extends JFrame {
	
	JPanel inputPanel;
	JPanel buttonPanel;
	JTextField usernameTextField;
	JTextField passwordTextField;
	
	public LogInFrame() {
		
		super("Finance Manager -- Log In");
		
		JPanel rootPanel = new JPanel();
		this.setContentPane(rootPanel);
		
		rootPanel.setLayout(new BorderLayout());
		
		inputPane();
		buttonPane();
		
		rootPanel.add(inputPanel, BorderLayout.CENTER);
		rootPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(400, 300);
		
		this.setVisible(true);
		
	}
	
	public void inputPane() {
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		
		JLabel usernameLabel = new JLabel("username ");
		constraints.gridx = 2;
        constraints.gridy = 2;
		inputPanel.add(usernameLabel, constraints);
		
		usernameTextField = new JTextField(10);
		constraints.gridx = 3;
        constraints.gridy = 2;
		inputPanel.add(usernameTextField, constraints);
		
		JLabel passwordLabel = new JLabel("password  ");
		constraints.gridx = 2;
        constraints.gridy = 3;
		inputPanel.add(passwordLabel, constraints);
		
		passwordTextField = new JTextField(10);
		constraints.gridx = 3;
        constraints.gridy = 3;
		inputPanel.add(passwordTextField, constraints);
		
	}
	
	public void buttonPane() {
		
		buttonPanel = new JPanel();
		
		JButton logInButton = new JButton("Log In");
		buttonPanel.add(logInButton);
		
		LogInActionListener logInListener = new LogInActionListener();
		logInButton.addActionListener(logInListener);
		
		JButton registerButton = new JButton("Register");
		buttonPanel.add(registerButton);
		
		RegisterActionListener registerListener = new RegisterActionListener(this);
		registerButton.addActionListener(registerListener);
		
	}
	
	private class LogInActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			
			// TODO
			int port = 9999;
			String ip = "localhost";
			try {
				Socket socket = new Socket(ip, port);
				
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os);
				pw.println(username);
				pw.println(password);
				pw.flush();
				
				pw.close();
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
	}
	
	private class RegisterActionListener implements ActionListener {
		
		private JFrame currentFrame;
		
		public RegisterActionListener(JFrame currentFrame) {
            this.currentFrame = currentFrame;
        }

		@Override
		public void actionPerformed(ActionEvent e) {

			JFrame frame = new RegisterFrame();
			currentFrame.dispose();
			
		}
		
	}

}
