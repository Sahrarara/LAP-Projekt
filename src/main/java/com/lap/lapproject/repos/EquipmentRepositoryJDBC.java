package com.lap.lapproject.repos;

import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.model.Program;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentRepositoryJDBC extends Repository implements EquipmentRepository {

    private static final String SELECT_EQUIPMENT_SQL_STRING = "SELECT * FROM equipment";
    private static final String ADD_NEW_EQUIPMENT_SQL_STRING = "INSERT INTO equipment (equipment_description) VALUES (?)";
    private static final String UPDATE_EQUIPMENT_SQL_STRING = "UPDATE equipment SET equipment_description =? WHERE equipment_id=?";

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

    public int addEquipment(Equipment equipment) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int generatedKey = 0;

        try {
            preparedStatement = connection.prepareStatement(ADD_NEW_EQUIPMENT_SQL_STRING, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, equipment.getDescription());
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next() ) {
                generatedKey = resultSet.getInt(1);
                equipment.setId(generatedKey);
            }

        } catch (SQLException e ) {
            e.printStackTrace();
        }

        return generatedKey;
    }


    public void updateEquipment(Equipment equipment) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_EQUIPMENT_SQL_STRING);
            preparedStatement.setString(1, equipment.getDescription());
            preparedStatement.setLong(2, equipment.getId());
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
