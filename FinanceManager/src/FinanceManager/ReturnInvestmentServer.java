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
import java.text.ParseException;

public class ReturnInvestmentServer {
	
	ServerSocket ss;
	Socket socket;
	String username;
	
	public static void main(String[] args) throws IOException, ParseException, SQLException {
		
		while (true) {
		
			ReturnInvestmentServer server = new ReturnInvestmentServer();
			
			server.queryDatabase();
			
		}
	
	}
	
	public ReturnInvestmentServer() throws IOException {
		
		int port = 9994;
		
		System.out.println("Listening...");
		ss = new ServerSocket(port);
		
		socket = ss.accept();
		System.out.println("Connection established" + socket);
		
		InputStream in = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		username = br.readLine();
		
		System.out.println(username);
		
	}
	
	public void queryDatabase() throws IOException, SQLException {
		
		String url = "jdbc:mysql://127.0.0.1:3306/FinanceManager";
		String db_username = "root";
		String db_password = "";
		
		Connection conn = DriverManager.getConnection(url, db_username, db_password);
		
		String sql = "SELECT sum(investmentAmount) AS totalInvestment FROM Investments WHERE matureDate > CURRENT_DATE AND Username = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        
        ResultSet rs = pstmt.executeQuery();
        
        String investment = "0.00";
        
        if (rs.next()) {
            investment = rs.getString("totalInvestment"); 
        }
        
        sendMessage(investment, socket.getOutputStream());
        
        sql = "SELECT sum(expectedYield) AS totalYield FROM Investments WHERE matureDate > CURRENT_DATE AND Username = ?";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        
        rs = pstmt.executeQuery();
        
        String yield = "0.00";
        
        if (rs.next()) {
            yield = rs.getString("totalYield"); 
        }
        
        sendMessage(yield, socket.getOutputStream());
        
        conn.close();
		socket.close();
		ss.close();
		
	}
	
	private void sendMessage(String message, OutputStream outputStream) {
		
		PrintWriter pw = new PrintWriter(outputStream, true);
	    pw.println(message);
	    
	}

}
