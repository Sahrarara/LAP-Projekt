package com.lap.lapproject.model;

import java.util.Date;

public class Course {
    private String courseName;
    private Program program;
    private Date courseStart;
    private Date courseEnd;
    private int groupSize;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Date getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(Date courseStart) {
        this.courseStart = courseStart;
    }

    public Date getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(Date courseEnd) {
        this.courseEnd = courseEnd;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public Course(String courseName, Program program, Date courseStart, Date courseEnd, int groupSize) {
        this.courseName = courseName;
        this.program = program;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.groupSize = groupSize;
    }
}
