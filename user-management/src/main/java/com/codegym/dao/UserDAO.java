package com.codegym.dao;

import com.codegym.model.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    // Thay username/password theo cấu hình MySQL trên máy của bạn.
    private static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    // Phương thức cũ được giữ lại để luyện tập/đối chiếu.
    @Override
    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, country) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.executeUpdate();
        }
    }

    @Override
    public User selectUser(int id) throws SQLException {
        String sql = "SELECT id, name, email, country FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("name"),
                            rs.getString("email"), rs.getString("country"));
                }
            }
        }
        return null;
    }

    @Override
    public List<User> selectUsers(String country, String sortBy) throws SQLException {
        String orderColumn = "name".equalsIgnoreCase(sortBy) ? "name" : "id";
        StringBuilder sql = new StringBuilder(
                "SELECT id, name, email, country FROM users");
        boolean hasCountry = country != null && !country.trim().isEmpty();
        if (hasCountry) sql.append(" WHERE country LIKE ?");
        sql.append(" ORDER BY ").append(orderColumn).append(" ASC");

        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            if (hasCountry) statement.setString(1, "%" + country.trim() + "%");
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getInt("id"), rs.getString("name"),
                            rs.getString("email"), rs.getString("country")));
                }
            }
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, country = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());
            return statement.executeUpdate() > 0;
        }
    }

    // Gọi Stored Procedure get_user_by_id bằng CallableStatement.
    @Override
    public User getUserById(int id) throws SQLException {
        String query = "{CALL get_user_by_id(?)}";
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {
            callableStatement.setInt(1, id);
            try (ResultSet rs = callableStatement.executeQuery()) {
                if (rs.next()) {
                    return new User(id, rs.getString("name"),
                            rs.getString("email"), rs.getString("country"));
                }
            }
        }
        return null;
    }

    // Gọi Stored Procedure insert_user bằng CallableStatement.
    @Override
    public void insertUserStore(User user) throws SQLException {
        String query = "{CALL insert_user(?, ?, ?)}";
        try (Connection connection = getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {
            callableStatement.setString(1, user.getName());
            callableStatement.setString(2, user.getEmail());
            callableStatement.setString(3, user.getCountry());
            callableStatement.executeUpdate();
        }
    }
}
