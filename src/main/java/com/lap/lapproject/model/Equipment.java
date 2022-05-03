package com.lap.lapproject.model;

import javafx.beans.property.SimpleBooleanProperty;

public class Equipment {
    private long id;
    private String description;
    private Room room;


    public Equipment(long id, String description, Room room) {
        this.id = id;
        this.description = description;
        this.room = room;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}
