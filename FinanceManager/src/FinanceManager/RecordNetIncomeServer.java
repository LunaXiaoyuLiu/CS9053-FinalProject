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
import java.text.DecimalFormat;
import java.text.ParseException;

public class RecordNetIncomeServer {
	
	ServerSocket ss;
	Socket socket;
	String username;
	String netIncome;
	String notes;
	float floatValue;
	
	public static void main(String[] args) throws IOException, ParseException, SQLException {
		
		while (true) {
		
			RecordNetIncomeServer server = new RecordNetIncomeServer();
			
			server.insertIntoDatabase();
			
		}
		
	}
	
	public RecordNetIncomeServer() throws IOException, ParseException {
		
		int port = 9997;
		
		System.out.println("Listening...");
		ss = new ServerSocket(port);
		
		socket = ss.accept();
		System.out.println("Connection established" + socket);
		
		InputStream in = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		username = br.readLine();
		netIncome = br.readLine();
		notes = br.readLine();
		
		floatValue = parseNetIncome(netIncome);
        System.out.println("Parsed float value with 2 decimals: " + floatValue);
		
	}
	
    private float parseNetIncome(String netIncomeString) throws ParseException {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setParseBigDecimal(true);
        
        return df.parse(netIncomeString).floatValue();
    }
	
	public void insertIntoDatabase() throws IOException, SQLException {
		
		String url = "jdbc:mysql://127.0.0.1:3306/FinanceManager";
		String db_username = "root";
		String db_password = "";
				
		Connection conn = DriverManager.getConnection(url, db_username, db_password);
		
		String sql = "INSERT INTO NetIncome(Username, NetIncome, `Time`, Notes) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setFloat(2, floatValue);
        pstmt.setString(3, notes);
        int rowsInserted = pstmt.executeUpdate();
        System.out.println(rowsInserted + " row(s) inserted.");
        sendMessage("Record successful!", socket.getOutputStream());
        pstmt.close();
        
        conn.close();
		
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
