package com.lap.lapproject.repos;

import com.lap.lapproject.controller.AddBookingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Repository {

    public static final String dbPrefix = "jdbc:mariadb:";
    public static final String location = "//localhost:3306/room_planning";
    public static final String dbUser = "root";
    public static final String dbPw = "";

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
