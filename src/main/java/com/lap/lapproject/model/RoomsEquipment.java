package com.lap.lapproject.model;

import javafx.beans.property.SimpleIntegerProperty;

public class RoomsEquipment {
    private int roomsEquipmentId;
    private int roomId;
    private int equipmentId;

    public RoomsEquipment(int roomsEquipmentId, int roomId, int equipmentId) {
        this.roomsEquipmentId = roomsEquipmentId;
        this.roomId = roomId;
        this.equipmentId = equipmentId;
    }

    public RoomsEquipment(int roomId, int equipmentId) {
        this.roomId = roomId;
        this.equipmentId = equipmentId;
    }

    public int getRoomsEquipmentId() {
        return roomsEquipmentId;
    }

    public void setRoomsEquipmentId(int roomsEquipmentId) {
        this.roomsEquipmentId = roomsEquipmentId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

}
