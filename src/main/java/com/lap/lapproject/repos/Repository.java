package com.lap.lapproject.repos;

import com.lap.lapproject.controller.AddBookingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Die Klasse Repository ist für die Verbindung zur Datenbank zuständig.
 * es wird das Prefix der DB, die Location im Localhost(der name der angelegten DB), DBUser und DBPasswort angegeben.
 */
public class Repository {

    public static final String dbPrefix = "jdbc:mariadb:";
    public static final String location = "//localhost:3306/room_planning";
    public static final String dbUser = "root";
    public static final String dbPw = "";

    protected static Logger logger;

    /**
     * Stellt eine Verbindung zur Datenbank mit einer try...Catch abfrage her, damit ein Error aufgefangen werden kann
     *
     * @return
     */
    protected static Connection connect() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location, dbUser, dbPw);
            logger.info("Connection successful");
//            System.out.println("Connection successful");
        } catch (SQLException e) {
            logger.error("Connection failed - Check XAMPP");
//            System.out.println("Connection failed - Check XAMPP");
            //e.printStackTrace();
            return null;
        }
        return connection;
    }
}
