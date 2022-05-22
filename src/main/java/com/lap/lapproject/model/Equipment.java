package com.lap.lapproject.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Equipment {

    private static final Logger log = LoggerFactory.getLogger(Equipment.class);

    private SimpleIntegerProperty id;
    private SimpleStringProperty description;

    public Equipment(String description) {
        this.description = new SimpleStringProperty(description);
        this.id = new SimpleIntegerProperty();
    }

    public Equipment(int id, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
