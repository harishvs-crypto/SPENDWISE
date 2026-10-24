package com.spendwise.thread;

import com.spendwise.dao.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetAlertThread extends Thread {
    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Sleep for 30 seconds before checking to simulate real-time
                Thread.sleep(30000); 

                checkBudgets();

            } catch (InterruptedException e) {
                System.out.println("BudgetAlertThread interrupted.");
                running = false;
            }
        }
    }

    private void checkBudgets() {
        String sql = "SELECT u.id, u.username, u.budget, IFNULL(SUM(e.amount), 0) as total_expenses " +
                     "FROM users u LEFT JOIN expenses e ON u.id = e.user_id " +
                     "GROUP BY u.id, u.username, u.budget";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                double budget = rs.getDouble("budget");
                double expenses = rs.getDouble("total_expenses");

                if (budget > 0) {
                    double percentage = (expenses / budget) * 100;
                    if (percentage >= 80.0) {
                        System.out.print("[ALERT] User " + rs.getString("username") + 
                                           " has used " + String.format("%.2f", percentage) + "% of their budget! ");
                        if (percentage >= 100) {
                            System.out.println("Budget Exceeded!");
                        } else {
                            System.out.println("Nearing Limit!");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
