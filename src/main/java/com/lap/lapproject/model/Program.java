package com.lap.lapproject.model;


public class Program {
    private long id;
    private String programName;

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

}
