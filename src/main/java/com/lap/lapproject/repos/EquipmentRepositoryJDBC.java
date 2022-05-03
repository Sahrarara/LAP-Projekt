package com.lap.lapproject.repos;

import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipmentRepositoryJDBC extends Repository implements EquipmentRepository {

    private static final String SELECT_EQUIPMENT_SQL_STRING = "SELECT * FROM rooms_equipment JOIN equipment ON rooms_equipment.equipment_id = equipment.equipment_id JOIN rooms ON rooms_equipment.room_id= rooms.room_id; ";


    @Override
    public boolean getEquipment() {
        Connection connection = connect();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.prepareStatement(SELECT_EQUIPMENT_SQL_STRING);
            rs = statement.executeQuery();
            while (rs.next()) {
                Equipment equipment = new Equipment(rs.getLong("rooms_equipment_id"), rs.getString(
                        "equipment.equipment_description"), new Room(rs.getString("rooms.room_number")));

                ListModel.equipmentList.add(equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
