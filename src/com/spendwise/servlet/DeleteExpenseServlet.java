package com.spendwise.servlet;

import com.spendwise.dao.ExpenseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteExpenseServlet extends HttpServlet {
    private ExpenseDAO expenseDAO = new ExpenseDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            boolean success = expenseDAO.deleteExpense(id);

            if (success) {
                out.print("{\"status\":\"success\"}");
                return;
            }
        }
        out.print("{\"status\":\"error\"}");
    }
}
