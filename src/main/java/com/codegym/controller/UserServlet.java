package com.codegym.controller;

import com.codegym.dao.UserDAO;
import com.codegym.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/** Handles the user list request, country filtering, and name sorting. */
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String country = request.getParameter("country");
        try {
            List<User> users = userDAO.searchByCountryOrderByName(country);
            request.setAttribute("listUser", users);
            request.setAttribute("country", country == null ? "" : country.trim());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/user/list.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException exception) {
            throw new ServletException("Unable to search users by country", exception);
        }
    }
}
