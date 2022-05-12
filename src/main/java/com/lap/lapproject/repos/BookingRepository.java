package com.lap.lapproject.repos;

import com.lap.lapproject.model.Booking;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface BookingRepository {

    ArrayList<Booking> readAll() throws SQLException;


//    void addBooking(int roomID, int userID, int trainerID, int courseID,
//                    String recurrenceRule, LocalDateTime localDateTimeStart,
//                    LocalDateTime localDateTimeEnd) throws SQLException;

    void addBooking(Booking booking) throws SQLException;


    void deleteBooking(int bookingID) throws SQLException;
}
