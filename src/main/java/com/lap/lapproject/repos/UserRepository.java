package com.lap.lapproject.repos;

import com.lap.lapproject.model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository {
    void add(User user) throws SQLException;
    Optional<User> read(long id) throws SQLException;
}
