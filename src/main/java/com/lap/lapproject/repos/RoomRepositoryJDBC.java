package com.lap.lapproject.repos;

import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.model.Location;
import com.lap.lapproject.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepositoryJDBC extends Repository implements RoomRepository {
    private static final String SELECT_ROOM_SQL_STRING = "SELECT rooms_equipment.rooms_equipment_id, rooms.room_id ," +
            "rooms" +
            ".room_number, rooms.size, location.street, equipment.equipment_description FROM rooms_equipment JOIN " +
            "rooms ON rooms_equipment.room_id= rooms.room_id JOIN equipment ON rooms_equipment.equipment_id = " +
            "equipment.equipment_id JOIN location ON rooms.location_id = location.location_id";



    @Override
    public List<Room> readAll() {
        Connection connection = connect();
        List<Room> roomList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_ROOM_SQL_STRING);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Location location = new Location(resultSet.getString("location.street"));
                Equipment equipment = new Equipment(resultSet.getString("equipment.equipment_description"));
                Room room = new Room(resultSet.getInt("rooms.room_id"), resultSet.getInt("rooms.room_number"),
                        resultSet.getInt("rooms.size"), location, equipment);
                roomList.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomList;
    }




}
