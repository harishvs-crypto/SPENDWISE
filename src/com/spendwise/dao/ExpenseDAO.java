package com.spendwise.dao;

import com.spendwise.model.Expense;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    public boolean addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (user_id, amount, category, expense_date, payment_mode, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, expense.getUserId());
            stmt.setDouble(2, expense.getAmount());
            stmt.setString(3, expense.getCategory());
            stmt.setDate(4, expense.getDate());
            stmt.setString(5, expense.getPaymentMode());
            stmt.setString(6, expense.getDescription());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Expense> getAllExpensesByUser(int userId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE user_id = ? ORDER BY expense_date DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Expense exp = new Expense();
                exp.setId(rs.getInt("id"));
                exp.setUserId(rs.getInt("user_id"));
                exp.setAmount(rs.getDouble("amount"));
                exp.setCategory(rs.getString("category"));
                exp.setDate(rs.getDate("expense_date"));
                exp.setPaymentMode(rs.getString("payment_mode"));
                exp.setDescription(rs.getString("description"));
                expenses.add(exp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    public boolean deleteExpense(int expenseId) {
        String sql = "DELETE FROM expenses WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, expenseId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
