package com.lap.lapproject.repos;

import com.lap.lapproject.model.Program;

import java.sql.SQLException;

public interface ProgramRepository {

    void addProgram(Program program) throws SQLException;
    boolean getProgram() throws SQLException;
}
