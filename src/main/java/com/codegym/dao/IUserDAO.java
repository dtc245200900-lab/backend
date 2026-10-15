package com.codegym.dao;

import com.codegym.model.User;
import java.sql.SQLException;
import java.util.List;

/** Declares user persistence operations. */
public interface IUserDAO {
    /** Returns users matching a country substring, ordered by name ascending. */
    List<User> searchByCountryOrderByName(String country) throws SQLException;
}
