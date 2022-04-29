package com.lap.lapproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Program {
    private long id;
    private String program;

    public Program(long id, String program) {
        this.program = program;
        this.id = id;
    }

    public Program(String program) {
        this.program = program;
    }

    public long getId() {
        return id;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setId(long id) {
        this.id = id;
    }

}
