package com.spendwise.servlet;

import com.spendwise.dao.ExpenseDAO;
import com.spendwise.model.Expense;
import com.spendwise.model.User;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReportServlet extends HttpServlet {
    private ExpenseDAO expenseDAO = new ExpenseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) session.getAttribute("user");
        List<Expense> expenses = expenseDAO.getAllExpensesByUser(user.getId());
        String type = request.getParameter("type"); // "txt" or "csv"

        if ("csv".equalsIgnoreCase(type)) {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"category_report.csv\"");
            
            // Using Java I/O explicitly
            try (PrintWriter writer = response.getWriter();
                 BufferedWriter bw = new BufferedWriter(writer)) {
                bw.write("Category,Total Amount\n");
                
                // HashMap for collections requirement
                Map<String, Double> categoryTotals = new HashMap<>();
                for (Expense e : expenses) {
                    categoryTotals.put(e.getCategory(), categoryTotals.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
                }
                for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                    bw.write(entry.getKey() + "," + entry.getValue() + "\n");
                }
                bw.flush();
            }
        } else {
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment; filename=\"monthly_report.txt\"");
            
            try (PrintWriter writer = response.getWriter();
                 BufferedWriter bw = new BufferedWriter(writer)) {
                bw.write("--- SPENDWISE EXPORT ---\n");
                bw.write("Full Expense Report\n");
                bw.write("------------------------\n");
                for (Expense e : expenses) {
                    bw.write("Date: " + e.getDate() + " | Category: " + e.getCategory() 
                                   + " | Amount: " + e.getAmount() + " | Mode: " + e.getPaymentMode() + "\n");
                }
                bw.flush();
            }
        }
    }
}
