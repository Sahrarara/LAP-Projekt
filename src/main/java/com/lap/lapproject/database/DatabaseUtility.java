package com.lap.lapproject.database;

import com.lap.lapproject.model.Admin;
import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.model.UserData;
import javafx.scene.control.Alert;

import java.sql.*;

public class DatabaseUtility {

    static final String dbPrefix = "jdbc:mariadb:";
    static final String location = "//localhost:3306/room_planning";
    static final String dbUser = "root";
    static final String dbPw = "";

    private static Connection connect (){
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix+location, dbUser, dbPw);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("Connection failed - Check XAMPP");
            //e.printStackTrace();
            return null;
        }
        return connection;
    }


    public static boolean checkUniqueUsername(String username){
        Connection connection = connect();
        //String query = "SELECT * FROM `users` WHERE `username`= '" + username + "'\n";
        //PreparedStatement statement = null;

        String query = "{call checkUniqueUsername(?)}";
        ResultSet resultSet = null;
        CallableStatement stmt = null;

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Username already taken");
        try {
            stmt=connection.prepareCall(query);
            stmt.setString(1,username);
            resultSet = stmt.executeQuery();
            //statement = connection.prepareStatement(query);
            //resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString("username"));
                if (resultSet.getString("username").equals(username))
                    a.show();
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


    public static boolean checkUsernamePasswordActiveStatus(String username, String password){
        Connection connection = connect();
        if (connection == null){
            System.out.println("Check XAMPP");
        }
        //String queryUsername =
        //        "SELECT * from users WHERE " +
        //                "username = '" + username + "' AND " +
        //                "password = '" + password + "' AND " +
        //                "active_status = '1'";
        //PreparedStatement statement = null;

        String query= "{call checkUsernamePasswordActive(?,?)}";
        ResultSet resultSet = null;
        CallableStatement stmt = null;

        try {

            stmt=connection.prepareCall(query);
            stmt.setString(1,username);
            stmt.setString(2,password);
            resultSet = stmt.executeQuery();

            //statement = connection.prepareStatement(queryUsername);
            //resultSet = statement.executeQuery();
            while (resultSet.next()){
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


                switch (authority){
                    case "admin":
                        Admin admin = new Admin(user, firstname, lastname, authority, adminAuth, email, phoneNmbr, description);
                        UserData.firstName = admin.getfName();
                        UserData.lastName = admin.getlName();
                        UserData.email = email;
                        UserData.telephoneNmbr = phoneNmbr;
                        UserData.description = description;
                        System.out.println(email);
                        System.out.println(admin.getEmail());
                        System.out.println(UserData.email);
                        return true;
                    case "coach":
                        Trainer trainer = new Trainer(user, firstname, lastname, authority, adminAuth, email, phoneNmbr, description);
                        UserData.firstName = trainer.getfName();
                        UserData.lastName = trainer.getlName();
                        UserData.email = email;
                        UserData.telephoneNmbr = phoneNmbr;
                        UserData.description = description;
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
