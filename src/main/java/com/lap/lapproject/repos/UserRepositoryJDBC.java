package com.lap.lapproject.repos;

import com.lap.lapproject.model.*;
import javafx.scene.control.Alert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.*;
import java.util.Optional;

public class UserRepositoryJDBC extends Repository implements UserRepository {

    private static final String SQL_STRING = "INSERT INTO users (user_id, username, photo) (?, ?, ?)";
    private static final String SQL_SELECT_WHERE_ID = "SELECT * WHERE id=?";

    private static final String SELECT_TRAINER_SQL_STRING = "SELECT user_id, first_name, last_name, email, phone, active_status FROM users WHERE authorization='coach'";

    @Override
    public void add(User user) throws SQLException {
        Connection connection = connect();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_STRING, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            InputStream inputStream = new ByteArrayInputStream(user.getPhoto());
            preparedStatement.setBlob(3, inputStream);

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    long userId = resultSet.getLong("user_id");
                    user.setId(userId);
                }
            }
        }
    }

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


    @Override
    public boolean getTrainer() {
        Connection connection = connect();
        ListModel.trainerList.clear();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_TRAINER_SQL_STRING);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Trainer trainer = new Trainer(resultSet.getLong("user_id"), resultSet.getString("first_name"),
                        resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString(
                        "phone"), resultSet.getBoolean("active_status"));
                ListModel.trainerList.add(trainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    private User getUser(ResultSet resultSet) throws SQLException {
        String authorization = resultSet.getString("authorization");
        User user = null;
        switch (authorization) {
            //TODO: Constructor
            case "admin":
                user = new Admin();
            case "coach":
                user = new Trainer();
        }
        return user;
    }

    public static boolean checkUsernamePasswordActiveStatus(String username, String password) {
        Connection connection = connect();
        if (connection == null) {
            System.out.println("Check XAMPP");
        }
        String queryUsername =
                "SELECT * from users WHERE " +
                        "username = '" + username + "' AND " +
                        "password = '" + password + "' AND " +
                        "active_status = '1'";
        PreparedStatement statement = null;

        //String query = "{call checkUsernamePasswordActive(?,?)}";
        ResultSet resultSet = null;
        //CallableStatement stmt = null;

        try {

            //stmt = connection.prepareCall(queryUsername);
            //stmt.setString(1, username);
            //stmt.setString(2, password);
            //resultSet = stmt.executeQuery();

            statement = connection.prepareStatement(queryUsername);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String user = resultSet.getString("username");
                String title = resultSet.getString("title");
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
                        Admin admin = new Admin(user, firstname, lastname, authority, email, phoneNmbr, description);
                        UserData.username = user;
                        System.out.println(UserData.username);
                        UserData.firstName = admin.getfName();
                        UserData.lastName = admin.getlName();
                        UserData.email = email;
                        UserData.telephoneNmbr = phoneNmbr;
                        UserData.description = description;
                        UserData.authority = authority;
                        System.out.println(authority);
                        System.out.println(admin.getAuthority());
                        System.out.println(UserData.authority);
                        return true;
                    case "coach":
                        Trainer trainer = new Trainer(user, firstname, lastname, authority, email, phoneNmbr, description);
                        UserData.firstName = trainer.getfName();
                        UserData.lastName = trainer.getlName();
                        UserData.email = email;
                        UserData.telephoneNmbr = phoneNmbr;
                        UserData.description = description;
                        UserData.authority = authority;
                        System.out.println(authority);
                        System.out.println(trainer.getAuthority());
                        System.out.println(UserData.authority);
                        return true;
                    default:
                        return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkUniqueUsername(String username) {
        Connection connection = connect();
        String query = "SELECT * FROM `users` WHERE `username`= '" + username + "'\n";
        PreparedStatement statement = null;

        //String query = "{call checkUniqueUsername(?)}";
        ResultSet resultSet = null;
        //CallableStatement stmt = null;

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Username already taken");
        try {
            //stmt = connection.prepareCall(query);
            //stmt.setString(1, username);
            //resultSet = stmt.executeQuery();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                if (resultSet.getString("username").equals(username))
                    a.show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}
