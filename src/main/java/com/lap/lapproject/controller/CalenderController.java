package com.lap.lapproject.controller;

import com.calendarfx.model.*;
import com.calendarfx.model.Calendar;
import com.calendarfx.view.CalendarView;
import com.lap.lapproject.model.Booking;
import com.lap.lapproject.model.BookingModel;
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

/**
 * Diese Klasse extends BaseController
 */
public class CalenderController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CalenderController.class);
    @FXML
    private BorderPane borderCalender;

    /**
     * Ruft die loadCalendarFXViewInBorderPaneCenter() Methode auf welche die Ansicht im Kalender prüft
     */
    @FXML
    private void initialize() {
        loadCalendarFXViewInBorderPaneCenter();
    }

    private void loadCalendarFXViewInBorderPaneCenter() {

        /*
       Map<LocalDate, List<Entry<?>>> result = myCalendarSource.getCalendars().get(2).findEntries(startDate, endDate, ZoneId.systemDefault());
        System.out.println(result);

        Map<LocalDate, List<Entry<?>>> result2 = myCalendarSource.getCalendars().get(1).findEntries(LocalDate.of(2022,6,5), LocalDate.of(2022, 6, 23 ), ZoneId.systemDefault());

        for (LocalDate key : result2.keySet()){
            System.out.println(key + " = " + result2.get(key));
        }
     */
        model.bookingModel.loadBookingIntoCalendar();

        CalendarView calendarView = new CalendarView();
//        calendarView.getCalendarSources().get(0).getCalendars().get(0).setReadOnly(true);
//        calendarView.getCalendarSources().get(0).getCalendars().get(0);
        calendarView.getCalendarSources().addAll(model.bookingModel.getCalendarSource());
        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {

            /**
             * run() ist eine nested Methode
             * solange sie wahr ist wird die lokale Zeit und das lokale datum im Kalender gesetzt
             * es wird außerdem versucht alle 10 Sekunden upzudaten, falls nicht, wird eine InterruptedException geworfen
             */
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
//        calendarView.setShowDeveloperConsole(true);
        borderCalender.setCenter(calendarView);
    }

    /*private Object handleEvent(Object e) {


    }*/

}
