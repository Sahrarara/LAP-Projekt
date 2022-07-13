package com.lap.lapproject.model;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.lap.lapproject.repos.BookingRepositoryJDBC;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingModel {
    Model model;
    CalendarSource calendarSource;

    public BookingModel(Model model) {
        this.model = model;
    }

    public CalendarSource getCalendarSource() {
        return calendarSource;
    }

    public List<Entry<?>> allEntriesInInterval(LocalDate starttime, LocalDate endtime){
        List<Entry<?>> invervalEntries = new ArrayList<>();
        for (Calendar calendar : calendarSource.getCalendars()){
            Map<LocalDate, List<Entry<?>>> result = calendar.findEntries(starttime, endtime, ZoneId.systemDefault());
            for (LocalDate key : result.keySet()){
                result.get(key);
                invervalEntries.addAll(result.get(key));

                //if (result.get(0).get(0).getUserObject()instanceof Booking b){
                //    b.getRoom();
                //}
            }
        }
        return invervalEntries;
    }

    public boolean findBookedRooms(List<Entry<?>> intervalEntries, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        List<Entry<?>> bookedEntries = new ArrayList<>();
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);
        for (Entry entry : intervalEntries){    //TODO: Mit Räume vergleichen, Mit Tag vergleichen
            if (entry.getUserObject()instanceof Booking b){;
                if ((start.isBefore(b.getDateTimeStart()) && end.isBefore(b.getDateTimeStart()))
                        || end.isEqual(b.getDateTimeEnd()) || start.isEqual(b.getDateTimeEnd()) || start.equals(b.getDateTimeEnd())){
                    return true;
                } else {
                    return false; //TODO: Liste aller Räume machen
                }
            }
        }
        return false;
    }

    public List<Room> actualEmptyRooms(List<Entry<?>> intervalEntries, List<Entry<?>> bookedEntries){
        List<Room> emptyRooms = new ArrayList<>();
            for (Entry entry : intervalEntries){
                if (true){
                    //TODO: Logik
                }
            }
            return emptyRooms;
    }

    /**
     * Prüft die Ansicht für jeden Kursnamen mit einer Buchung aus der Datenbank und fragt jeden Kurs nach einer Buchung ab.
     * Jede Buchung, welche aus der Datenbank gelesen wird, wird nach der RecurranceRule befragt.
     * Die sich wiederholenden Kurse werden mit Regex in einen lesbaren String gewandelt, um diese im Kalender anzuzeigen.
     * Wenn der Kurs nur einmalig ist, wird er ohne umwandlung in den Kalender eingetragen, da das Startdatum auch das Enddatum ist.
     */
    public void loadBookingIntoCalendar(){

        calendarSource = new CalendarSource("My Calendars");

        System.setProperty("calendarfx.developer", "true");

        Calendar holidays = new Calendar("Feiertage");

        holidays.setStyle(Calendar.Style.STYLE2);


        model.courses.forEach(course -> {
            String currentCourse = course.getCourseName();
            System.out.println(course.getCourseName());
            Calendar newCalendar = new Calendar(currentCourse);
            model.bookings.forEach(booking -> {
                if (booking.getCourse().getCourseName().equals(currentCourse)){
                    Entry<Booking> newEntry = new Entry<>(booking.getCourse().getProgram().getProgramName());
                    newEntry.setLocation(booking.getRoom().getLocation().getStreet());
                    newEntry.setUserObject(booking);
                    if (!booking.getRecurrenceRule().equals("keiner")){
                        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
                        LocalDateTime dailytime = LocalDateTime.of(booking.getDateTimeStart().toLocalDate(), booking.getDateTimeEnd().toLocalTime());
                        Interval interval = new Interval(booking.getDateTimeStart(), dailytime, ZoneId.systemDefault());
                        newEntry.setInterval(interval);
                        String recurrenceRule = bookingRepositoryJDBC.convertRecurrenceRuleFromTextToFrequency(booking.getRecurrenceRule());
                        String regexDate = booking.getDateTimeEnd().toString().replaceAll("[-:]","").substring(0,8);
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
