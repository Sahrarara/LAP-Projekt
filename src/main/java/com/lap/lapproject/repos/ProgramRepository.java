package com.lap.lapproject.repos;

import com.lap.lapproject.model.Course;
import com.lap.lapproject.model.Program;

import java.sql.SQLException;
import java.util.List;

public interface ProgramRepository {

    int addProgram(Program program) throws SQLException;
    List<Program> readProgram() throws SQLException;
    void updateProgram(Program program) throws SQLException;
    void deleteProgram(Program program) throws SQLException;
    Program getProgramByProgramName(String programName) throws SQLException;
    int getProgramsCountByProgramName(String programName) throws SQLException;
}
