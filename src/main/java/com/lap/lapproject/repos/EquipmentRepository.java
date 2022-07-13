package com.lap.lapproject.repos;

import com.lap.lapproject.model.Equipment;

import java.sql.SQLException;
import java.util.List;

/**
 * Das EquipmentRepository Interface erstellt die leeren Methoden addEquipment(), updateEquipment(), deleteEquipment(), getEquipmentCountByEquipmentDescription() und die ListMethode readAll() welche die Klasse Equipment erwartet,
 * diese Methoden müssen in der Klasse die dieses Interface implementiert definiert werden.
 */
public interface EquipmentRepository {
    List<Equipment> readAll() throws SQLException;
    int addEquipment(Equipment equipment) throws SQLException;

    void updateEquipment(Equipment equipment) throws SQLException;
    void deleteEquipment(Equipment equipment) throws SQLException;
    int getEquipmentCountByEquipmentDescription(String equipmentDescription) throws SQLException;
}
