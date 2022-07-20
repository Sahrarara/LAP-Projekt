package com.lap.lapproject.model;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import com.lap.lapproject.repos.RoomRepositoryJDBC;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BookingModel {
    Model model;
    CalendarSource calendarSource;

    public BookingModel(Model model) {
        this.model = model;
    }

    public CalendarSource getCalendarSource() {
        return calendarSource;
    }

    public List<Entry<?>> allEntriesInInterval(LocalDate startDate, LocalDate endDate) {
        List<Entry<?>> invervalEntries = new ArrayList<>();
        for (Calendar calendar : calendarSource.getCalendars()) {
            Map<LocalDate, List<Entry<?>>> result = calendar.findEntries(startDate, endDate, ZoneId.systemDefault());
            for (LocalDate key : result.keySet()) {
                invervalEntries.addAll(result.get(key));

                //if (result.get(0).get(0).getUserObject()instanceof Booking b){
                //    b.getRoom();
                //}
            }
        }
        return invervalEntries;
    }

    public List<Entry<?>> allEntriesInInterval(LocalDate date) {
        return allEntriesInInterval(date, date);
    }


    public List<Room> findEmptyRooms(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {

        List<Entry<?>> intervalEntries = allEntriesInInterval(startDate, endDate);

        RoomRepositoryJDBC roomsRepo = new RoomRepositoryJDBC();
        List<Room> rooms = roomsRepo.readAll();

        for (Entry entry :
                intervalEntries) {
            LocalTime startTimeOfEntry = entry.getInterval().getStartTime();
            LocalTime endTimeOfEntry = entry.getInterval().getEndTime();
            Booking entryOfUserObject = (Booking) entry.getUserObject();

            Room room = entryOfUserObject.getRoom();

            if (!((startTime.isBefore(startTimeOfEntry) && endTime.isBefore(startTimeOfEntry)
                    || startTime.isAfter(endTimeOfEntry) && endTime.isAfter(endTimeOfEntry)
                    || startTime.equals(endTimeOfEntry) || endTime.equals(startTimeOfEntry)
            ))) {
                rooms.remove(room);
            }
        }
        return rooms;
    }

    /**
     * Prüft die Ansicht für jeden Kursnamen mit einer Buchung aus der Datenbank und fragt jeden Kurs nach einer Buchung ab.
     * Jede Buchung, welche aus der Datenbank gelesen wird, wird nach der RecurranceRule befragt.
     * Die sich wiederholenden Kurse werden mit Regex in einen lesbaren String gewandelt, um diese im Kalender anzuzeigen.
     * Wenn der Kurs nur einmalig ist, wird er ohne umwandlung in den Kalender eingetragen, da das Startdatum auch das Enddatum ist.
     */
    public void loadBookingIntoCalendar() {

        calendarSource = new CalendarSource("My Calendars");

        System.setProperty("calendarfx.developer", "true");

        Calendar holidays = new Calendar("Feiertage");

        holidays.setStyle(Calendar.Style.STYLE2);


        model.courses.forEach(course -> {
            String currentCourse = course.getCourseName();
            System.out.println(course.getCourseName());
            Calendar newCalendar = new Calendar(currentCourse);
            model.bookings.forEach(booking -> {
                if (booking.getCourse().getCourseName().equals(currentCourse)) {
                    Entry<Booking> newEntry = new Entry<>(booking.getCourse().getProgram().getProgramName());
                    newEntry.setLocation(booking.getRoom().getLocation().getStreet());
                    newEntry.setUserObject(booking);
                    if (!booking.getRecurrenceRule().equals("keiner")) {
                        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();

                        //TODO: frage wegen dailytime , daylytime ist anfangstag und anfangszeit
                        // sollte es nicht ein endOfdaylyTime geben für das interval

                        LocalDateTime dailytime = LocalDateTime.of(booking.getDateTimeStart().toLocalDate(), booking.getDateTimeEnd().toLocalTime());
                        Interval interval = new Interval(booking.getDateTimeStart(), dailytime, ZoneId.systemDefault());
                        newEntry.setInterval(interval);
                        String recurrenceRule = bookingRepositoryJDBC.convertRecurrenceRuleFromTextToFrequency(booking.getRecurrenceRule());
                        String regexDate = booking.getDateTimeEnd().toString().replaceAll("[-:]", "").substring(0, 8);
                        newEntry.setRecurrenceRule(recurrenceRule + ";UNTIL=" + regexDate + ";");
                    } else {
                        Interval interval = new Interval(booking.getDateTimeStart(), booking.getDateTimeEnd(), ZoneId.systemDefault());
                        newEntry.setInterval(interval);
                    }
                    newCalendar.addEntry(newEntry);
                }
            });
            newCalendar.setReadOnly(true);
            calendarSource.getCalendars().add(newCalendar);
        });

    }
}
