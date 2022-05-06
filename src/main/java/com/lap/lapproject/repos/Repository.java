package com.lap.lapproject.repos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Repository {
    static final String dbPrefix = "jdbc:mariadb:";
    static final String location = "//localhost:3306/room_planning";
    static final String dbUser = "root";
    static final String dbPw = "";

    protected static Connection connect() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location, dbUser, dbPw);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("Connection failed - Check XAMPP");
            //e.printStackTrace();
            return null;
        }
        return connection;
    }
}
