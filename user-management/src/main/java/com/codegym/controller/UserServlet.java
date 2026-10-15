package com.codegym.controller;

import com.codegym.dao.UserDAO;
import com.codegym.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
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
        String action = request.getParameter("action");
        if (action == null || action.isBlank()) action = "list";

        try {
            switch (action) {
                case "create":
                    request.getRequestDispatcher("/user/create.jsp").forward(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    showDeleteForm(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Không thể xử lý yêu cầu User.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                User newUser = readUser(request, false);
                // Bài thực hành này dùng Stored Procedure thay cho insertUser().
                userDAO.insertUserStore(newUser);
            } else if ("edit".equals(action)) {
                userDAO.updateUser(readUser(request, true));
            } else if ("delete".equals(action)) {
                userDAO.deleteUser(Integer.parseInt(request.getParameter("id")));
            }
            // PRG pattern: tránh gửi lại form khi người dùng refresh.
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (Exception e) {
            throw new ServletException("Không thể lưu thay đổi User.", e);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String country = request.getParameter("country");
        String sort = request.getParameter("sort");
        if (sort == null || sort.isBlank()) sort = "name";
        request.setAttribute("listUser", userDAO.selectUsers(country, sort));
        request.setAttribute("country", country == null ? "" : country);
        request.setAttribute("sort", sort);
        request.getRequestDispatcher("/user/list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        // Bài thực hành này dùng Stored Procedure get_user_by_id.
        User user = userDAO.getUserById(id);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy User.");
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("/user/edit.jsp").forward(request, response);
    }

    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.selectUser(id);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy User.");
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("/user/delete.jsp").forward(request, response);
    }

    private User readUser(HttpServletRequest request, boolean includeId) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        if (includeId) {
            return new User(Integer.parseInt(request.getParameter("id")), name, email, country);
        }
        return new User(name, email, country);
    }
}
