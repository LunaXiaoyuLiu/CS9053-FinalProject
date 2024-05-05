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

class ReturnNetIncomeServer {
	
	ServerSocket ss;
	Socket socket;
	String username;
	
	public static void main(String[] args) throws IOException, ParseException, SQLException {
		
		while (true) {
		
			ReturnNetIncomeServer server = new ReturnNetIncomeServer();
			
			server.queryDatabase();
			
		}
		
	}

	public ReturnNetIncomeServer() throws IOException {
		
		int port = 9996;
		
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
		
		String sql = "SELECT SUM(NetIncome) AS TotalIncome FROM NetIncome WHERE DATE(Time) = CURDATE() AND Username = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        
        ResultSet rs = pstmt.executeQuery();
        
        String daily = "0.00"; // Initialize totalIncome variable
        
        if (rs.next()) {
            daily = rs.getString("TotalIncome"); // Retrieve TotalIncome from the result set
        }
        
        sql = "SELECT SUM(NetIncome) AS TotalIncome FROM NetIncome WHERE YEAR(Time) = YEAR(CURDATE()) AND MONTH(Time) = MONTH(CURDATE()) AND Username = ?";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        
        rs = pstmt.executeQuery();
        
        String monthly = "0.00"; // Initialize totalIncome variable
        
        if (rs.next()) {
            monthly = rs.getString("TotalIncome"); // Retrieve TotalIncome from the result set
        }
        
        sql = "SELECT SUM(NetIncome) AS TotalIncome FROM NetIncome WHERE YEAR(Time) = YEAR(CURDATE()) AND Username = ?";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        
        rs = pstmt.executeQuery();
        
        String yearly = "0.00"; // Initialize totalIncome variable
        
        if (rs.next()) {
            yearly = rs.getString("TotalIncome"); // Retrieve TotalIncome from the result set
        }
        
        sendMessage(daily, socket.getOutputStream());
        sendMessage(monthly, socket.getOutputStream());
        sendMessage(yearly, socket.getOutputStream());
        
        String trnsactionAmount1 = "";
        String trnsactionAmount2 = "";
        String trnsactionAmount3 = "";
        String trnsactionAmount4 = "";
        String trnsactionNote1 = "";
        String trnsactionNote2 = "";
        String trnsactionNote3 = "";
        String trnsactionNote4 = "";
        String trnsactionTime1 = "";
        String trnsactionTime2 = "";
        String trnsactionTime3 = "";
        String trnsactionTime4 = "";
        
        sql = "SELECT NetIncome, Notes, `Time` FROM NetIncome WHERE Username = ? ORDER BY `Time` DESC LIMIT 4";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        
        rs = pstmt.executeQuery();
        
        if (rs.next()) {
        	trnsactionAmount1 = rs.getString("NetIncome"); // Retrieve TotalIncome from the result set
        	trnsactionNote1 = rs.getString("Notes");
        	trnsactionTime1 = rs.getString("Time");
        }
        
        sendMessage(trnsactionAmount1, socket.getOutputStream());
        sendMessage(trnsactionNote1, socket.getOutputStream());
        sendMessage(trnsactionTime1, socket.getOutputStream());
        
        if (rs.next()) {
        	trnsactionAmount2 = rs.getString("NetIncome"); // Retrieve TotalIncome from the result set
        	trnsactionNote2 = rs.getString("Notes");
        	trnsactionTime2 = rs.getString("Time");
        }
        
        sendMessage(trnsactionAmount2, socket.getOutputStream());
        sendMessage(trnsactionNote2, socket.getOutputStream());
        sendMessage(trnsactionTime2, socket.getOutputStream());
        
        if (rs.next()) {
        	trnsactionAmount3 = rs.getString("NetIncome"); // Retrieve TotalIncome from the result set
        	trnsactionNote3 = rs.getString("Notes");
        	trnsactionTime3 = rs.getString("Time");
        }
        
        sendMessage(trnsactionAmount3, socket.getOutputStream());
        sendMessage(trnsactionNote3, socket.getOutputStream());
        sendMessage(trnsactionTime3, socket.getOutputStream());
        
        if (rs.next()) {
        	trnsactionAmount4 = rs.getString("NetIncome"); // Retrieve TotalIncome from the result set
        	trnsactionNote4 = rs.getString("Notes");
        	trnsactionTime4 = rs.getString("Time");
        }
        
        sendMessage(trnsactionAmount4, socket.getOutputStream());
        sendMessage(trnsactionNote4, socket.getOutputStream());
        sendMessage(trnsactionTime4, socket.getOutputStream());
		
        conn.close();
		socket.close();
		ss.close();
		
    }
	
	private void sendMessage(String message, OutputStream outputStream) {
		
		PrintWriter pw = new PrintWriter(outputStream, true);
	    pw.println(message);
	    
	}

}
