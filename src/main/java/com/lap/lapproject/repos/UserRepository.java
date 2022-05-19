package com.lap.lapproject.repos;

import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void add(User user) throws SQLException;
    //BEISPIEL??
    Optional<User> read(long id) throws SQLException;

    List<Trainer> readAllTrainer() throws SQLException;

    List<Trainer> readAuthorization() throws SQLException;

    boolean checkUniqueUsername(String username) throws SQLException;

    boolean checkUser(String username, String pass) throws SQLException;

    void deleteUser(User user) throws SQLException;

    void updateUser(User user) throws SQLException;

}
