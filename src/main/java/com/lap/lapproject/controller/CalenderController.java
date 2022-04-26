package com.lap.lapproject.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.lap.lapproject.model.Booking;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalenderController extends BaseController{
    @FXML
    private BorderPane borderCalender;

    @FXML
    private void initialize(){
        loadCalendarFXViewInBorderPaneCenter();
    }
    private void loadCalendarFXViewInBorderPaneCenter() {
        System.setProperty("calendarfx.developer", "true");

        CalendarView calendarView = new CalendarView();
        Calendar birthdays = new Calendar("Birthdays");
        Calendar holidays = new Calendar("Holidays");

        birthdays.setStyle(Calendar.Style.STYLE5);
        holidays.setStyle(Calendar.Style.STYLE2);



        model.bookings.forEach(element ->{
            Entry<Booking> entry = new Entry<>(element.getCourse().getCourseName());
            Interval interval = new Interval(element.getDateTimeStart(), element.getDateTimeEnd().plusDays(1), ZoneId.systemDefault());
            entry.setInterval(interval);
            birthdays.addEntries(entry);
        });

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(birthdays, holidays);
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
            };
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
        calendarView.setShowDeveloperConsole(true);
        borderCalender.setCenter(calendarView);
    }

}
