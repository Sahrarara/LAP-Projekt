package com.lap.lapproject.repos;

import com.lap.lapproject.model.Room;
import com.lap.lapproject.model.RoomsEquipment;

import java.sql.SQLException;

public interface RoomsEquipmentRepository {
    RoomsEquipment addRoomEquipmentRT(RoomsEquipment roomsEquipment) throws SQLException;
    //void updateRoomEquipmentRT(RoomsEquipment roomsEquipment) throws SQLException;
    void deleteRoomEquipmentRT(RoomsEquipment roomsEquipment)  throws SQLException;


}
