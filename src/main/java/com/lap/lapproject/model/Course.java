package com.lap.lapproject.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Course {
    private long id;
    private String courseName;
    private Program program;
    private LocalDateTime courseStart;
    private LocalDate courseEnd;
    private int groupSize;

    public Course(long id, String courseName, Program program, LocalDateTime courseStart, LocalDate courseEnd,
                  int groupSize) {
        this.id = id;
        this.courseName = courseName;
        this.program = program;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.groupSize = groupSize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDate getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(LocalDate courseEnd) {
        this.courseEnd = courseEnd;
    }
}
