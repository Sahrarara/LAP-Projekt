package com.lap.lapproject.repos;

import com.lap.lapproject.model.Booking;
import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.model.Room;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Das RoomRepository Interface erstellt die leeren Methoden addRoomEquipment(), deleteRoom(), deleteProgram(), updateRoom(), deleteEquipment(), getRoomCountByRoomNumber und die ListMethode readAll(),
 * diese Methoden müssen in der Klasse die dieses Interface implementiert definiert werden.
 */
public interface RoomRepository {

    List<Room> readAll();

    List<Booking> getEmptyRoomsByTimeWindow(LocalDateTime startSearchTime, LocalDateTime endSearchTime);

    void addRoomEquipment(Room room) throws SQLException;

    void deleteRoom(Room room) throws SQLException;

    void updateRoom(Room room) throws SQLException;

    void deleteEquipment(Room room, Equipment equipment) throws SQLException;

    void addEquipment(Room room, Equipment equipment) throws SQLException;

    int getRoomCountByRoomNumber(int roomNumber) throws SQLException;
}
