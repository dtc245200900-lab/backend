package com.codegym.dao;

import com.codegym.model.User;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "password"; // Đổi thành mật khẩu MySQL của bạn.

    private static final String SQL_INSERT = "INSERT INTO Employee (name, salary, created_Date) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE Employee SET salary=? WHERE name=?";
    private static final String SQL_TABLE_CREATE = "CREATE TABLE Employee (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(120) NOT NULL, salary DECIMAL(15,2) NOT NULL, created_Date TIMESTAMP NULL, PRIMARY KEY (id)) ENGINE=InnoDB";
    private static final String SQL_TABLE_DROP = "DROP TABLE IF EXISTS Employee";

    private Connection getConnection() throws SQLException {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { throw new SQLException("Không tìm thấy MySQL JDBC Driver", e); }
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    @Override
    public List<User> selectAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection c=getConnection(); PreparedStatement ps=c.prepareStatement("SELECT id,name,email,country FROM users ORDER BY id DESC"); ResultSet rs=ps.executeQuery()) {
            while(rs.next()) users.add(new User(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("country")));
        }
        return users;
    }
    @Override
    public User selectUser(int id) throws SQLException {
        try(Connection c=getConnection(); PreparedStatement ps=c.prepareStatement("SELECT id,name,email,country FROM users WHERE id=?")) {
            ps.setInt(1,id);
            try(ResultSet rs=ps.executeQuery()) { if(rs.next()) return new User(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("country")); }
        }
        return null;
    }
    @Override
    public void insertUser(User user) throws SQLException {
        try(Connection c=getConnection(); PreparedStatement ps=c.prepareStatement("INSERT INTO users(name,email,country) VALUES(?,?,?)")) {
            ps.setString(1,user.getName()); ps.setString(2,user.getEmail()); ps.setString(3,user.getCountry()); ps.executeUpdate();
        }
    }
    @Override
    public void updateUser(User user) throws SQLException {
        try(Connection c=getConnection(); PreparedStatement ps=c.prepareStatement("UPDATE users SET name=?,email=?,country=? WHERE id=?")) {
            ps.setString(1,user.getName()); ps.setString(2,user.getEmail()); ps.setString(3,user.getCountry()); ps.setInt(4,user.getId()); ps.executeUpdate();
        }
    }
    @Override
    public void deleteUser(int id) throws SQLException {
        try(Connection c=getConnection(); PreparedStatement ps=c.prepareStatement("DELETE FROM users WHERE id=?")) { ps.setInt(1,id); ps.executeUpdate(); }
    }

    @Override
    public void insertUpdateUseTransaction() throws SQLException {
        try (Connection conn = getConnection()) {
            boolean originalAutoCommit = conn.getAutoCommit();
            try {
                // DROP/CREATE trước transaction để chỉ các thao tác INSERT/UPDATE thuộc transaction.
                try (Statement statement = conn.createStatement()) {
                    statement.execute(SQL_TABLE_DROP);
                    statement.execute(SQL_TABLE_CREATE);
                }

                conn.setAutoCommit(false);
                try (PreparedStatement psInsert = conn.prepareStatement(SQL_INSERT);
                     PreparedStatement psUpdate = conn.prepareStatement(SQL_UPDATE)) {
                    psInsert.setString(1, "Quynh");
                    psInsert.setBigDecimal(2, new BigDecimal("10.00"));
                    psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                    psInsert.executeUpdate();

                    psInsert.setString(1, "Ngan");
                    psInsert.setBigDecimal(2, new BigDecimal("20.00"));
                    psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                    psInsert.executeUpdate();

                    // BẢN MẶC ĐỊNH: cố tình gây lỗi giống yêu cầu bài để kiểm tra rollback.
                    // Muốn thử lần 2 thành công, đổi tham số 2 thành 1 ở dòng dưới.
                    psUpdate.setBigDecimal(2, new BigDecimal("999.99"));
                    psUpdate.setString(2, "Quynh");
                    psUpdate.executeUpdate();
                }
                conn.commit();
                System.out.println("Transaction commit thành công.");
            } catch (SQLException | RuntimeException e) {
                try {
                    conn.rollback();
                    System.out.println("Có lỗi: transaction đã rollback.");
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                if (e instanceof SQLException) throw (SQLException)e;
                throw new SQLException("Lỗi khi thực thi transaction.", e);
            } finally {
                try { conn.setAutoCommit(originalAutoCommit); } catch (SQLException ignored) {}
            }
        }
    }
}