package com.lap.lapproject.repos;

import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Location;
import com.lap.lapproject.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomRepositoryJDBC extends Repository implements RoomRepository {

    private static final String SELECT_ROOM_SQL_STRING =   "SELECT room_id,room_number,size,street FROM rooms JOIN location " +
            "ON rooms.location_id = location.location_id";

    @Override
    public boolean getRoom() {
        Connection connection = connect();
        ListModel.roomList.clear();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_ROOM_SQL_STRING);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Location location = new Location(resultSet.getString("street"));
                Room room = new Room(resultSet.getLong("room_id"), resultSet.getString("room_number"), resultSet.getInt(
                        "size"),
                        location);
                ListModel.roomList.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
