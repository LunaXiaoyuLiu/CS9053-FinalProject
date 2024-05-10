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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RegisterFrame extends JFrame {
	
	JPanel rootPanel;
	JPanel inputPanel;
	JPanel buttonPanel;
	JTextField usernameTextField;
	JTextField passwordTextField;
	JLabel message;
	
	public RegisterFrame() {
		
		super("Finance Manager -- Register");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(400, 300);
		
		JPanel rootPanel = new JPanel();
		this.setContentPane(rootPanel);
		
		rootPanel.setLayout(new BorderLayout());
		
		inputPane();
		buttonPane();
		
		message = new JLabel("");
		rootPanel.add(message, BorderLayout.NORTH);
		
		rootPanel.add(inputPanel, BorderLayout.CENTER);
		rootPanel.add(buttonPanel, BorderLayout.SOUTH);
		
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
		
		JButton registerButton = new JButton("Register");
		buttonPanel.add(registerButton);
		
		RegisterActionListener logInListener = new RegisterActionListener(this);
		registerButton.addActionListener(logInListener);
		
		JButton backButton = new JButton("Back");
		buttonPanel.add(backButton);
		
		BackActionListener backListener = new BackActionListener(this);
		backButton.addActionListener(backListener);
		
	}
	
	private class RegisterActionListener implements ActionListener {
		
		private JFrame currentFrame;
		
		public RegisterActionListener(JFrame currentFrame) {
            this.currentFrame = currentFrame;
        }

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			
			if (username.isEmpty() || password.isEmpty()) {
				
				message.setText("Username or password cannot be empty.");
				
			}
			
			else {
	            int port = 9998;
	            String ip = "localhost";
	            Socket socket;
	            try {
	                socket = new Socket(ip, port);
	                
	                OutputStream os = socket.getOutputStream();
	                
	                PrintWriter pw = new PrintWriter(os);
	                pw.println(username);
	                pw.println(password);
	                pw.flush();
	                
	                InputStream is = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String response = br.readLine();
                    
                    if (SwingUtilities.isEventDispatchThread()) {
                        message.setText(response);
                        System.out.println(message.getText()); // Print response for debugging
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            message.setText(response);
                            System.out.println(message.getText()); // Print response for debugging
                        });
                    }
                    
                    
                    br.close();
                    pw.close();      
                    
	            } catch (UnknownHostException ex) {
	                ex.printStackTrace();
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	            
	            
				
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

			JFrame frame = new LogInFrame();
			currentFrame.dispose();
			
		}
		
	}

}
