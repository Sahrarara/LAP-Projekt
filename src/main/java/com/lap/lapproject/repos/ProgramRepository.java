package com.lap.lapproject.repos;

import com.lap.lapproject.model.Program;

import java.sql.SQLException;

public interface ProgramRepository {

    void addProgram(Program program) throws SQLException;
    boolean getProgram() throws SQLException;
    void updateProgram(Program program) throws SQLException;
    void deleteProgram(Program program) throws SQLException;
}
