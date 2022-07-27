package com.lap.lapproject.repos;

import com.lap.lapproject.model.Location;

import java.sql.SQLException;
import java.util.List;

/**
 * Das LocationRepository Interface erstellt die leeren Methoden addLocation(), deleteLocation(), updateLocation() und die ListMethode readAll() welche die Klasse erwartet,
 * diese Methoden müssen in der Klasse die dieses Interface implementiert definiert werden.
 */
public interface LocationRepository {
    List<Location> readAll() throws SQLException;

    int addLocation(Location location) throws SQLException;

    void deleteLocation(Location location) throws SQLException;

    void updateLocation(Location location) throws SQLException;

}
