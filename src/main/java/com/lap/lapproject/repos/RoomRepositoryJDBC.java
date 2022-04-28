package com.lap.lapproject.repos;

import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Location;
import com.lap.lapproject.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomRepositoryJDBC extends Repository implements RoomRepository {

    private static final String GET_ROOM_QUERY =   "SELECT room_number,size,street FROM rooms JOIN location ON rooms.location_id = location.location_id";

    @Override
    public boolean getRoom() throws SQLException {
        Connection connection = connect();
        ListModel.roomList.clear();
        String query = GET_ROOM_QUERY;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Location location = new Location(resultSet.getString("street"));
                Room room = new Room(resultSet.getString("room_number"), resultSet.getInt("size"), location);
                ListModel.roomList.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
