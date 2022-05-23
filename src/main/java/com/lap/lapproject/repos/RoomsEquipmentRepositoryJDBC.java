package com.lap.lapproject.repos;

import com.lap.lapproject.model.RoomsEquipment;

import java.sql.*;
public class RoomsEquipmentRepositoryJDBC extends Repository implements RoomsEquipmentRepository {
    private static final String ADD_EQUIPMENT_ROOM_RT = "INSERT INTO rooms_equipment ( room_id, equipment_id) " + "VALUES (?,?)";


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
        }
        return roomsEquipment;
    }
}
