package com.lap.lapproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Program {
    private long id;
    private SimpleStringProperty program;

    public Program(long id, String program) {
        this.program = new SimpleStringProperty(program);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProgram() {
        return program.get();
    }

    public SimpleStringProperty programProperty() {
        return program;
    }

    public void setProgram(String program) {
        this.program.set(program);
    }
}
