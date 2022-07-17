package com.lap.lapproject.repos;

import com.lap.lapproject.model.Booking;
import com.lap.lapproject.model.Room;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Das BookingRepository Interface erstellt Methoden welche SQLExceptions werfen können, welche in der Klasse BookingRepositoryJDBC benötigt werden.
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

    //Negin........................................................
    List<Room> findFreeRoomsByTime(LocalDateTime startTime, LocalDateTime endTime);

    Boolean  isRoomFree(int roomId, LocalDateTime statTime, LocalDateTime endTime);

}
