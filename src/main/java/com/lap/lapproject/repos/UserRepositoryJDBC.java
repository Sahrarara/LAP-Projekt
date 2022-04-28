package com.lap.lapproject.repos;

import com.lap.lapproject.model.Admin;
import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.model.User;
import com.lap.lapproject.model.UserData;

import java.sql.*;
import java.util.Optional;

public class UserRepositoryJDBC extends Repository implements UserRepository {

    private static final String SQL_STRING = "INSERT INTO users (user_id, username) (?, ?)";

    @Override
    public void add(User user) throws SQLException{
        Connection connection = connect();

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_STRING)){

        }
    }

    @Override
    public Optional<User> read(long id) throws SQLException{
        return Optional.empty();
    }

    public static boolean checkUsernamePasswordActiveStatus(String username, String password) {
        Connection connection = connect();
        if (connection == null) {
            System.out.println("Check XAMPP");
        }
        //String queryUsername =
        //        "SELECT * from users WHERE " +
        //                "username = '" + username + "' AND " +
        //                "password = '" + password + "' AND " +
        //                "active_status = '1'";
        //PreparedStatement statement = null;

        String query = "{call checkUsernamePasswordActive(?,?)}";
        ResultSet resultSet = null;
        CallableStatement stmt = null;

        try {

            stmt = connection.prepareCall(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            resultSet = stmt.executeQuery();

            //statement = connection.prepareStatement(queryUsername);
            //resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String user = resultSet.getString("username");
                String title = resultSet.getString("title");
                String firstname = resultSet.getString("first_name");
                String lastname = resultSet.getString("last_name");
                String authority = resultSet.getString("authorization");
                Boolean adminAuth = resultSet.getBoolean("admin");
                String description = resultSet.getString("description");
                int phoneNmbr = resultSet.getInt("phone");
                String email = resultSet.getString("email");
                byte[] photo = resultSet.getBytes("photo");
                Boolean descriptionVisibility = resultSet.getBoolean("description_visable");
                Boolean phoneNmbrVisibility = resultSet.getBoolean("phone_visable");
                Boolean emailVisibility = resultSet.getBoolean("email_visable");
                Boolean photoVisibility = resultSet.getBoolean("photo_visable");


                switch (authority) {
                    case "admin":
                        Admin admin = new Admin(user, firstname, lastname, authority, adminAuth, email, phoneNmbr, description);
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
                        Trainer trainer = new Trainer(user, firstname, lastname, authority, adminAuth, email, phoneNmbr, description);
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


}
