package com.lap.lapproject.repos;

import com.lap.lapproject.model.Room;

import java.sql.SQLException;
import java.util.List;

public interface RoomRepository {

    List<Room> readAll() throws SQLException;

    Room addRoom(Room room) throws SQLException;
    void deleteRoom(Room room) throws SQLException;
    void updateRoom(Room room) throws SQLException;
}
