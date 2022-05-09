package com.lap.lapproject.repos;

import com.lap.lapproject.model.Equipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentRepositoryJDBC extends Repository implements EquipmentRepository {

    private static final String SELECT_EQUIPMENT_SQL_STRING = "SELECT * FROM equipment";


    @Override
    public List<Equipment> readAll() {
        Connection connection = connect();
        List<Equipment> equipmentList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.prepareStatement(SELECT_EQUIPMENT_SQL_STRING);
            rs = statement.executeQuery();
            while (rs.next()) {
                Equipment equipment = new Equipment(rs.getInt("equipment_id"), rs.getString(
                        "equipment_description"));

              equipmentList.add(equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipmentList;
    }
}
