package com.codegym.dao;

import com.codegym.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Implements user search using JDBC and MySQL. */
public class UserDAO implements IUserDAO {
    private static final String SEARCH_USERS_SQL =
            "SELECT id, name, email, country FROM users "
            + "WHERE country LIKE ? ORDER BY name ASC";

    private final String jdbcUrl = setting("DB_URL", "jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=UTC");
    private final String jdbcUsername = setting("DB_USERNAME", "root");
    private final String jdbcPassword = setting("DB_PASSWORD", "password");

    private static String setting(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
    }

    @Override
    public List<User> searchByCountryOrderByName(String country) throws SQLException {
        List<User> users = new ArrayList<>();
        String searchTerm = country == null ? "" : country.trim();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_USERS_SQL)) {
            statement.setString(1, "%" + searchTerm + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("country")));
                }
            }
        }
        return users;
    }
}
