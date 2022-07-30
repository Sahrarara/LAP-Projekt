package com.lap.lapproject.model;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.lap.lapproject.controller.CalenderController;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import com.lap.lapproject.repos.CourseRepositoryJDBC;
import com.lap.lapproject.repos.RoomRepositoryJDBC;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//TODO: Kurs start-ende sollte sich nicht mit buchungsende oder start überschneiden

public class BookingModel {
    ListModel listModel;
    Model model;
    CalendarSource calendarSource = new CalendarSource("My Calender");

    public BookingModel(ListModel listModel, Model model) {
        this.listModel = listModel;
        this.model = model;
    }

    public CalendarSource getCalendarSource() {
        return calendarSource;
    }

    public List<Entry<?>> allEntriesInInterval(LocalDate startDate, LocalDate endDate) {
        List<Entry<?>> intervalEntries = new ArrayList<>();
        for (Calendar calendar : calendarSource.getCalendars()) {
            Map<LocalDate, List<Entry<?>>> result = calendar.findEntries(startDate, endDate, ZoneId.systemDefault());
            for (LocalDate date : result.keySet()) {
                intervalEntries.addAll(result.get(date));
            }
        }
        return intervalEntries;
    }

    public List<Entry<?>> allEntriesInInterval(LocalDate date) {
        return allEntriesInInterval(date, date);
    }

    /**
     * Holt sich aus der allEntriesInInterval-Methode alle Einträge. Es wird eine neue Instanz des RoomRepositoryJDBC erstellt, alle Räume ausgegeben und dann durch die Einträge iteriert
     *
     * @param startDate - gesuchter Starttag
     * @param endDate   - gesuchter Endtag
     * @param startTime - gesuchte Startzeit
     * @param endTime   - gesuchte Endzeit
     * @return es wird eine Liste von Räumen zurück gegeben
     */
    public List<Room> findEmptyRooms(Booking currentBooking, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {

        List<Entry<?>> intervalEntries = allEntriesInInterval(startDate, endDate);

        RoomRepositoryJDBC roomsRepo = new RoomRepositoryJDBC();
        List<Room> rooms = roomsRepo.readAll();

        for (Entry entry :
                intervalEntries) {
            System.out.println(rooms);
            LocalTime startTimeOfEntry = entry.getInterval().getStartTime();
            LocalTime endTimeOfEntry = entry.getInterval().getEndTime();
            Booking entryOfUserObject = (Booking) entry.getUserObject();


            Room room = entryOfUserObject.getRoom();

            if (entryOfUserObject.equals(currentBooking)) {
                continue;
            }

            if (!(startTime.isBefore(startTimeOfEntry) && endTime.isBefore(startTimeOfEntry)
                    || startTime.isAfter(endTimeOfEntry) && endTime.isAfter(endTimeOfEntry)
                    || startTime.equals(endTimeOfEntry) || endTime.equals(startTimeOfEntry)
            )) {
                rooms.remove(room);
            }
        }
        return rooms;
    }

    public List<Room> findEmptyRooms(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return findEmptyRooms(null, startDate, endDate, startTime, endTime);
    }

/*

    public List<Room> findEmptyRooms(int bookingId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {

        List<Entry<?>> intervalEntries = allEntriesInInterval(startDate, endDate);

        RoomRepositoryJDBC roomsRepo = new RoomRepositoryJDBC();
        List<Room> rooms = roomsRepo.readAll();

//        logger.info("All entries: {}", intervalEntries);

        for (Entry entry :
                intervalEntries) {
            LocalTime startTimeOfEntry = entry.getInterval().getStartTime();
            LocalTime endTimeOfEntry = entry.getInterval().getEndTime();
            Booking entryOfUserObject = (Booking) entry.getUserObject();
            int entryBookingId = entryOfUserObject.getId();
//            logger.info("EntryBookingId: {}, bookingTBU id {}",
//                    entryBookingId, bookingId);
            Room room = entryOfUserObject.getRoom();

            if (!((startTime.isBefore(startTimeOfEntry) && endTime.isBefore(startTimeOfEntry)
                    || startTime.isAfter(endTimeOfEntry) && endTime.isAfter(endTimeOfEntry)
                    || startTime.equals(endTimeOfEntry) || endTime.equals(startTimeOfEntry)
            ))) {
                // collision with booking with same id
                // thus the room of this booking is free
                if (entryBookingId != 0 && entryBookingId != bookingId) {
//                    logger.info("Removing room: {}", room);
                    rooms.remove(room);
                } else {
//                    logger.info("Room {} available for booking with id {}",
//                            room.getId(), bookingId);
                }
            }
        }
        return rooms;
    }
*/

    public List<Trainer> findAvailableTrainer(Booking currentBooking, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {

        List<Entry<?>> intervalEntries = allEntriesInInterval(startDate, endDate);

        UserRepositoryJDBC trainerRepo = new UserRepositoryJDBC();
        List<Trainer> allTrainer = trainerRepo.readAllTrainer();

        for (Entry entry :
                intervalEntries) {
            LocalTime startTimeOfEntry = entry.getInterval().getStartTime();
            LocalTime endTimeOfEntry = entry.getInterval().getEndTime();
            Booking entryOfUserObject = (Booking) entry.getUserObject();

            Trainer trainer = entryOfUserObject.getTrainer();

            if (entryOfUserObject.equals(currentBooking)) {
                continue;
            }
            if (!((startTime.isBefore(startTimeOfEntry) && endTime.isBefore(startTimeOfEntry)
                    || startTime.isAfter(endTimeOfEntry) && endTime.isAfter(endTimeOfEntry)
                    || startTime.equals(endTimeOfEntry) || endTime.equals(startTimeOfEntry)
            ))) {
                allTrainer.remove(trainer);

            }
        }
        return allTrainer;
    }

    public List<Course> findAvailableCourse(Booking currentBooking, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {

        List<Entry<?>> intervalEntries = allEntriesInInterval(startDate, endDate);

        CourseRepositoryJDBC courseRepo = new CourseRepositoryJDBC();
        List<Course> courses = courseRepo.readAll();

        for (Entry entry :
                intervalEntries) {
            LocalTime startTimeOfEntry = entry.getInterval().getStartTime();
            LocalTime endTimeOfEntry = entry.getInterval().getEndTime();
            Booking entryOfUserObject = (Booking) entry.getUserObject();

            Course course = entryOfUserObject.getCourse();

            if (entryOfUserObject.equals(currentBooking)) {
                continue;
            }

            if (!((startTime.isBefore(startTimeOfEntry) && endTime.isBefore(startTimeOfEntry)
                    || startTime.isAfter(endTimeOfEntry) && endTime.isAfter(endTimeOfEntry)
                    || startTime.equals(endTimeOfEntry) || endTime.equals(startTimeOfEntry)
            ))) {
                courses.remove(course);
            }
        }
        return courses;
    }


    /**
     * Prüft die Ansicht für jeden Kursnamen mit einer Buchung aus der Datenbank und fragt jeden Kurs nach einer Buchung ab.
     * Jede Buchung, welche aus der Datenbank gelesen wird, wird nach der RecurranceRule befragt.
     * Die sich wiederholenden Kurse werden mit Regex in einen String, für den recurrence String, umgewandelt, um diese im Kalender anzuzeigen.
     * Wenn der Kurs nur einmalig ist, wird er ohne umwandlung in den Kalender eingetragen, da das Startdatum auch das Enddatum ist.
     * <p>
     * //TODO: weiterenText hinzufügen
     */
    public void loadBookingIntoCalendar() {
        calendarSource.getCalendars().clear();
        System.setProperty("calendarfx.developer", "true");

//        Calendar holidays = new Calendar("Feiertage");
//        holidays.setStyle(Calendar.Style.STYLE2);


        model.courses.forEach(course -> {
            String currentCourse = course.getCourseName();
            Calendar tempCalendar = new Calendar("temporary");

            // jeder Kurs in einem eigenen Kalender gespeichert
            Calendar newCalendar = new Calendar(currentCourse);

            // gehe durch alle bookings
            listModel.bookingList.forEach(booking -> {

                // gehört dieses booking in diesen (currentCourse) Kalender, wenn ja erstelle einen neuen Eintrag
                if (booking.getCourse().getCourseName().equals(currentCourse)) {

                    Entry<Booking> newEntry = createEntryForBooking(booking);

                    tempCalendar.addEntry(newEntry);
                    ArrayList<LocalDate> dateList = new ArrayList<LocalDate>(); // ist überflüssig?
                    List<LocalDate> checkDate = new ArrayList<LocalDate>(); // ist überflüssig?
                    Map<LocalDate, List<Entry<?>>> endryDateMap = tempCalendar.findEntries(booking.getDateTimeStart().toLocalDate(), booking.getDateTimeEnd().toLocalDate(), ZoneId.systemDefault());

                    // Checkt für jeden Tag den Eintrag ob es nicht an einem Wochenende oder Feiertag ist
                    for (LocalDate date : endryDateMap.keySet()) {
                        dateList.add(date);
                        if (!CalenderController.isHolidayOrWeekend(date)) {

                            Entry<?> tempEntry = endryDateMap.get(date).get(0);

                            if (tempEntry.getUserObject() instanceof Booking booking1) {
//                                Booking booking1 = tempEntry.getUserObject();
                                LocalDateTime newStartDate = LocalDateTime.of(date, tempEntry.getStartTime());
                                LocalDateTime newEndDate = LocalDateTime.of(date, tempEntry.getEndTime());

                                // erstelle einen neuen Eintrag aus dem alten Eintrag
                                Entry<Booking> partitionedEntry = new Entry<>(booking1.getCourse().getProgram().getProgramName());
                                partitionedEntry.setLocation(booking1.getRoom().getLocation().getStreet());
                                partitionedEntry.setUserObject(booking1);
                                Interval interval1 = new Interval(newStartDate, newEndDate, ZoneId.systemDefault());
                                partitionedEntry.setInterval(interval1);
                                // fügen die Kopie zum neuen Kalender hinzu
                                newCalendar.addEntry(partitionedEntry);
                            }
                        }
                    }
                }
            });
            newCalendar.setReadOnly(true);
            calendarSource.getCalendars().add(newCalendar);
        });

    }

    /**
     * Kreiert entry von booking und setzt konvertierte Recurrence Rule von Booking
     *
     * @param booking
     * @return
     */
    private Entry<Booking> createEntryForBooking(Booking booking) {
        Entry<Booking> newEntry = new Entry<>(booking.getCourse().getProgram().getProgramName());
        newEntry.setLocation(booking.getRoom().getLocation().getStreet());
        newEntry.setUserObject(booking);
        //Recurrence-Rule logik
        if (!booking.getRecurrenceRule().equals("Einzeltermin")) {
            BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
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
        return newEntry;
    }
}
