package com.lap.lapproject.repos;

import com.lap.lapproject.application.BCrypt;
import com.lap.lapproject.controller.QuickAlert;
import com.lap.lapproject.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            preparedStatement.setString(6, hashPassword(user.getUserPassword()));
            preparedStatement.setString(7, user.getAuthority());
            preparedStatement.setString(8, user.getDescription());
            preparedStatement.setString(9, user.getPhoneNmbr());
            preparedStatement.setString(10, user.getEmail());
            InputStream inputStream = new ByteArrayInputStream(user.getPhoto());
            preparedStatement.setBlob(11, inputStream);
            preparedStatement.setBoolean(12, user.getDescriptionVisibility());
            preparedStatement.setBoolean(13, user.getPhoneNmbrVisibility());
            preparedStatement.setBoolean(14, user.getEmailVisibility());
            preparedStatement.setBoolean(15, user.getPhotoVisibility());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                generatedKey = resultSet.getInt(1);
                user.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Passwort haschen
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


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
        }
        return authorizationList;
    }


    @Override
    public List<Trainer> readAllTrainer() {
        Connection connection = connect();
        List<Trainer> trainerList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_TRAINER_SQL_STRING);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Trainer trainer = new Trainer(resultSet.getInt("user_id"), resultSet.getString("username"), resultSet.getString("first_name"),
                        resultSet.getString("last_name"), resultSet.getString("authorization"), resultSet.getString("email"), resultSet.getString(
                        "phone"), resultSet.getString("description"), resultSet.getBoolean("active_status"));
                trainerList.add(trainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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


    private static final String SELECT_USERNAME_PASSWORD_SQL_STRING = " SELECT * FROM users WHERE username=? AND active_status='1'";

    //paswort entschlüßeln
    private boolean checkPass(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }


    @Override
    public boolean checkUser(String username, String pass) {
        Connection connection = connect();
        if (connection == null) {
            System.out.println("Check XAMPP");
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(SELECT_USERNAME_PASSWORD_SQL_STRING);
            preparedStatement.setString(1, username);
            //preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

           if (checkPass(pass, resultSet.getString("password"))){
               //logger.info("pass: {}", "TRUE");
               return true;
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //logger.info("pass:{}", "FALSE");
        return false;
    }




    public static final String SELECT_USERS_SQL_STRING = "SELECT username FROM users";

    @Override
    public void checkUniqueUsername(String username) throws SQLException {
        Connection connection = connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_USERS_SQL_STRING);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String usernameDatabase = resultSet.getString("username");
                if (usernameDatabase.equals(username)) {
                    System.out.println("USERNAME IS SAME");
                    QuickAlert.showError("Benutzername bereits vergeben");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public User loginUser(String username) {
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
        logger.info("We're here 2");
        try {
            statement = connection.prepareStatement(queryUsername);
            resultSet = statement.executeQuery();
            logger.info("We're here 2 before the while");
            while (resultSet.next()) {
                logger.info("We're here 2 after the while");

                int userid = resultSet.getInt("user_id");
                String user = resultSet.getString("username");
                String title = resultSet.getString("title");
                String activeStatus = resultSet.getString("active_status");
                String firstname = resultSet.getString("first_name");
                String lastname = resultSet.getString("last_name");
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

                        Admin admin = new Admin(userid, user, title, Boolean.valueOf(activeStatus), firstname, lastname, authority, description,
                                phoneNmbr, email, photo, descriptionVisibility, phoneNmbrVisibility, emailVisibility, photoVisibility);
                        return admin;

                    case "coach":

                        Trainer trainer = new Trainer(userid, user, title, Boolean.valueOf(activeStatus), firstname, lastname, authority, description,
                                phoneNmbr, email, photo, descriptionVisibility, phoneNmbrVisibility, emailVisibility, photoVisibility);
                        return trainer;
                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
