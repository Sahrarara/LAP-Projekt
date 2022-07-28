package com.lap.lapproject.repos;

import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Das UserRepository UserRepository erstellt die leeren Methoden add(), deleteUser(), updateUser(), updateUserProfile(), checkUniqueEmailAdresse(), checkUniqueUsername(), checkUser(), updatePassword(), die ListMethoden readAllTrainer() und readAuthorization() welche die Klasse Trainer erwarten
 * und die ObjectMethode read() welche einen user erwartet doch sie wird derzeit nicht genutzt
 * diese Methoden müssen in der Klasse die dieses Interface implementiert definiert werden.
 */
public interface UserRepository {
    //BEISPIEL??
    Optional<User> read(long id) throws SQLException;

    List<Trainer> readAllTrainer() throws SQLException;

    List<Trainer> readAuthorization() throws SQLException;

    void add(User user) throws SQLException;

    void deleteUser(User user) throws SQLException;

    void updateUser(User user) throws SQLException;

    void updateUserProfile(User user) throws SQLException;

    boolean checkUniqueEmailAdresse(String emailAdresse);

    boolean checkUniqueUsername(String username) throws SQLException;

    boolean checkUser(String username, String pass) throws SQLException;

    void updatePassword(String newHashPassword, int userID) throws SQLException;
}
