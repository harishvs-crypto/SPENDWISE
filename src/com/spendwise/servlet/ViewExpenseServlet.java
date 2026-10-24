package com.spendwise.servlet;

import com.spendwise.dao.ExpenseDAO;
import com.spendwise.model.Expense;
import com.spendwise.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewExpenseServlet extends HttpServlet {
    private ExpenseDAO expenseDAO = new ExpenseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (session == null || session.getAttribute("user") == null) {
            out.print("{\"status\":\"error\", \"message\":\"Unauthorized\"}");
            return;
        }

        User user = (User) session.getAttribute("user");
        List<Expense> expenses = expenseDAO.getAllExpensesByUser(user.getId());

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            json.append("{")
                .append("\"id\":").append(e.getId()).append(",")
                .append("\"amount\":").append(e.getAmount()).append(",")
                .append("\"category\":\"").append(e.getCategory()).append("\",")
                .append("\"date\":\"").append(e.getDate()).append("\",")
                .append("\"paymentMode\":\"").append(e.getPaymentMode()).append("\",")
                .append("\"description\":\"").append(e.getDescription()).append("\"")
                .append("}");
            if (i < expenses.size() - 1) json.append(",");
        }
        json.append("]");

        out.print(json.toString());
    }
}
