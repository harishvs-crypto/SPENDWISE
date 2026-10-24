package com.spendwise.servlet;

import com.spendwise.dao.UserDAO;
import com.spendwise.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        double budget = Double.parseDouble(request.getParameter("budget") != null ? request.getParameter("budget") : "0");

        User newUser = new User(0, username, pass, budget);
        boolean success = userDAO.registerUser(newUser);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (success) {
            out.print("{\"status\":\"success\"}");
        } else {
            out.print("{\"status\":\"error\", \"message\":\"Username may already exist.\"}");
        }
    }
}
