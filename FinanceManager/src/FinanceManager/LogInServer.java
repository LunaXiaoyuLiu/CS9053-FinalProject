package FinanceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class LogInServer {
	
	String username;
	String passeord;
	
	public static void main(String[] args) {
		
		LogInServer();
	
	}
	
	public static void LogInServer() {
		
		int port = 9999;
		
		try {
			System.out.println("Listening...");
			ServerSocket loginSocket = new ServerSocket(port);
			Socket socket = loginSocket.accept();
			System.out.println("Connection established" + socket);
			
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String usernmae = br.readLine();
			String password = br.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
