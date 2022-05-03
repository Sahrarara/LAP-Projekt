package com.lap.lapproject.model;

import javafx.beans.property.SimpleBooleanProperty;


public class Program {
    private long id;
    private String programName;
    private SimpleBooleanProperty checked = new SimpleBooleanProperty(false);

    public Program(long id, String programName) {
        this.id = id;
        this.programName = programName;
    }

    public Program(String programName) {
        this.programName = programName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
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
