package com.lap.lapproject.repos;

import com.lap.lapproject.model.Location;

import java.sql.SQLException;
import java.util.List;

public interface LocationRepository {

   List<Location> readAll() throws SQLException;
    void addLocation(Location location) throws SQLException;
}
