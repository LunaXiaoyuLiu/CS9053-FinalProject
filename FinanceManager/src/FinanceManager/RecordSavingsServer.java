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
import java.text.DecimalFormat;
import java.text.ParseException;

public class RecordSavingsServer {
	
	ServerSocket ss;
	Socket socket;
	String username;
	float floatValueAmount;
	
	public static void main(String[] args) throws IOException, ParseException, SQLException {
		
		while (true) {
		
			RecordSavingsServer server = new RecordSavingsServer();
			
			server.insertIntoDatabase();
			
		}
		
	}
	
	public RecordSavingsServer() throws IOException, ParseException {
		
		int port = 9993;
		
		System.out.println("Listening...");
		ss = new ServerSocket(port);
		
		socket = ss.accept();
		System.out.println("Connection established" + socket);
		
		InputStream in = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		username = br.readLine();
		String amount = br.readLine();
		
		floatValueAmount = parse(amount);
        System.out.println("Parsed float value with 2 decimals: " + floatValueAmount);
	}
	

	private float parse(String amount) throws ParseException {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#.##");
        df.setParseBigDecimal(true);
        
        return df.parse(amount).floatValue();
	}

	private void insertIntoDatabase() throws SQLException, IOException {
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://127.0.0.1:3306/FinanceManager";
		String db_username = "root";
		String db_password = "";
				
		Connection conn = DriverManager.getConnection(url, db_username, db_password);
		
		String sql = "INSERT INTO Savings (username, savingAmount, savingTime) VALUES (?, ?, CURRENT_TIMESTAMP)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setFloat(2, floatValueAmount);
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
