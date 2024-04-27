package FinanceManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCTest {
	
	public static void  main(String[] args) throws Exception {
		
		String url = "jdbc:mysql://127.0.0.1:3306/FinanceManager";
		String username = "root";
		String password = "";
		Connection conn = DriverManager.getConnection(url, username, password);
		
		String sql = "UPDATE `user` SET password = '12' WHERE username = 'Luna'";
		
		Statement stmt = conn.createStatement();
		
		int count = stmt.executeUpdate(sql);
		
		System.out.println(count);
		
		stmt.close();
		conn.close();
		
	}

}
