package com.lap.lapproject.repos;

import com.lap.lapproject.controller.AddRoomController;
import com.lap.lapproject.model.*;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoomRepositoryJDBC extends Repository implements RoomRepository {
    private static final Logger logger = LoggerFactory.getLogger(AddRoomController.class);

    private static final String SELECT_ROOM_SQL_STRING = "SELECT * FROM rooms JOIN location ON rooms.location_id = location.location_id";
    private static final String SELECT_EQUIPMENT_ID_LIST = "SELECT * FROM equipment INNER JOIN rooms_equipment ON rooms_equipment.equipment_id=equipment.equipment_id WHERE rooms_equipment.room_id=(?) ";
  /*  private static final String SEARCH_BOOKING_REPEATING_SQL = "SELECT * FROM rooms \n" +
            "LEFT JOIN booking On rooms.room_id = booking.room_id \n" +
            "LEFT JOIN location On rooms.location_id = location.location_id \n" +
            "LEFT JOIN rooms_equipment ON rooms.room_id = rooms_equipment.room_id \n" +
            "LEFT JOIN equipment ON rooms_equipment.equipment_id = equipment.equipment_id \n" +
            "WHERE booking.datetime_start >= (?) \n" +
            "AND booking.datetime_end <= (?)" ;
*/
    private static final String ADD_NEW_ROOM_SQL_STRING = "INSERT INTO rooms(room_number, size, location_id)" + "VALUES (?,?,?)";
    private static final String ADD_EQUIPMENT_SQL_STRING = "INSERT INTO rooms_equipment (room_id, equipment_id)" +
            " VALUES (?,?)";
    private static final String DELETE_ROOM_SQL_STRING = "DELETE FROM rooms WHERE room_id=?";
    private static final String UPDATE_ROOM_SQL_STRING = "UPDATE rooms SET room_number =?, size=?, location_id=? WHERE room_id=? ";
    private static final String DELETE_EQUIPMENT_ROOM_RT = "DELETE FROM rooms_equipment WHERE room_id=? AND " +
            "equipment_id=?";
    private static final String GET_ROOM_COUNT_BY_UNIQUE_ROOM_NUMBER_SQL_STRING = "SELECT COUNT(*) AS unique_room_count FROM rooms WHERE room_number = (?) ";

    @Override
    public List<Room> readAll()  {
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
                room.setEquipments(equipments);

                roomList.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return roomList;
    }

/*

    public List<Room> getEmptyRoomsByTimeWindow(LocalDateTime startSearchTime, LocalDateTime endSearchTime) {
        Connection connection = connect();
        PreparedStatement statement = null;
        PreparedStatement equipmentStatement = null;

        ResultSet resultSet = null;
        ResultSet equipmentResultSet = null;
        List<Room> roomList = new ArrayList<>();

        String startDateTime = startSearchTime.toString();

        try {
            assert connection != null;
            statement = connection.prepareStatement(SEARCH_BOOKING_REPEATING_SQL);
            statement.setTimestamp(1, Timestamp.valueOf(startSearchTime));
            statement.setTimestamp(2, Timestamp.valueOf(endSearchTime));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {


                Location location = new Location(
                        resultSet.getInt("location_id"),
                        resultSet.getString("street"),
                        resultSet.getString("zip"),
                        resultSet.getString("city"));


                //TODO: Equipment von rooms_equipment hinzufügen
                //Equipment equipment = new Equipment();

                List<Equipment> equipments = new ArrayList<>();

                Room room = new Room(
                        resultSet.getInt("rooms.room_id"),
                        resultSet.getInt("rooms.room_number"),
                        resultSet.getInt("rooms.size"),
                        location,
                        equipments);

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
                room.setEquipments(equipments);

                roomList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {

                if (connection != null) connection.close();
            }catch (SQLException e) {
                logger.error("Something went wrong, it did not work", e);
            }
        }
        return roomList;
    }

*/


    @Override
    public void addRoomEquipment(Room room) throws SQLException {
        Connection connection = connect();
        ResultSet rs = null;
        PreparedStatement ps = null;
        int roomKey = 0;

        try {

            ps = connection.prepareStatement(ADD_NEW_ROOM_SQL_STRING, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, room.getRoomNumber());
            ps.setInt(2, room.getSize());
            ps.setInt(3, room.getLocation().getId());
            ps.executeUpdate();

            logger.info("Room added to DB: {} ", room.getRoomNumber());

            rs = ps.getGeneratedKeys();
            while (rs.next()) {
                roomKey = rs.getInt(1);
                room.setId((roomKey));

                logger.info("New room saved in DB with ID: " + roomKey);
            }
            ps.close();

            for (Equipment equipmentId : room.getEquipments()) {

                ps = connection.prepareStatement(ADD_EQUIPMENT_SQL_STRING);
                ps.setInt(1, roomKey);
                ps.setInt(2, equipmentId.getId());
                ps.executeUpdate();
                /* addEquipment( room,  equipmentId);*/

                logger.info("New equipments added to DB: {}", equipmentId);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }


    @Override
    public void addEquipment(Room room, Equipment equipment) throws SQLException {
        Connection connection = connect();
        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(ADD_EQUIPMENT_SQL_STRING);
            ps.setInt(1, room.getId());
            ps.setInt(2, equipment.getId());
            ps.executeUpdate();

            logger.info("Add equipment to DB :{}", equipment);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }


    @Override
    public void deleteEquipment(Room room, Equipment equipment) throws SQLException {
        Connection connection = connect();
        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(DELETE_EQUIPMENT_ROOM_RT);
            ps.setInt(1, room.getId());
            ps.setInt(2, equipment.getId());
            ps.executeUpdate();

            logger.info("Delete equipment in DB :{}", equipment);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }


    @Override
    public void deleteRoom(Room room) throws SQLException {
        Connection connection = connect();
        PreparedStatement ps = null;

        try {

            ps = connection.prepareStatement(DELETE_ROOM_SQL_STRING);
            ps.setInt(1, room.getId());
            ps.executeUpdate();

            logger.info("Room deleted from DB: {}", room.getId());

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
            preparedStatement.executeUpdate();

            logger.info("Room updated in DB: {}", room.getRoomNumber());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }


    public int getRoomCountByRoomNumber(int roomNumber){
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int roomCount = 0;
        try {
            preparedStatement = connection.prepareStatement(GET_ROOM_COUNT_BY_UNIQUE_ROOM_NUMBER_SQL_STRING  );
            preparedStatement.setInt(1, roomNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                roomCount = resultSet.getInt("unique_room_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return roomCount;
    }



}
