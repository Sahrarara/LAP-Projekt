package com.lap.lapproject.repos;

import com.lap.lapproject.model.Course;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Program;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CourseRepositoryJDBC extends Repository implements CourseRepository {
    private final String SELECT_COURSE_SQL_STRING = "SELECT course_id, course_name, course_start, course_end,group_size, name FROM courses JOIN programs WHERE courses.program_id=programs.program_id";


    @Override
    public void addCourse() throws SQLException {

    }

    @Override
    public boolean getCourse()  {
        Connection connection = connect();
        ListModel.courseList.clear();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(SELECT_COURSE_SQL_STRING);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timestart = resultSet.getString("course_start");
                LocalDateTime coursestart = LocalDateTime.parse(timestart, formatter);
                String timeend = resultSet.getString("course_end");
                LocalDateTime courseend = LocalDateTime.parse(timeend, formatter);

                Course course = new Course(resultSet.getLong("course_id"), resultSet.getString("course_name"),
                        new Program(resultSet.getString("name")), coursestart,
                                courseend, resultSet.getInt("group_size"));
                ListModel.courseList.add(course);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return false;
    }
}
