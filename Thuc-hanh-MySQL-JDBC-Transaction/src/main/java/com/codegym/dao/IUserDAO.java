package com.codegym.dao;

import com.codegym.model.User;
import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    List<User> selectAllUsers() throws SQLException;
    User selectUser(int id) throws SQLException;
    void insertUserTransaction(User user, int[] permissionIds) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(int id) throws SQLException;
}