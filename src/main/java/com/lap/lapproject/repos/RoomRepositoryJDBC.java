package com.lap.lapproject.repos;

import com.lap.lapproject.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepositoryJDBC extends Repository implements RoomRepository {


    /*private static final String SELECT_ROOM_SQL_STRING = "SELECT * FROM rooms " +
            "JOIN location ON rooms.location_id = location.location_id " +
            "JOIN rooms_equipment ON rooms_equipment.room_id= rooms.room_id " +
            "LEFT JOIN equipment ON equipment.equipment_id = rooms_equipment.equipment_id  ";*/

    private static final String SELECT_ROOM_SQL_STRING = "SELECT * FROM rooms " +
            "JOIN location ON rooms.location_id = location.location_id " +
            "LEFT JOIN equipment ON equipment.equipment_id=rooms.room_id ";

    private static final String ADD_NEW_ROOM_SQL_STRING = "INSERT INTO rooms(room_number, size, location_id)" + "VALUES (?,?,?)";
    private static final String SELECT_EQUIPMENT_ID_LIST = "SELECT * FROM equipment INNER JOIN rooms_equipment ON rooms_equipment.equipment_id=equipment.equipment_id WHERE rooms_equipment.room_id=(?) ";
    private static final String DELETE_ROOM_SQL_STRING = "DELETE FROM rooms WHERE room_id=?";
    private static final String UPDATE_ROOM_SQL_STRING = "UPDATE rooms SET room_number =?, size=?, location_id=? WHERE room_id=? ";

    @Override
    public List<Room> readAll() throws SQLException {
        Connection connection = connect();
        List<Room> roomList = new ArrayList<>();

        PreparedStatement roomStatement = null;
        PreparedStatement equipmentStatement = null;
        ResultSet roomResultSet = null;
        ResultSet equipmentResultSet = null;

        try {
            roomStatement = connection.prepareStatement(SELECT_ROOM_SQL_STRING);
            roomResultSet = roomStatement.executeQuery();

            while (roomResultSet.next()) {
                Location location = new Location(roomResultSet.getInt("location_id"),
                        roomResultSet.getString("location.street"),
                        roomResultSet.getString("location.zip"),
                        roomResultSet.getString("location.city"));

                List<Equipment> equipments = new ArrayList<>();

                Room room = new Room(roomResultSet.getInt("room_id"),
                        roomResultSet.getInt("room_number"),
                        roomResultSet.getInt("size"),
                        location,
                        equipments);


                /*ListView<Equipment> listView = new ListView<Equipment>((ObservableList<Equipment>) equipments);*/
                equipmentStatement = connection.prepareStatement(SELECT_EQUIPMENT_ID_LIST);
                equipmentStatement.setInt(1, room.getId());
                equipmentResultSet = equipmentStatement.executeQuery();

                while (equipmentResultSet.next()) {
                    Equipment equipment = new Equipment(
                            equipmentResultSet.getInt("equipment_id"),
                            equipmentResultSet.getString("equipment_description")
                    );
                    equipments.add(equipment);
                }
                room.setEquipment(equipments);

                roomList.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }

        return roomList;
    }


    @Override
    public Room addRoom(Room room) throws SQLException {
        Connection connection = connect();
        int generatedKeys = 0;

        try (PreparedStatement ps = connection.prepareStatement(ADD_NEW_ROOM_SQL_STRING, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setInt(1,room.getId());
            ps.setInt(1, room.getRoomNumber());
            ps.setInt(2, room.getSize());
            ps.setInt(3, room.getLocation().getId());
            // ps.setArray(4, (Array) room.getEquipment());
            ps.executeQuery();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    generatedKeys = rs.getInt(1);
                    room.setId((generatedKeys));
                    System.out.println("New room saved with ID: " + generatedKeys);
                }
            }
        }
        return room;
    }

    @Override
    public void deleteRoom(Room room) throws SQLException {
        Connection connection = connect();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(DELETE_ROOM_SQL_STRING);
            ps.setInt(1, room.getId());
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }

    @Override
    public void updateRoom(Room room) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_ROOM_SQL_STRING);
            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setInt(2, room.getSize());
            preparedStatement.setInt(3, room.getLocation().getId());
            preparedStatement.setInt(4, room.getId());
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }

    }
}
