package com.lap.lapproject.repos;

import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.model.Room;

import java.sql.SQLException;
import java.util.List;

public interface RoomRepository {

    List<Room> readAll() throws SQLException;

    void addRoomEquipment(Room room) throws SQLException;

    void deleteRoom(Room room) throws SQLException;

    void updateRoom(Room room) throws SQLException;

    void deleteEquipment(Room room, Equipment equipment) throws SQLException;

    void addEquipment(Room room, Equipment equipment) throws SQLException;
    int getRoomCountByRoomNumber(int roomNumber) throws SQLException;
}
