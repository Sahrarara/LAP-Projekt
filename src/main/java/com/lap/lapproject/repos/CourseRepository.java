package com.lap.lapproject.repos;

import com.lap.lapproject.model.Course;

import java.sql.SQLException;

public interface CourseRepository {
    void addCourse() throws SQLException;
    boolean getCourse() throws SQLException;

}
