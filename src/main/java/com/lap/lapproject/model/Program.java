package com.lap.lapproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Program {
    private SimpleStringProperty program;

    public Program(String program) {
        this.program = new SimpleStringProperty(program);
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
