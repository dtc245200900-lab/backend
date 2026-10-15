package com.codegym.dao;

import com.codegym.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "password"; // Sửa theo mật khẩu MySQL của bạn.

    private Connection getConnection() throws SQLException {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { throw new SQLException("Không tìm thấy MySQL JDBC Driver", e); }
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    @Override
    public List<User> selectAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try(Connection c=getConnection(); PreparedStatement ps=c.prepareStatement("SELECT id,name,email,country FROM users ORDER BY id DESC"); ResultSet rs=ps.executeQuery()) {
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
        try(Connection c=getConnection(); PreparedStatement ps=c.prepareStatement("DELETE FROM users WHERE id=?")) {
            ps.setInt(1,id); ps.executeUpdate();
        }
    }

    @Override
    public void addUserTransaction(User user, int[] permissionIds) throws SQLException {
        String insertUserSql = "INSERT INTO users(name,email,country) VALUES(?,?,?)";
        String insertPermissionSql = "INSERT INTO user_permission(user_id,permission_id) VALUES(?,?)";

        try(Connection conn=getConnection()) {
            boolean oldAutoCommit = conn.getAutoCommit();
            try {
                conn.setAutoCommit(false);
                int userId;
                try(PreparedStatement ps=conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1,user.getName());
                    ps.setString(2,user.getEmail());
                    ps.setString(3,user.getCountry());
                    if(ps.executeUpdate()!=1) throw new SQLException("Không thể thêm User.");
                    try(ResultSet keys=ps.getGeneratedKeys()) {
                        if(!keys.next()) throw new SQLException("Không lấy được ID User mới.");
                        userId=keys.getInt(1);
                    }
                }

                if(permissionIds!=null && permissionIds.length>0) {
                    try(PreparedStatement ps=conn.prepareStatement(insertPermissionSql)) {
                        for(int permissionId:permissionIds) {
                            ps.setInt(1,userId);
                            ps.setInt(2,permissionId);
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                }

                // COMMIT chỉ khi thêm User và toàn bộ quyền đều thành công.
                conn.commit();
                System.out.println("Transaction commit thành công.");
            } catch(SQLException | RuntimeException e) {
                try {
                    conn.rollback();
                    System.err.println("Transaction đã rollback do có lỗi.");
                } catch(SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                if(e instanceof SQLException) throw (SQLException)e;
                throw new SQLException("Lỗi khi thêm User và phân quyền.",e);
            } finally {
                try { conn.setAutoCommit(oldAutoCommit); } catch(SQLException ignored) {}
            }
        }
    }
}