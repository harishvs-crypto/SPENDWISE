package com.spendwise.servlet;

import com.spendwise.dao.ExpenseDAO;
import com.spendwise.model.Expense;
import com.spendwise.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddExpenseServlet extends HttpServlet {
    private ExpenseDAO expenseDAO = new ExpenseDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (session == null || session.getAttribute("user") == null) {
            out.print("{\"status\":\"error\", \"message\":\"Unauthorized\"}");
            return;
        }

        User user = (User) session.getAttribute("user");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String category = request.getParameter("category");
        Date date = Date.valueOf(request.getParameter("date"));
        String paymentMode = request.getParameter("paymentMode");
        String description = request.getParameter("description");

        Expense expense = new Expense(0, user.getId(), amount, category, date, paymentMode, description);
        boolean success = expenseDAO.addExpense(expense);

        if (success) {
            out.print("{\"status\":\"success\"}");
        } else {
            out.print("{\"status\":\"error\", \"message\":\"Failed to add expense\"}");
        }
    }
}
