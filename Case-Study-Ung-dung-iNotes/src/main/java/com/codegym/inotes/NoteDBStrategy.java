package com.codegym.inotes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDBStrategy implements NoteStrategy {
    @Override
    public Note save(Note note) {
        String sql = note.getId() > 0
                ? "UPDATE notes SET title=?, content=?, type_id=? WHERE id=?"
                : "INSERT INTO notes(title, content, type_id) VALUES(?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setInt(3, note.getTypeId());
            if (note.getId() > 0) ps.setInt(4, note.getId());
            int changed = ps.executeUpdate();
            if (changed == 0) throw new IllegalArgumentException("Không tìm thấy ghi chú để cập nhật.");
            if (note.getId() == 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) note.setId(rs.getInt(1));
                }
            }
            return note;
        } catch (SQLException e) {
            throw new RuntimeException("Không thể lưu ghi chú: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM notes WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Không thể xóa ghi chú: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Note> findAll() {
        return queryNotes("SELECT id,title,content,type_id FROM notes ORDER BY id DESC", null);
    }

    @Override
    public List<Note> search(String keyword) {
        String q = keyword == null ? "" : keyword.trim();
        return queryNotes("SELECT id,title,content,type_id FROM notes WHERE title LIKE ? OR content LIKE ? ORDER BY id DESC",
                "%" + q + "%");
    }

    private List<Note> queryNotes(String sql, String keyword) {
        List<Note> notes = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (keyword != null) {
                ps.setString(1, keyword);
                ps.setString(2, keyword);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) notes.add(new Note(rs.getInt("id"), rs.getString("title"),
                        rs.getString("content"), rs.getInt("type_id")));
            }
            return notes;
        } catch (SQLException e) {
            throw new RuntimeException("Không thể đọc ghi chú: " + e.getMessage(), e);
        }
    }

    @Override
    public List<NoteType> findAllTypes() {
        List<NoteType> types = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT id,name,description FROM note_type ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) types.add(new NoteType(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
            return types;
        } catch (SQLException e) {
            throw new RuntimeException("Không thể đọc danh mục: " + e.getMessage(), e);
        }
    }

    @Override
    public Note findById(int id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT id,title,content,type_id FROM notes WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Note(rs.getInt("id"), rs.getString("title"),
                        rs.getString("content"), rs.getInt("type_id"));
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Không thể tìm ghi chú: " + e.getMessage(), e);
        }
    }
}
