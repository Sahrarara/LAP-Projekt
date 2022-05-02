package com.lap.lapproject.repos;

import com.lap.lapproject.model.Location;

import java.sql.SQLException;

public interface LocationRepository {

    boolean getLocation() throws SQLException;
    void addLocation(Location location) throws SQLException;
}
