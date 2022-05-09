package com.lap.lapproject.repos;

import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.model.Program;

import java.sql.SQLException;
import java.util.List;

public interface EquipmentRepository {
    List<Equipment> readAll() throws SQLException;
    int addEquipment(Equipment equipment) throws SQLException;

}
