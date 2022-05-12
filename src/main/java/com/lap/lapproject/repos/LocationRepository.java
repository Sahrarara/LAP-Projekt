package com.lap.lapproject.repos;

import com.lap.lapproject.model.Location;

import java.sql.SQLException;
import java.util.List;

public interface LocationRepository {
   List<Location> readAll() throws SQLException;
   int addLocation(Location location) throws SQLException;

   void deleteLocation(Location location) throws SQLException;
   void updateLocation(Location location) throws SQLException;

}
