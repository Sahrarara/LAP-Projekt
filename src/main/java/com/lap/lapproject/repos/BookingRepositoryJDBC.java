package com.lap.lapproject.repos;

import com.lap.lapproject.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BookingRepositoryJDBC extends Repository implements BookingRepository {

    private static final Logger logger = LoggerFactory.getLogger(BookingRepository.class);

    private static final String SELECT_BOOKING_SQL_STRING = "SELECT * FROM `booking` " +
            " JOIN courses ON booking.course_id=courses.course_id" +
            " JOIN rooms ON booking.room_id=rooms.room_id" +
            " JOIN users ON booking.user_id=users.user_id" +
            " JOIN location ON rooms.location_id=location.location_id;";
    //TODO: join rooms_equipment

    private static final String ADD_NEW_BOOKING_SQL_STRING =
            "INSERT INTO booking(room_id, user_id, trainer_id, course_id, recurrence_rule, datetime_start, datetime_end)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_BOOKING_SQL_STRING = "DELETE FROM booking WHERE booking_id=?";

    @Override
    public ArrayList<Booking> readAll() throws SQLException {
        Connection connection = connect();
        ArrayList<Booking> list = new ArrayList<>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            assert connection != null;
            statement = connection.prepareStatement(SELECT_BOOKING_SQL_STRING);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int bookingID = resultSet.getInt("booking_id");
                logger.info("BookingID: {}", bookingID);

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

//                Booking b = new Booking(room, trainer, course, startTime, endTime, recurrenceRule);
//                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



    public void addBooking(Booking booking) {
        Connection connection = connect();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_BOOKING_SQL_STRING, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, booking.getRoom().getId());
            preparedStatement.setInt(2, booking.getUser().getId());
            preparedStatement.setInt(3, booking.getTrainer().getId());
            preparedStatement.setInt(4, booking.getCourse().getId());
            preparedStatement.setString(5, booking.getRecurrenceRule());
            preparedStatement.setObject(6, booking.getDateTimeStart());
            preparedStatement.setObject(7, booking.getDateTimeEnd());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void deleteBooking(Booking booking) { //Booking booking
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_BOOKING_SQL_STRING);
//            preparedStatement.setInt(1, );
//            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
