package com.lap.lapproject.repos;

import com.lap.lapproject.model.Course;
import com.lap.lapproject.model.Program;

import java.sql.SQLException;
import java.util.List;

/**
 * Das ProgramRepository Interface erstellt die leeren Methoden addProgram(), updateProgram(), deleteProgram(), getProgramsCountByProgramName(), getProgramByProgramName() und die ListMethode readProgram(),
 * diese Methoden müssen in der Klasse die dieses Interface implementiert definiert werden.
 */
public interface ProgramRepository {

    List<Program> readProgram() throws SQLException;
    int addProgram(Program program) throws SQLException;
    void updateProgram(Program program) throws SQLException;
    void deleteProgram(Program program) throws SQLException;
    Program getProgramByProgramName(String programName) throws SQLException;
    int getProgramsCountByProgramName(String programName) throws SQLException;
}
