package com.lap.lapproject.repos;


import com.lap.lapproject.model.Booking;
import com.lap.lapproject.model.Room;
import com.lap.lapproject.utility.QuickAlert;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class BookingRepositoryJDBCTest {

    @Test
   public  void readAll() throws SQLException {
        BookingRepositoryJDBC tester = new BookingRepositoryJDBC();
        List<Booking> result = tester.readAll();
        //Wie viele zeilen gibt es in unsere booking Tabelle
        assertEquals(8,result.size());
    }

    @Test
   public void findFreeRoomsByTime() {
        BookingRepositoryJDBC tester = new BookingRepositoryJDBC();
        LocalDateTime startTime = LocalDateTime.of(2023, 5, 28, 12,0);
        LocalDateTime endTime =LocalDateTime.of(2023, 5, 28, 13,0) ;
        List<Room> result = tester.findFreeRoomsByTime(startTime ,endTime);
        //wie viele Elemente hat die Liste
        assertEquals(1,result.size());
        assertEquals(2, result.get(0).getId());
    }

    @Test
    public void findFreeRoomsByTime3() {
        BookingRepositoryJDBC tester = new BookingRepositoryJDBC();
        LocalDateTime startTime = LocalDateTime.of(2023, 5, 28, 10,0);
        LocalDateTime endTime =LocalDateTime.of(2023, 5, 28, 11,0) ;
        List<Room> result;
        result = tester.findFreeRoomsByTime(startTime ,endTime);
        //wie viele Elemente hat die Liste
        assertEquals(2,result.size());//die liste hat 2 Elemente
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());

        String freeRoomsString = String.join(", ", result.toString());
       System.out.println(" Folgende Räume sind frei:\n"+ freeRoomsString);

    }


    @Test
    public void roomOneIsNotFree() {
        BookingRepositoryJDBC tester = new BookingRepositoryJDBC();
        LocalDateTime startTime = LocalDateTime.of(2023, 5, 28, 11,30);
        LocalDateTime endTime =LocalDateTime.of(2023, 5, 28, 12,30) ;
        Boolean result;
        result = tester.isRoomFree(1,startTime ,endTime);
        assertFalse(result);

    }

    @Test
    public void roomOneIsFree() {
        BookingRepositoryJDBC tester = new BookingRepositoryJDBC();
        LocalDateTime startTime = LocalDateTime.of(2023, 5, 28, 11,00);
        LocalDateTime endTime =LocalDateTime.of(2023, 5, 28, 12,00) ;
        Boolean result;
        result = tester.isRoomFree(1,startTime ,endTime);
        assertTrue(result);

    }

    @Test
    public void roomTwoIsFree() {
        BookingRepositoryJDBC tester = new BookingRepositoryJDBC();
        LocalDateTime startTime = LocalDateTime.of(2023, 5, 28, 11,30);
        LocalDateTime endTime =LocalDateTime.of(2023, 5, 28, 12,30) ;
        Boolean result;
        result = tester.isRoomFree(2,startTime ,endTime);
        assertTrue(result);

    }
}




