package com.codegym.dao;

import com.codegym.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "password"; // Đổi theo mật khẩu MySQL trên máy bạn.

    private Connection getConnection() throws SQLException {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { throw new SQLException("Không tìm thấy MySQL JDBC Driver", e); }
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    // Danh sách users được lấy bằng Stored Procedure + CallableStatement.
    @Override
    public List<User> selectAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL select_all_users()}");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("country")));
            }
        }
        return users;
    }

    @Override
    public User selectUser(int id) throws SQLException {
        String sql = "SELECT id, name, email, country FROM users WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return new User(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("country"));
            }
        }
        return null;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users(name, email, country) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getCountry());
            stmt.executeUpdate();
        }
    }

    // Sửa user bằng Stored Procedure + CallableStatement.
    @Override
    public void updateUser(User user) throws SQLException {
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL update_user(?, ?, ?, ?)}")) {
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getCountry());
            stmt.executeUpdate();
        }
    }

    // Xóa user bằng Stored Procedure + CallableStatement.
    @Override
    public void deleteUser(int id) throws SQLException {
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL delete_user(?)}")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}