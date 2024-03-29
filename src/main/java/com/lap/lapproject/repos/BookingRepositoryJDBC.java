package com.lap.lapproject.repos;

import com.lap.lapproject.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingRepositoryJDBC extends Repository implements BookingRepository {

    static {
        logger = LoggerFactory.getLogger(BookingRepository.class);
    }

    private static final String SELECT_BOOKING_SQL_STRING = "SELECT * FROM `booking` " +
            " JOIN courses ON booking.course_id=courses.course_id" +
            " JOIN rooms ON booking.room_id=rooms.room_id" +
            " JOIN users ON booking.trainer_id=users.user_id" +
            " JOIN location ON rooms.location_id=location.location_id" +
            " JOIN programs ON courses.program_id=programs.program_id";

    private static final String ADD_NEW_BOOKING_SQL_STRING =
            "INSERT INTO booking(room_id, user_id, trainer_id, course_id, recurrence_rule, datetime_start, datetime_end)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_BOOKING_SQL_STRING = "DELETE FROM booking WHERE booking_id = ?";

    private static final String UPDATE_BOOKING_SQL_STRING =
            "UPDATE booking SET room_id=?, user_id=?, trainer_id=?, course_id=?," +
                    "recurrence_rule=?, datetime_start=?, datetime_end=? WHERE booking_id=?";

    private static final String GET_BOOKINGS_COUNT_BY_PROGRAM_ID_JOIN_LOCATION_ID_SQL_STRING = "SELECT COUNT(*) AS booking_count_by_location FROM booking JOIN rooms ON booking.room_id=rooms.room_id JOIN location ON location.location_id=rooms.location_id WHERE rooms.location_id =(?) ";
    private static final String GET_BOOKINGS_COUNT_BY_ROOM_ID_SQL_STRING = "SELECT COUNT(*) AS rooms_count FROM booking WHERE room_id = (?) ";
    private static final String GET_BOOKINGS_COUNT_BY_TRAINER_ID_SQL_STRING = "SELECT COUNT(*) AS trainer_count FROM booking WHERE trainer_id = (?) ";

    @Override
    public ArrayList<Booking> readAll() {
        Connection connection = connect();
        ArrayList<Booking> bookingList = new ArrayList<>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            assert connection != null;
            statement = connection.prepareStatement(SELECT_BOOKING_SQL_STRING);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
//                int booker = resultSet.getInt("user_id");

                Location location = new Location(
                        resultSet.getInt("location_id"),
                        resultSet.getString("street"),
                        resultSet.getString("zip"),
                        resultSet.getString("city"));

                //TODO: Equipment von rooms_equipment hinzufügen
                //Equipment equipment = new Equipment();

                Room room = new Room(
                        resultSet.getInt("rooms.room_id"),
                        resultSet.getInt("rooms.room_number"),
                        resultSet.getInt("rooms.size"),
                        location);

                Trainer trainer = new Trainer(
                        resultSet.getInt("trainer_id"),
                        resultSet.getString("username"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("authorization"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("active_status"));

                User user = new Trainer(resultSet.getInt("user_id"));

                Program program = new Program(
                        resultSet.getInt("program_id"),
                        resultSet.getString("name"));


                LocalDate courseStart = resultSet.getDate("course_start").toLocalDate();
                LocalDate courseEnd = resultSet.getDate("course_end").toLocalDate();


                Course course = new Course(
                        resultSet.getInt("course_id"),
                        resultSet.getString("course_name"),
                        program,
                        courseStart,
                        courseEnd,
                        resultSet.getInt("group_size"));

                LocalDateTime startTime = resultSet.getTimestamp("datetime_start").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("datetime_end").toLocalDateTime();


                String recurrenceRuleFrequency = resultSet.getString("recurrence_rule");
                String recurrenceRule = convertRecurrenceRuleFromFrequencyToText(recurrenceRuleFrequency);

                Booking booking = new Booking(
                        resultSet.getInt("booking_id"),
                        room, trainer, user, course, startTime, endTime, recurrenceRule);
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookingList;
    }


    public int addBooking(Booking booking) throws SQLException {
        Connection connection = connect();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int generatedKey = 0;

        try {
            preparedStatement = connection.prepareStatement(ADD_NEW_BOOKING_SQL_STRING, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, booking.getRoom().getId());
            preparedStatement.setInt(2, booking.getUser().getId());
            preparedStatement.setInt(3, booking.getTrainer().getId());
            preparedStatement.setInt(4, booking.getCourse().getId());

            String recurrenceRuleFrequency = convertRecurrenceRuleFromTextToFrequency(booking.getRecurrenceRule());

            preparedStatement.setString(5, recurrenceRuleFrequency);
            preparedStatement.setObject(6, booking.getDateTimeStart());
            preparedStatement.setObject(7, booking.getDateTimeEnd());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                generatedKey = resultSet.getInt(1);
                booking.setId(generatedKey);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        return generatedKey;
    }


    public void deleteBooking(Booking booking) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_BOOKING_SQL_STRING);
            preparedStatement.setInt(1, booking.getId());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }


    @Override
    public void updateBooking(Booking booking) throws SQLException {

        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_BOOKING_SQL_STRING);
            preparedStatement.setInt(1, booking.getRoom().getId());
            preparedStatement.setInt(2, booking.getUser().getId());
            preparedStatement.setInt(3, booking.getTrainer().getId());
            preparedStatement.setInt(4, booking.getCourse().getId());

            String recurrenceRule = convertRecurrenceRuleFromTextToFrequency(booking.getRecurrenceRule());

            preparedStatement.setString(5, recurrenceRule);
            preparedStatement.setObject(6, booking.getDateTimeStart());
            preparedStatement.setObject(7, booking.getDateTimeEnd());
            preparedStatement.setInt(8, booking.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }


    public String convertRecurrenceRuleFromTextToFrequency(String recurrenceRuleText) {
        String recurrenceRule;
        switch (recurrenceRuleText) {
            case "Täglich":
                recurrenceRule = "RRULE:FREQ=DAILY";
                break;
            case "Wöchentlich":
                recurrenceRule = "RRULE:FREQ=WEEKLY";
                break;
            case "Monatlich":
                recurrenceRule = "RRULE:FREQ=MONTHLY";
                break;
            default:
                recurrenceRule = "Einzeltermin";
                break;
        }
        return recurrenceRule;
    }


    public static String convertRecurrenceRuleFromFrequencyToText(String recurrenceRuleFrequency) {
        String recurrenceRule;
        switch (recurrenceRuleFrequency) {
            case "RRULE:FREQ=DAILY":
                recurrenceRule = "Täglich";
                break;
            case "RRULE:FREQ=WEEKLY":
                recurrenceRule = "Wöchentlich";
                break;
            case "RRULE:FREQ=MONTHLY":
                recurrenceRule = "Monatlich";
                break;
            default:
                recurrenceRule = "Einzeltermin";
        }
        return recurrenceRule;
    }


    /**
     * @param locationId
     * @return
     * @throws SQLException
     */
    @Override
    public int getBookingCountByProgramIdJoinLocationId(int locationId) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int roomsCountByLocationId = 0;
        try {
            preparedStatement = connection.prepareStatement(GET_BOOKINGS_COUNT_BY_PROGRAM_ID_JOIN_LOCATION_ID_SQL_STRING);
            preparedStatement.setInt(1, locationId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                roomsCountByLocationId = resultSet.getInt("booking_count_by_location");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        return roomsCountByLocationId;
    }


    @Override
    public int getBookingCountByRoomId(int roomId) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int bookingsCountByRoomId = 0;
        try {
            preparedStatement = connection.prepareStatement(GET_BOOKINGS_COUNT_BY_ROOM_ID_SQL_STRING);
            preparedStatement.setInt(1, roomId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookingsCountByRoomId = resultSet.getInt("rooms_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        return bookingsCountByRoomId;
    }


    @Override
    public int getBookingCountByTrainerId(int trainerId) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int bookingsCountByTrainerId = 0;
        try {
            preparedStatement = connection.prepareStatement(GET_BOOKINGS_COUNT_BY_TRAINER_ID_SQL_STRING);
            preparedStatement.setInt(1, trainerId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookingsCountByTrainerId = resultSet.getInt("trainer_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        return bookingsCountByTrainerId;
    }


}
