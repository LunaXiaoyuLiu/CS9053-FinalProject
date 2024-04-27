package FinanceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInServer {
	
	String username;
	String password;
	Socket socket;
	ServerSocket ss;
	
	public static void main(String[] args) throws IOException, SQLException {
		
		while (true) {
		
			LogInServer loginServer = new LogInServer();
			loginServer.checkDatabase();
			
		}
		
	}
	
	public LogInServer() {
		
		int port = 9999;
		
		try {
			System.out.println("Listening...");
			ss = new ServerSocket(port);
			socket = ss.accept();
			System.out.println("Connection established" + socket);
			
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			username = br.readLine();
			password = br.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void checkDatabase() throws IOException, SQLException {
		
		String url = "jdbc:mysql://127.0.0.1:3306/FinanceManager";
		String db_username = "root";
		String db_password = "";
		
		Connection conn = DriverManager.getConnection(url, db_username, db_password);
		
		String sql = "SELECT `password` FROM Users WHERE username = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, username);
        
		ResultSet rs = pstmt.executeQuery();
		
		boolean userExists = rs.next();
		
		if (userExists) {
			
			String true_password = rs.getString("password");
		    // User exists
			if (true_password.equals(password)) {
				sendMessage("Login successful!", socket.getOutputStream());
				System.out.println("Login Successful");
			}
			else {
				sendMessage("Wrong password.", socket.getOutputStream());
				System.out.println("Wrong password.");
			}
		} else {
		    // User doesn't exist
			sendMessage("User doesn't exist. Register first.", socket.getOutputStream());
		    System.out.println("User doesn't exist");
		}
		
		socket.close();
		ss.close();
		
	}

	private void sendMessage(String message, OutputStream outputStream) {
	    try {
	        PrintWriter pw = new PrintWriter(outputStream, true);
	        pw.println(message);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
