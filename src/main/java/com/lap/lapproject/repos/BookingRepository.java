package com.lap.lapproject.repos;

import com.lap.lapproject.model.Booking;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingRepository {

    ArrayList<Booking> readAll() throws SQLException;
}
