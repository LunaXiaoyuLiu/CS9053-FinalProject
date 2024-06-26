package FinanceManager;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Server {
		
    public static void main(String[] args) throws IOException, SQLException, ParseException {
        // Create threads for each server
        Thread loginThread = new Thread(() -> {
            try {
                LogInServer.main(args);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        Thread registerThread = new Thread(() -> {
            try {
                RegisterServer.main(args);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread recordNetIncomeThread = new Thread(() -> {
            try {
                RecordNetIncomeServer.main(args);
            } catch (IOException | SQLException | ParseException e) {
                e.printStackTrace();
            }
        });
        
        Thread returnNetIncomeThread = new Thread(() -> {
            try {
                ReturnNetIncomeServer.main(args);
            } catch (IOException | SQLException | ParseException e) {
                e.printStackTrace();
            }
        });
        
        Thread recordInvestmentThread = new Thread(() -> {
            try {
                RecordInvestmentServer.main(args);
            } catch (IOException | SQLException | ParseException e) {
                e.printStackTrace();
            }
        });
        
        Thread returnInvestmentThread = new Thread(() -> {
            try {
                ReturnInvestmentServer.main(args);
            } catch (IOException | SQLException | ParseException e) {
                e.printStackTrace();
            }
        });
        
        Thread recordSavingsThread = new Thread(() -> {
            try {
                RecordSavingsServer.main(args);
            } catch (IOException | SQLException | ParseException e) {
                e.printStackTrace();
            }
        });
        
        Thread returnSavingsThread = new Thread(() -> {
            try {
                ReturnSavingsServer.main(args);
            } catch (IOException | SQLException | ParseException e) {
                e.printStackTrace();
            }
        });
        
        Thread dashboardThread = new Thread(() -> {
            try {
                DashboardServer.main(args);
            } catch (IOException | SQLException | ParseException e) {
                e.printStackTrace();
            }
        });

        // Start all threads
        loginThread.start();
        registerThread.start();
        recordNetIncomeThread.start();
        returnNetIncomeThread.start();
        recordInvestmentThread.start();
        returnInvestmentThread.start();
        recordSavingsThread.start();
        returnSavingsThread.start();
        dashboardThread.start();
    }			

}
