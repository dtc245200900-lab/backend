package com.codegym.dao;

import com.codegym.model.User;
import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    void insertUser(User user) throws SQLException;
    User selectUser(int id) throws SQLException;
    List<User> selectUsers(String country, String sortBy) throws SQLException;
    boolean deleteUser(int id) throws SQLException;
    boolean updateUser(User user) throws SQLException;

    // Các phương thức sử dụng Stored Procedure.
    User getUserById(int id) throws SQLException;
    void insertUserStore(User user) throws SQLException;
}
