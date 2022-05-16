package com.lap.lapproject.model;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Program {

    private static final Logger log = LoggerFactory.getLogger(Program.class);

    private SimpleIntegerProperty id;
    private SimpleStringProperty programName;

    public Program(int id) {
        this.id = new SimpleIntegerProperty(id);
        this.programName = new SimpleStringProperty();
    }

    public Program(String programName) {
        this.programName = new SimpleStringProperty(programName);
        this.id = new SimpleIntegerProperty();
    }

    public Program(int id, String programName) {
        this.id = new SimpleIntegerProperty(id);
        this.programName = new SimpleStringProperty(programName);
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


    public String getProgramName() {
        return programName.get();
    }

    public SimpleStringProperty programNameProperty() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName.set(programName);
    }
    @Override
    public String toString() {
        return getProgramName();
    }
}
