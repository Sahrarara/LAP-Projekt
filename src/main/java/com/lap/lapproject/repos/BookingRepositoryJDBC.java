package com.lap.lapproject.repos;

import com.lap.lapproject.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BookingRepositoryJDBC extends Repository implements BookingRepository {

    private static final String ADD_NEW_BOOKING_SQL_STRING =
            "INSERT INTO booking(room_id, user_id, trainer_id, course_id, recurrence_rule, datetime_start, datetime_end)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";

    @Override
    public ArrayList<Booking> readAll() throws SQLException {
        Connection connection = connect();
        //ListModel.bookingList.clear();
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


                Location location = new Location(resultSet.getInt("location_id"), resultSet.getString("street"),
                        resultSet.getString("zip"),
                        resultSet.getString("city"));


                //TODO: Equipment von rooms_equipment hinzufügen
                //Equipment equipment = new Equipment();


                Room room = new Room(resultSet.getInt("rooms.room_id"), resultSet.getInt("rooms.room_number"),
                        resultSet.getInt("rooms.size"), location);



                Trainer trainer = new Trainer(resultSet.getString("username"), resultSet.getString("first_name"),
                        resultSet.getString("last_name"), resultSet.getString("authorization"), resultSet.getString("email"),
                        resultSet.getString("phone"), resultSet.getString("description"));



                Program program = new Program(resultSet.getInt("program_id"), resultSet.getString("course_name"));


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String timestart = resultSet.getString("course_start");
                LocalDateTime courseStart = (LocalDateTime.parse(timestart, formatter));

                String timeEnd = resultSet.getString("course_end");
                LocalDateTime courseEnd = (LocalDateTime.parse(timeEnd, formatter));


                Course course = new Course(resultSet.getInt("course_id"),resultSet.getString("course_name"), program,
                        courseStart,
                        courseEnd,
                        resultSet.getInt("group_size"));


                String datetimeStart = resultSet.getString("datetime_start");
                LocalDateTime startTime = LocalDateTime.parse(datetimeStart, formatter);

                String datetimeEnd = resultSet.getString("datetime_end");
                LocalDateTime endTime = LocalDateTime.parse(datetimeEnd, formatter);


                String recurrenceRule = resultSet.getString("recurrence_rule");


                Booking b = new Booking(room, trainer, course, startTime, endTime, recurrenceRule);
                list.add(b);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addBooking(int roomID, int userID, int trainerID, int courseID,
                           String recurrenceRule, LocalDateTime localDateTimeStart, LocalDateTime localDateTimeEnd) {
        Connection connection = connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_BOOKING_SQL_STRING, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, roomID);
            preparedStatement.setInt(2, userID);
            preparedStatement.setInt(3, trainerID);
            preparedStatement.setInt(4, courseID);
            preparedStatement.setString(5, recurrenceRule);
            preparedStatement.setObject(6, localDateTimeStart);
            preparedStatement.setObject(7, localDateTimeEnd);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



}
