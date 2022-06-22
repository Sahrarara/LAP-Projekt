package com.lap.lapproject.controller;

import com.calendarfx.model.*;
import com.calendarfx.model.Calendar;
import com.calendarfx.view.CalendarView;
import com.lap.lapproject.model.Booking;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class CalenderController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CalenderController.class);
    @FXML
    private BorderPane borderCalender;

    @FXML
    private void initialize() {
        loadCalendarFXViewInBorderPaneCenter();
    }

    private void loadCalendarFXViewInBorderPaneCenter() {




        CalendarSource myCalendarSource = new CalendarSource("My Calendars");


        System.setProperty("calendarfx.developer", "true");

        CalendarView calendarView = new CalendarView();
        Calendar birthdays = new Calendar("Birthdays");
        Calendar holidays = new Calendar("Holidays");

        birthdays.setStyle(Calendar.Style.STYLE5);
        holidays.setStyle(Calendar.Style.STYLE2);

        /*
        model.bookings.forEach(booking -> {
            System.out.println(booking.getCourse().getCourseName());
            String coursename = booking.getCourse().getCourseName();
            Calendar newCalendar = new Calendar(coursename);
        });
        */

        calendarView.getCalendarSources().get(0).getCalendars().get(0).setReadOnly(true);

        model.courses.forEach(course -> {
            String currentCourse = course.getCourseName();
            System.out.println(course.getCourseName());
            Calendar newCalendar = new Calendar(currentCourse);
            model.bookings.forEach(booking -> {
                if (booking.getCourse().getCourseName().equals(currentCourse)){
                    Entry<Booking> newEntry = new Entry<>(booking.getCourse().getProgram().getProgramName());
                     if (!booking.getRecurrenceRule().equals("keiner")){
                        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
                        String Date = booking.getDateTimeStart().toString().substring(0,10) + " " + booking.getDateTimeEnd().toString().substring(11,16);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime dailytime = LocalDateTime.parse(Date, formatter);
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
            myCalendarSource.getCalendars().add(newCalendar);

        });
/*
        model.bookings.forEach(element -> {
            Entry<Booking> entry = new Entry<>(element.getCourse().getCourseName());
            Interval interval = new Interval(element.getDateTimeStart(), element.getDateTimeEnd(),
                    ZoneId.systemDefault());

            entry.setInterval(interval);
            birthdays.addEntries(entry);
        });
*/
        //read only for calendarFX



       /* model.bookings.forEach(element -> {
            Entry<Booking> recurringEntry = new Entry<>(element.getCourse().getCourseName());
            recurringEntry.setInterval(element.getDateTimeStart(), element.getDateTimeEnd());
            recurringEntry.setRecurrenceRule(element.getRecurrenceRule());
            //recurringEntry.setRecurrenceRule("RRULE:FREQ=WEEKLY;INTERVAL=2;UNTIL=20220528");
            birthdays.addEntry(recurringEntry);
        });*/


        //birthdays.addEventHandler(CalendarEvent.ENTRY_ADDED, e -> handleEvent(e));

        /* String searchTerm = "P512";
        List<Entry<?>> result = birthdays.findEntries(searchTerm);
        log.info("THIS IS ENTRY FOR P512{}", result);*/


        /*Calendar calendar = new Calendar("Home");
        calendar.addEventHandler(CalendarEvent.ANY, evt -> handleEvent(evt));*/


        /*model.bookings.forEach(element -> {
            Map<LocalDate, List<Entry<?>>> results = birthdays.findEntries(LocalDate.from(element.getDateTimeStart()),
                    LocalDate.from(element.getDateTimeEnd()), ZoneId.of("Europe/London"));
        });*/

        /*Set<String> timeZoneIdentifiers = ZoneId.getAvailableZoneIds();
        log.info("TIME  ZONE:{}", timeZoneIdentifiers.toString());*/

    /*    ZoneId zoneId = ZoneId.of("Europe/Copenhagen");
        LocalTime id1 = LocalTime.now(zoneId);
        log.info("ZEITZONE{}: ", id1);*/

        /*for (Booking booking : listModel.bookingList) {
            Entry<Booking> myGroup = new Entry<>(booking.getCourse().getCourseName());
            LocalDateTime startTime = booking.getDateTimeStart();
            LocalDateTime endTime = booking.getDateTimeEnd();
            LocalDate date = booking.getDateTimeStart().toLocalDate();
            if (!startTime.isAfter(endTime)) {
                myGroup.setInterval(date);
                myGroup.setInterval(startTime, endTime);
                birthdays.addEntry(myGroup);
            }
        }*/


        calendarView.getCalendarSources().addAll(myCalendarSource);

        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

            ;
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
        calendarView.setShowDeveloperConsole(true);
        borderCalender.setCenter(calendarView);
    }

    /*private Object handleEvent(Object e) {


    }*/

}
