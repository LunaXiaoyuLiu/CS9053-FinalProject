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
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class RegisterServer {
	
	String username;
	String password;
	Socket socket;
	ServerSocket ss;
	
	public static void main(String[] args) throws IOException {
		
		while (true) {
		
			RegisterServer registerServer = new RegisterServer();
			registerServer.insertIntoDatabase();
			
		}
		
	}
	
	public RegisterServer() {
		
		int port = 9998;
		
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
	
	public void insertIntoDatabase() throws IOException {
		
		String url = "jdbc:mysql://127.0.0.1:3306/FinanceManager";
		String db_username = "root";
		String db_password = "";
		try {
			Connection conn = DriverManager.getConnection(url, db_username, db_password);
			
			String sql = "INSERT INTO Users(username, `password`) VALUES (?, ?)";
			
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                int rowsInserted = pstmt.executeUpdate();
                System.out.println(rowsInserted + " row(s) inserted.");
                sendMessage("Registration successful!", socket.getOutputStream());
                pstmt.close();
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			conn.close();
			
			
		} catch (SQLIntegrityConstraintViolationException e) {
	        // Duplicate entry exception handling
			try {
				sendMessage("Username is already in user.", socket.getOutputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
