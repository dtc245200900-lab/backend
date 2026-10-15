package com.codegym.product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> findAll() {
        String sql = "SELECT id, name, price, quantity FROM products ORDER BY id DESC";
        List<Product> products = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(rs.getInt("id"), rs.getString("name"),
                        rs.getBigDecimal("price"), rs.getInt("quantity")));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Không thể tải danh sách sản phẩm: " + e.getMessage(), e);
        }
    }

    public Product findById(int id) {
        String sql = "SELECT id, name, price, quantity FROM products WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Product(rs.getInt("id"), rs.getString("name"),
                        rs.getBigDecimal("price"), rs.getInt("quantity"));
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Không thể tìm sản phẩm: " + e.getMessage(), e);
        }
    }

    public void insert(Product p) {
        String sql = "INSERT INTO products(name, price, quantity) VALUES(?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            bind(ps, p);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Không thể thêm sản phẩm: " + e.getMessage(), e);
        }
    }

    public boolean update(Product p) {
        String sql = "UPDATE products SET name=?, price=?, quantity=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            bind(ps, p);
            ps.setInt(4, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Không thể cập nhật sản phẩm: " + e.getMessage(), e);
        }
    }

    public boolean delete(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM products WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Không thể xóa sản phẩm: " + e.getMessage(), e);
        }
    }

    private void bind(PreparedStatement ps, Product p) throws SQLException {
        ps.setString(1, p.getName());
        ps.setBigDecimal(2, p.getPrice());
        ps.setInt(3, p.getQuantity());
    }
}
