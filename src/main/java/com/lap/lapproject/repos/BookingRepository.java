package com.lap.lapproject.repos;

import com.lap.lapproject.model.Booking;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Das BookingRepository Interface erstellt leere Methoden welche zum Teil schon im vorhinein Warnen das sie SQLExceptions werfen können, diese Methoden werden hauptsächlich in der Klasse BookingRepositoryJDBC gefunden.
 */
public interface BookingRepository {

    ArrayList<Booking> readAll() throws SQLException;
    int addBooking(Booking booking) throws SQLException;

    void deleteBooking(Booking booking) throws SQLException;

    int getBookingCountByProgramIdJoinLocationId(int LocationId) throws SQLException;

    int getBookingCountByRoomId(int roomId) throws SQLException;

    int getBookingCountByTrainerId(int trainerId) throws SQLException;

    void updateBooking(Booking booking) throws SQLException;

    List<Booking> getBookingsByTimeWindow(LocalDateTime startSearchTime, LocalDateTime endSearchTime);

    //void updateBooking(int roomid, int trainerid, int userid, int locationid, int courseid, String recurrenceRule, LocalDateTime localDateTime_start, LocalDateTime localDateTime_end) throws SQLException;
}
