package com.lap.lapproject.repos;

import com.lap.lapproject.model.Booking;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface BookingRepository {

    ArrayList<Booking> readAll() throws SQLException;
    void addBooking(Booking booking) throws SQLException;

    void deleteBooking(Booking booking) throws SQLException;
}
