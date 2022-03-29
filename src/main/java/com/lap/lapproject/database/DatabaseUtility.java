package com.lap.lapproject.database;

import com.lap.lapproject.model.UserModel;
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
            System.out.println("Connection failed");
            e.printStackTrace();
            return null;
        }
        return connection;
    }


    public static boolean checkUniqueUsername(String username){
        Connection connection = connect();
        String query = "SELECT * FROM `users` WHERE `username`= '" + username + "'\n";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Username already taken");
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
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
        String queryUsername =
                "SELECT * from users WHERE " +
                        "username = '" + username + "' AND " +
                        "password = '" + password + "' AND " +
                        "active_status = '1'";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(queryUsername);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                UserModel.username = resultSet.getString("username");
                UserModel.userid = resultSet.getString("id");
                UserModel.fName = resultSet.getString("first_name");
                UserModel.lName = resultSet.getString("last_name");
                UserModel.authority = resultSet.getString("authorization");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



}
