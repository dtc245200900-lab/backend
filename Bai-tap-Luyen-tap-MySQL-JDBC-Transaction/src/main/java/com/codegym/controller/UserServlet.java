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
    @Override public void init(){ userDAO=new UserDAO(); }

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        request.setCharacterEncoding("UTF-8");
        String action=request.getParameter("action");
        try {
            if("create".equals(action)) {
                request.getRequestDispatcher("/user/create.jsp").forward(request,response);
            } else if("edit".equals(action)) {
                User user=userDAO.selectUser(Integer.parseInt(request.getParameter("id")));
                if(user==null){response.sendError(404,"Không tìm thấy User");return;}
                request.setAttribute("user",user);
                request.getRequestDispatcher("/user/edit.jsp").forward(request,response);
            } else if("delete".equals(action)) {
                userDAO.deleteUser(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect(request.getContextPath()+"/users?deleted=1");
            } else {
                request.setAttribute("users",userDAO.selectAllUsers());
                request.getRequestDispatcher("/user/list.jsp").forward(request,response);
            }
        } catch(SQLException|NumberFormatException e) {
            throw new ServletException("Không thể xử lý yêu cầu User.",e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
        request.setCharacterEncoding("UTF-8");
        String action=request.getParameter("action");
        try {
            if("insert".equals(action)) {
                String[] values=request.getParameterValues("permissions");
                int[] permissions=null;
                if(values!=null) {
                    permissions=new int[values.length];
                    for(int i=0;i<values.length;i++) permissions[i]=Integer.parseInt(values[i]);
                }
                User user=new User(request.getParameter("name"),request.getParameter("email"),request.getParameter("country"));
                userDAO.addUserTransaction(user,permissions);
                response.sendRedirect(request.getContextPath()+"/users?created=1");
            } else if("update".equals(action)) {
                User user=new User(Integer.parseInt(request.getParameter("id")),request.getParameter("name"),
                    request.getParameter("email"),request.getParameter("country"));
                userDAO.updateUser(user);
                response.sendRedirect(request.getContextPath()+"/users?updated=1");
            } else response.sendError(400,"Action không hợp lệ");
        } catch(SQLException|NumberFormatException e) {
            throw new ServletException("Không thể lưu User. Nếu thao tác phân quyền lỗi, transaction đã rollback.",e);
        }
    }
}