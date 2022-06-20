package com.lap.lapproject.repos;

import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.utility.PasswordSecurity;
import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserRepositoryJDBC extends Repository implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryJDBC.class);

    //BEISPIEL??
    private static final String SQL_SELECT_WHERE_ID = "SELECT * WHERE id=?";

    private static final String SELECT_TRAINER_SQL_STRING = "SELECT * FROM users WHERE authorization='coach'";
    private static final String ADD_NEW_USER_SQL_STRING = "INSERT INTO users (username,active_status,title,first_name,last_name,password," +
            " authorization,description,phone,email, photo, description_visable, phone_visable, email_visable," +
            "photo_visable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_AUTHORIZATION_SQL_STRING = "SELECT DISTINCT authorization FROM users";
    //    nicht passender und verwirrender Name, was wird selektiert?  es sollte das passwort selectiert werden dem namen zufolge
    private static final String SELECT_USERNAME_PASSWORD_SQL_STRING = " SELECT * FROM users WHERE username=? AND active_status='1'";
    public static final String SELECT_USERS_SQL_STRING = "SELECT username FROM users";
    private static final String DELETE_USER_SQL_STRING = "DELETE FROM users WHERE user_id=?";
    private static final String UPDATE_USER_SQL_STRING = "UPDATE users SET username=?,active_status=?," +
            "title=?,first_name=?, last_name=?,password=?,authorization=?,description=?,phone=?,email=?,photo=?,description_visable=?,phone_visable=?,email_visable=?," +
            "photo_visable=? WHERE user_id=?";

    private static final String UPDATE_PROFILE_SQL_STRING = "UPDATE users SET description=?, phone=?, email=?, photo=?, photo_visable=? WHERE user_id=?";
    private static final String UPDATE_PASSWORD_SQL_STRING = "UPDATE `users` SET password = ?  WHERE `user_id` = ? ";


    @Override
    public void add(User user) throws SQLException {
        Connection connection = connect();
        ResultSet resultSet = null;
        int generatedKey = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER_SQL_STRING, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setBoolean(2, user.getActiveStatus());
            preparedStatement.setString(3, user.getTitle());
            preparedStatement.setString(4, user.getfName());
            preparedStatement.setString(5, user.getlName());
//            preparedStatement.setString(6, hashPassword(user.getUserPassword()));
            preparedStatement.setString(6, PasswordSecurity.hashPassword(user.getUserPassword()));
            preparedStatement.setString(6, PasswordSecurity.hashPassword(user.getUserPassword()));
            preparedStatement.setString(7, user.getAuthority());
            preparedStatement.setString(8, user.getDescription());
            preparedStatement.setString(9, user.getPhoneNmbr());
            preparedStatement.setString(10, user.getEmail());

            InputStream inputStream = null;
            if (user.getPhoto() != null) {
                inputStream = new ByteArrayInputStream(user.getPhoto());
            }
            preparedStatement.setBlob(11, inputStream);
            preparedStatement.setBoolean(12, user.getDescriptionVisibility());
            preparedStatement.setBoolean(13, user.getPhoneNmbrVisibility());
            preparedStatement.setBoolean(14, user.getEmailVisibility());
            if (user.getPhoto() != null) {
                preparedStatement.setBoolean(15, user.getPhotoVisibility());
            }else {
                QuickAlert.showError("Bitte foto!");
            }
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                generatedKey = resultSet.getInt(1);
                user.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }



    @Override
    public void deleteUser(User user) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(DELETE_USER_SQL_STRING);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }


    @Override
    public void updateUser(User user) throws SQLException{
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER_SQL_STRING);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setBoolean(2, user.getActiveStatus());
            preparedStatement.setString(3, user.getTitle());
            preparedStatement.setString(4, user.getfName());
            preparedStatement.setString(5, user.getlName());
            preparedStatement.setString(6, user.getUserPassword());
            preparedStatement.setString(7, user.getAuthority());
            preparedStatement.setString(8, user.getDescription());
            preparedStatement.setString(9, user.getPhoneNmbr());
            preparedStatement.setString(10, user.getEmail());

            InputStream inputStream = null;
            if (user.getPhoto() != null) {
                inputStream = new ByteArrayInputStream(user.getPhoto());
            }
            preparedStatement.setBlob(11, inputStream);

            preparedStatement.setBoolean(12, user.getDescriptionVisibility());
            preparedStatement.setBoolean(13, user.getPhoneNmbrVisibility());
            preparedStatement.setBoolean(14, user.getEmailVisibility());
            preparedStatement.setBoolean(15, user.getPhotoVisibility());
            preparedStatement.setInt(16, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }
/*if (listModel.getSelectedUser().getPhoto() != null){
                trainer.setPhotoVisibility(photoCheckBox.isSelected());
            } else {
                QuickAlert.showError("Bitte Foto hochladen!!");
            }*/

    @Override
    public List<Trainer> readAuthorization() throws SQLException {
        Connection connection = connect();

        List<Trainer> authorizationList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            preparedStatement = connection.prepareStatement(SELECT_AUTHORIZATION_SQL_STRING);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Trainer authorization = new Trainer(rs.getString("authorization"));
                authorizationList.add(authorization);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        return authorizationList;
    }


    @Override
    public List<Trainer> readAllTrainer() throws SQLException {
        Connection connection = connect();
        List<Trainer> trainerList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_TRAINER_SQL_STRING);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Trainer trainer = new Trainer(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("title"),
                        resultSet.getBoolean("active_status"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("password"),
                        resultSet.getString("authorization"),
                        resultSet.getString("description"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getBytes("photo"),
                        resultSet.getBoolean("description_visable"),
                        resultSet.getBoolean("phone_visable"),
                        resultSet.getBoolean("email_visable"),
                        resultSet.getBoolean("photo_visable")
                );
                trainerList.add(trainer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        return trainerList;
    }


    //BEISPIEL??
    @Override
    public Optional<User> read(long id) throws SQLException {
        User user = null;
        try (PreparedStatement preparedStatement = connect().prepareStatement(SQL_SELECT_WHERE_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                getUser(resultSet);
            }
        }
        return Optional.of(user);
    }


    //BEISPIEL??
    private User getUser(ResultSet resultSet) throws SQLException {
        String authorization = resultSet.getString("authorization");
        User user = null;
        switch (authorization) {
            //TODO: Constructor
            case "admin":
                //user = new Admin();
            case "coach":
                //user = new Trainer();
        }
        return user;
    }



    @Override
    public boolean checkUser(String username, String pass) throws SQLException {
        Connection connection = connect();
        if (connection == null) {
            System.out.println("Check XAMPP");
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(SELECT_USERNAME_PASSWORD_SQL_STRING);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            if (PasswordSecurity.checkPass(pass, resultSet.getString("password"))) {
                logger.info("pass: {}", "TRUE");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        logger.info("pass:{}", "FALSE");

        JOptionPane.showMessageDialog(null, "Benutzername und/oder Passwort falsch!",
                "Warnung", JOptionPane.ERROR_MESSAGE, null);

        return false;
    }



    @Override
    public boolean checkUniqueUsername(String username) throws SQLException {
        Connection connection = connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;


        try {
            statement = connection.prepareStatement(SELECT_USERS_SQL_STRING);
            resultSet = statement.executeQuery();
            ArrayList<String> usernamesFromDB = new ArrayList<>();

            while (resultSet.next()) {
                usernamesFromDB.add(resultSet.getString("username"));
                // logger.info("usernamesfromdb: {}", resultSet.getString("username"));
            }

            for (String item : usernamesFromDB) {
                if (item.equals(username)) {
                    logger.info("neue username gibt schon in db");
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        logger.info("neue username gibt es nicht in db");
        return true;
    }


    public User loginUser(String username) throws SQLException {
        Connection connection = connect();
        if (connection == null) {
            System.out.println("Check XAMPP");
        }
        String queryUsername =
                "SELECT * from users WHERE " +
                        "username = '" + username + "'";

        PreparedStatement statement = null;

        //String query = "{call checkUsernamePasswordActive(?,?)}";
        ResultSet resultSet = null;
        //CallableStatement stmt = null;
        try {
            statement = connection.prepareStatement(queryUsername);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int userid = resultSet.getInt("user_id");
                String user = resultSet.getString("username");
                String title = resultSet.getString("title");
                String activeStatus = resultSet.getString("active_status");
                String firstname = resultSet.getString("first_name");
                String lastname = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                String authority = resultSet.getString("authorization");
                String description = resultSet.getString("description");
                String phoneNmbr = resultSet.getString("phone");
                String email = resultSet.getString("email");
                byte[] photo = resultSet.getBytes("photo");
                Boolean descriptionVisibility = resultSet.getBoolean("description_visable");
                Boolean phoneNmbrVisibility = resultSet.getBoolean("phone_visable");
                Boolean emailVisibility = resultSet.getBoolean("email_visable");
                Boolean photoVisibility = resultSet.getBoolean("photo_visable");

                switch (authority) {

                    case "admin":

                        Admin admin = new Admin(userid, user, title, Boolean.valueOf(activeStatus), firstname,
                                lastname, password, authority, description,
                                phoneNmbr, email, photo, descriptionVisibility, phoneNmbrVisibility, emailVisibility, photoVisibility);
                        return admin;

                    case "coach":

                        Trainer trainer = new Trainer(userid, user, title, Boolean.valueOf(activeStatus), firstname,
                                lastname, password, authority, description,
                                phoneNmbr, email, photo, descriptionVisibility, phoneNmbrVisibility, emailVisibility, photoVisibility);
                        return trainer;
                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        return null;
    }


    @Override
    public void updateUserProfile(User user) throws SQLException {

        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PROFILE_SQL_STRING);

            preparedStatement.setString(1, user.getDescription());
            preparedStatement.setString(2, user.getPhoneNmbr());
            preparedStatement.setString(3, user.getEmail());

            InputStream inputStream = null;
            if (user.getPhoto() != null) {
                inputStream = new ByteArrayInputStream(user.getPhoto());

            }
            preparedStatement.setBlob(4, inputStream);
            preparedStatement.setBoolean(5, user.getPhotoVisibility());
            preparedStatement.setInt(6, user.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }


    public void updatePassword(String newHashPassword, int userID) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_SQL_STRING);
            preparedStatement.setString(1, newHashPassword);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

            logger.info("password updated");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }



}
