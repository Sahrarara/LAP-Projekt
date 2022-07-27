package com.lap.lapproject.repos;

import com.lap.lapproject.model.Booking;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Das BookingRepository Interface erstellt die leeren Methoden addBooking(), deleteBooking(), getBookingCountByProgramIdJoinLocationId(), getBookingCountByRoomId(), getBookingCountByTrainerId(), updateBooking(), die ListMethode getBookingsByTimeWindow() und readAll() beide erwarten die Klasse Booking ales Element,
 * diese Methoden müssen in der Klasse die dieses Interface implementiert definiert werden.
 */
public interface BookingRepository {

    ArrayList<Booking> readAll() throws SQLException;

    int addBooking(Booking booking) throws SQLException;

    void deleteBooking(Booking booking) throws SQLException;

    int getBookingCountByProgramIdJoinLocationId(int LocationId) throws SQLException;

    int getBookingCountByRoomId(int roomId) throws SQLException;

    int getBookingCountByTrainerId(int trainerId) throws SQLException;

    void updateBooking(Booking booking) throws SQLException;


    //void updateBooking(int roomid, int trainerid, int userid, int locationid, int courseid, String recurrenceRule, LocalDateTime localDateTime_start, LocalDateTime localDateTime_end) throws SQLException;
}
