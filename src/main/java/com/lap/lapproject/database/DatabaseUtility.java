package com.lap.lapproject.database;

import com.lap.lapproject.model.*;
import javafx.scene.control.Alert;
import javafx.util.converter.LocalDateStringConverter;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DatabaseUtility {

    static final String dbPrefix = "jdbc:mariadb:";
    static final String location = "//localhost:3306/room_planning";
    static final String dbUser = "root";
    static final String dbPw = "";

    private static Connection connect() {
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


    public static boolean checkUniqueUsername(String username) {
        Connection connection = connect();
        //String query = "SELECT * FROM `users` WHERE `username`= '" + username + "'\n";
        //PreparedStatement statement = null;

        String query = "{call checkUniqueUsername(?)}";
        ResultSet resultSet = null;
        CallableStatement stmt = null;

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Username already taken");
        try {
            stmt = connection.prepareCall(query);
            stmt.setString(1, username);
            resultSet = stmt.executeQuery();
            //statement = connection.prepareStatement(query);
            //resultSet = statement.executeQuery();
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

    public static ArrayList<Booking> readAll() {
        Connection connection = connect();
        ArrayList<Booking> list = new ArrayList<>();
//"SELECT courses.course_name,rooms.room_number,users.username, booking.datetime_start,booking.datetime_end FROM `booking` " +

        String query = "SELECT * FROM `booking` " +
                " JOIN courses ON booking.course_id=courses.course_id" +
                " JOIN rooms ON booking.room_id=rooms.room_id" +
                " JOIN users ON booking.user_id=users.user_id" +
                " JOIN location ON rooms.location_id=location.location_id;";
        //TODO: join rooms_equipment

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Location location = new Location(resultSet.getString("street"), resultSet.getString("zip"), resultSet.getString("city"));
                //TODO: Equipment von rooms_equipment hinzufügen
                //Equipment equipment = new Equipment();
                Room room = new Room(resultSet.getString("rooms.room_number"), resultSet.getInt("rooms.size"), location);
                Trainer trainer = new Trainer();
                Program program = new Program(resultSet.getString("course_name"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timestart = resultSet.getString("course_start");
                LocalDateTime coursestart = LocalDateTime.parse(timestart, formatter);
                String timeend = resultSet.getString("course_end");
                LocalDateTime courseend = LocalDateTime.parse(timeend, formatter);

                Course course = new Course(resultSet.getString("course_name"), program, coursestart, courseend, resultSet.getInt("group_size"));


                String datetimestart = resultSet.getString("datetime_start");
                LocalDateTime startTime = LocalDateTime.parse(datetimestart, formatter);

                String datetimeend = resultSet.getString("datetime_end");
                LocalDateTime endTime = LocalDateTime.parse(datetimeend, formatter);

                Booking b = new Booking(room, trainer, course, startTime, endTime);
                list.add(b);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static boolean getLocation() {
        Connection connection = connect();
        String query = "SELECT * FROM location";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Location location = new Location(resultSet.getString("street"),
                        resultSet.getString("zip"), resultSet.getString("city"));

                ListModel.locationList.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static boolean getProgram() {
        Connection connection = connect();
        String query = "SELECT * FROM programs";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Program program = new Program(resultSet.getString("name"));
                ListModel.programList.add(program);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public  static boolean getRoom() {
        Connection connection = connect();
        String query = "SELECT room_number,size,street FROM rooms JOIN location ON rooms.location_id = location.location_id";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Location location = new Location(resultSet.getString("street"));
                Room room = new Room(resultSet.getString("room_number"), resultSet.getInt("size"), location);
                ListModel.roomList.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


}
