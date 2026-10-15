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
    @Override public void init(){userDAO=new UserDAO();}

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        request.setCharacterEncoding("UTF-8");
        String action=request.getParameter("action");
        try {
            if("test-use-tran".equals(action)) {
                testUseTran(request,response);
            } else if("create".equals(action)) {
                request.getRequestDispatcher("/user/create.jsp").forward(request,response);
            } else if("edit".equals(action)) {
                User user=userDAO.selectUser(Integer.parseInt(request.getParameter("id")));
                if(user==null){response.sendError(404,"Không tìm thấy user");return;}
                request.setAttribute("user",user);
                request.getRequestDispatcher("/user/edit.jsp").forward(request,response);
            } else if("delete".equals(action)) {
                userDAO.deleteUser(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect(request.getContextPath()+"/users");
            } else {
                request.setAttribute("users",userDAO.selectAllUsers());
                request.getRequestDispatcher("/user/list.jsp").forward(request,response);
            }
        } catch(SQLException|NumberFormatException e) {
            throw new ServletException("Không thể xử lý yêu cầu.",e);
        }
    }

    private void testUseTran(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        try {
            userDAO.insertUpdateUseTransaction();
            request.setAttribute("testMessage","Transaction đã commit thành công. Hãy kiểm tra bảng Employee.");
        } catch(SQLException expected) {
            // Lỗi là có chủ đích trong lần thử đầu; DAO đã rollback trước khi ném lỗi lên.
            request.setAttribute("testError",expected.getMessage());
            request.setAttribute("testMessage","Đã xảy ra lỗi như dự kiến. Transaction đã rollback; kiểm tra bảng Employee.");
        }
        request.getRequestDispatcher("/user/test-use-transaction.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        request.setCharacterEncoding("UTF-8");
        String action=request.getParameter("action");
        try {
            if("insert".equals(action)) {
                userDAO.insertUser(new User(request.getParameter("name"),request.getParameter("email"),request.getParameter("country")));
                response.sendRedirect(request.getContextPath()+"/users?created=1");
            } else if("update".equals(action)) {
                userDAO.updateUser(new User(Integer.parseInt(request.getParameter("id")),request.getParameter("name"),request.getParameter("email"),request.getParameter("country")));
                response.sendRedirect(request.getContextPath()+"/users?updated=1");
            } else response.sendError(400,"Action không hợp lệ");
        } catch(SQLException|NumberFormatException e) {
            throw new ServletException("Lưu dữ liệu thất bại.",e);
        }
    }
}