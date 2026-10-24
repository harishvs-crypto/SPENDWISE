package com.spendwise.servlet;

import com.spendwise.dao.UserDAO;
import com.spendwise.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        
        User authenticatedUser = userDAO.authenticateUser(user, pass);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        if (authenticatedUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", authenticatedUser);
            out.print("{\"status\":\"success\", \"id\":" + authenticatedUser.getId() + ", \"username\":\"" + authenticatedUser.getUsername() + "\", \"budget\":" + authenticatedUser.getBudget() + "}");
        } else {
            out.print("{\"status\":\"error\", \"message\":\"Invalid credentials\"}");
        }
    }
}
