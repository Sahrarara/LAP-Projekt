package com.lap.lapproject.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Course {
    private String courseName;
    private Program program;
    private LocalDateTime courseStart;
    private LocalDateTime courseEnd;
    private int groupSize;

    public Course(String courseName, Program program, LocalDateTime courseStart, LocalDateTime courseEnd, int groupSize) {
        this.courseName = courseName;
        this.program = program;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.groupSize = groupSize;
    }

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

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public LocalDateTime getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(LocalDateTime courseStart) {
        this.courseStart = courseStart;
    }

    public LocalDateTime getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(LocalDateTime courseEnd) {
        this.courseEnd = courseEnd;
    }
}
