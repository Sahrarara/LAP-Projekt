package com.lap.lapproject.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Program {

    private static final Logger log = LoggerFactory.getLogger(Program.class);

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
