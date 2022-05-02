package com.lap.lapproject.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Program {
    private long id;
    private String program;
    private SimpleBooleanProperty checked = new SimpleBooleanProperty(false);

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

    public boolean isChecked() {
        return checked.get();
    }

    public SimpleBooleanProperty checkedProperty() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }
}
