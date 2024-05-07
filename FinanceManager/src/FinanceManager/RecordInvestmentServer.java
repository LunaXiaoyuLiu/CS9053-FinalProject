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
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordInvestmentServer {
	
	ServerSocket ss;
	Socket socket;
	String username;
	String name;
	String date;
	float floatValueAmount;
	float floatValueYield;
	
	public static void main(String[] args) throws IOException, ParseException, SQLException {
		
		while (true) {
		
			RecordInvestmentServer server = new RecordInvestmentServer();
			
			server.insertIntoDatabase();
			
		}
		
	}
	
	public RecordInvestmentServer() throws IOException, ParseException {
		
		int port = 9995;
		
		System.out.println("Listening...");
		ss = new ServerSocket(port);
		
		socket = ss.accept();
		System.out.println("Connection established" + socket);
		
		InputStream in = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		username = br.readLine();
		String amount = br.readLine();
		String yield = br.readLine();
		name = br.readLine();
		date = br.readLine();
		
		floatValueAmount = parse(amount);
		floatValueYield = parse(yield);
        System.out.println("Parsed float value with 2 decimals: " + floatValueAmount + floatValueYield);
	}
	
	public void insertIntoDatabase() throws IOException, SQLException {
		
		String url = "jdbc:mysql://127.0.0.1:3306/FinanceManager";
		String db_username = "root";
		String db_password = "";
				
		Connection conn = DriverManager.getConnection(url, db_username, db_password);
		
		String sql = "INSERT INTO Investments (username, investmentAmount, expectedYield, matureDate, `name`) VALUES (?, ?, ?, STR_TO_DATE(?, '%Y-%m-%d'), ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setFloat(2, floatValueAmount);
        pstmt.setFloat(3, floatValueYield);
        pstmt.setString(4, date);
        pstmt.setString(5, name);
        int rowsInserted = pstmt.executeUpdate();
        System.out.println(rowsInserted + " row(s) inserted.");
        sendMessage("Record successful!", socket.getOutputStream());
        pstmt.close();
		
        conn.close();
		
		socket.close();
		ss.close();
		
	}
	
    private float parse(String netIncomeString) throws ParseException {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setParseBigDecimal(true);
        
        return df.parse(netIncomeString).floatValue();
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
