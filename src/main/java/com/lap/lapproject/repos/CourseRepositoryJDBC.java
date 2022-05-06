package com.lap.lapproject.repos;

import com.lap.lapproject.model.Course;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Program;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CourseRepositoryJDBC extends Repository implements CourseRepository {
   private final String SELECT_COURSE_SQL_STRING = "SELECT course_id, course_name, course_start, course_end,group_size, name FROM courses JOIN programs WHERE courses.program_id=programs.program_id";


    //private final String SELECT_COURSE_SQL_STRING = "SELECT courses.course_id, courses.course_name, courses.course_start, courses.course_end, programs.name, courses.group_size FROM courses INNER JOIN programs ON courses.program_id=programs.program_id";

    private static final String ADD_NEW_COURSE_SQL_STRING = "INSERT INTO courses (course_name, course_start, course_end, program_id, group_size) VALUES (?, ?, ?, ?, ?)";





    @Override
    public boolean getCourse() {
        Connection connection = connect();
        ListModel.courseList.clear();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(SELECT_COURSE_SQL_STRING);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timeStart = resultSet.getString("course_start");
                LocalDateTime courseStart = LocalDateTime.parse(timeStart, formatter);
                String timeEnd = resultSet.getString("course_end");
                LocalDateTime courseEnd = LocalDateTime.parse(timeEnd, formatter);


                Course course = new Course(resultSet.getLong("course_id"), resultSet.getString("course_name"),
                        new Program(resultSet.getString("name")), courseStart, courseEnd, resultSet.getInt("group_size"));
                ListModel.courseList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


    @Override
    public void addCourse(Course course) throws SQLException {
        Connection connection = connect();

        try(PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_COURSE_SQL_STRING, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(course.getCourseStart().toLocalDate()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(course.getCourseEnd().toLocalDate()));
            preparedStatement.setInt(4, (int) course.getProgram().getId());
            preparedStatement.setInt(5, course.getGroupSize());

            preparedStatement.executeUpdate();


        }

    }
}
