package com.codegym.inotes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static final String URL = System.getenv().getOrDefault(
            "INOTES_DB_URL", "jdbc:mysql://localhost:3306/inotes_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh");
    private static final String USER = System.getenv().getOrDefault("INOTES_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("INOTES_DB_PASSWORD", "");

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
