package com.lap.lapproject.repos;

import com.lap.lapproject.model.Equipment;

import java.sql.SQLException;
import java.util.List;

public interface EquipmentRepository {
    List<Equipment> readAll() throws SQLException;

}
