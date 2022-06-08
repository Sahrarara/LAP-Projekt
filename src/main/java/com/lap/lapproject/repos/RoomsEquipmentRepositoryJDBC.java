package com.lap.lapproject.repos;

import com.lap.lapproject.model.RoomsEquipment;

import java.sql.*;
public class RoomsEquipmentRepositoryJDBC extends Repository implements RoomsEquipmentRepository {
    private static final String ADD_EQUIPMENT_ROOM_RT = "INSERT INTO rooms_equipment ( room_id, equipment_id) " + "VALUES (?,?)";
    //private static final String UPDATE_EQUIPMENT_ROOM_RT = "UPDATE rooms_equipment SET equipment_id=? WHERE rooms_equipment_id =?";
    private static final String DELETE_EQUIPMENT_ROOM_RT = "DELETE FROM rooms_equipment  WHERE room_id=?";


    @Override
    public RoomsEquipment addRoomEquipmentRT(RoomsEquipment roomsEquipment) throws SQLException {
        Connection connection = connect();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(ADD_EQUIPMENT_ROOM_RT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, roomsEquipment.getRoomId());
            ps.setInt(2, roomsEquipment.getEquipmentId());

            ps.executeQuery();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    int generatedKeys = rs.getInt(1);
                    roomsEquipment.setRoomsEquipmentId(generatedKeys);
                    System.out.println("Roomrepository. " + generatedKeys);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        return roomsEquipment;
    }

    @Override
    public void deleteRoomEquipmentRT(RoomsEquipment roomsEquipment) throws SQLException {
        Connection connection = connect();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(DELETE_EQUIPMENT_ROOM_RT );
            ps.setInt(1, roomsEquipment.getRoomId());
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }

     /* @Override //TODO: zum verbessern
    public void updateRoomEquipmentRT(RoomsEquipment roomsEquipment) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_EQUIPMENT_ROOM_RT);
           // preparedStatement.setInt(1, roomsEquipment.getRoomId());
            preparedStatement.setInt(1,roomsEquipment.getEquipmentId());
            preparedStatement.setInt(2, roomsEquipment.getRoomsEquipmentId());
            preparedStatement.executeQuery();
            //preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
