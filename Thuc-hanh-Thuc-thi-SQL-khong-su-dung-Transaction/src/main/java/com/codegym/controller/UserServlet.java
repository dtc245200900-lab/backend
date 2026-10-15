package com.codegym.controller;

import com.codegym.dao.UserDAO;
import com.codegym.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override public void init() { userDAO = new UserDAO(); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            if ("test-without-tran".equals(action)) {
                testWithoutTran(request, response);
            } else if ("create".equals(action)) {
                request.getRequestDispatcher("/user/create.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                User user = userDAO.selectUser(id);
                if (user == null) { response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy user"); return; }
                request.setAttribute("user", user);
                request.getRequestDispatcher("/user/edit.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                userDAO.deleteUser(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect(request.getContextPath() + "/users");
            } else {
                request.setAttribute("users", userDAO.selectAllUsers());
                request.getRequestDispatcher("/user/list.jsp").forward(request, response);
            }
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Không thể xử lý yêu cầu user.", e);
        }
    }

    private void testWithoutTran(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            userDAO.insertUpdateWithoutTransaction();
        } catch (SQLException expected) {
            // Đây là lỗi có chủ đích của bài thực hành; tiếp tục trả trang kết quả cho người dùng.
            request.setAttribute("testError", expected.getMessage());
        }
        request.setAttribute("testDone", true);
        request.getRequestDispatcher("/user/test-without-transaction.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try {
            if ("insert".equals(action)) {
                User user = new User(request.getParameter("name"), request.getParameter("email"), request.getParameter("country"));
                userDAO.insertUser(user);
                response.sendRedirect(request.getContextPath() + "/users?created=1");
            } else if ("update".equals(action)) {
                User user = new User(Integer.parseInt(request.getParameter("id")), request.getParameter("name"),
                    request.getParameter("email"), request.getParameter("country"));
                userDAO.updateUser(user);
                response.sendRedirect(request.getContextPath() + "/users?updated=1");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action không hợp lệ");
            }
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Lưu dữ liệu thất bại.", e);
        }
    }
}