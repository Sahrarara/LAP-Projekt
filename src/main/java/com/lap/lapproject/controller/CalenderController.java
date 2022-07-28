package com.lap.lapproject.controller;

import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.*;

/**
 * Diese Klasse extends BaseController
 */
public class CalenderController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CalenderController.class);
    @FXML
    private BorderPane borderCalender;

    private static Map<Integer, Map<String, GregorianCalendar>> holidayMap = new HashMap<>();

    /**
     * Ruft die loadCalendarFXViewInBorderPaneCenter() Methode auf welche die Ansicht im Kalender prüft
     */
    @FXML
    private void initialize() {loadCalendarFXViewInBorderPaneCenter();
    }

    private void loadCalendarFXViewInBorderPaneCenter() {

        bookingModel.loadBookingIntoCalendar();

        CalendarView calendarView = new CalendarView();
        calendarView.getCalendarSources().get(0).getCalendars().get(0).setReadOnly(true);
        calendarView.getCalendarSources().addAll(bookingModel.getCalendarSource());
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
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
//        calendarView.setShowDeveloperConsole(true);
        borderCalender.setCenter(calendarView);
    }

    /**
     * Prüft auf Wochenenden und Feiertage
     *
     * @param date erwartet ein LocalDate
     * @return
     */
    public static boolean isHolidayOrWeekend(LocalDate date) {
        return (isWeekend(date) || isHoliday(date));

    }

    /**
     * Prüft ob der übergebene Tag (LocalDate Parameter) ein Samstag oder Sonntag ist.
     *
     * @param ldate erwartet ein LocalDate
     * @return
     */
    private static boolean isWeekend(LocalDate ldate) {
        Integer day = new GregorianCalendar(ldate.getYear(), (ldate.getMonthValue() - 1), ldate.getDayOfMonth()).get(GregorianCalendar.DAY_OF_WEEK);
        return day.equals(Calendar.SUNDAY) || day.equals(Calendar.SATURDAY);
    }


    /**
     * Es wird mit der Gausschen "Osterregel" alle Feiertage heraus gefiltert, welche die keine veränderlichen Tage sind wie z.B. Neujahr, sind hardgecodet geschrieben.
     *
     * @param date - Ein LocalDate wird erwartet
     * @return
     */
    private static boolean isHoliday(LocalDate date) {

        int year = date.getYear();
        GregorianCalendar gCDate = new GregorianCalendar(year, (date.getMonthValue() - 1), date.getDayOfMonth());

        int a = year % 19;
        int b = year % 4;
        int c = year % 7;
        int month = 0;
        int m = (8 * (year / 100) + 13) / 25 - 2;
        int s = year / 100 - year / 400 - 2;
        m = (15 + s - m) % 30;
        int n = (6 + s) % 7;
        int d = (m + 19 * a) % 30;
        if (d == 29)
            d = 28;
        else if (d == 28 && a >= 11)
            d = 27;
        int e = (2 * b + 4 * c + 6 * d + n) % 7;
        int day = 21 + d + e + 1;
        if (day > 31) {
            day = day % 31;
            month = 3;
        } else if (day <= 31) {
            month = 2;
        }


        if (!holidayMap.containsKey(Integer.valueOf(year))) {

            Map<String, GregorianCalendar> holiday = new HashMap<>();

            GregorianCalendar gc_eastersunday = new GregorianCalendar(year, month, day);
            holiday.put("easterSunday", gc_eastersunday);
            GregorianCalendar gc_ostermonday = new GregorianCalendar(gc_eastersunday.get(Calendar.YEAR), gc_eastersunday.get(Calendar.MONTH), (gc_eastersunday.get(Calendar.DATE) + 1));
            holiday.put("easterMonday", gc_ostermonday);
            GregorianCalendar gc_karfriday = new GregorianCalendar(gc_eastersunday.get(Calendar.YEAR), gc_eastersunday.get(Calendar.MONTH), (gc_eastersunday.get(Calendar.DATE) - 2));
            holiday.put("karfriday", gc_karfriday);
            GregorianCalendar gc_rosemonday = new GregorianCalendar(gc_eastersunday.get(Calendar.YEAR), gc_eastersunday.get(Calendar.MONTH), (gc_eastersunday.get(Calendar.DATE) - 48));
            holiday.put("rosemonday", gc_rosemonday);
            GregorianCalendar gc_ascensionDay = new GregorianCalendar(gc_eastersunday.get(Calendar.YEAR), gc_eastersunday.get(Calendar.MONTH), (gc_eastersunday.get(Calendar.DATE) + 39));
            holiday.put("ascensionDay", gc_ascensionDay);
            GregorianCalendar gc_whitSunday = new GregorianCalendar(gc_eastersunday.get(Calendar.YEAR), gc_eastersunday.get(Calendar.MONTH), (gc_eastersunday.get(Calendar.DATE) + 49));
            holiday.put("whitSunday", gc_whitSunday);
            GregorianCalendar gc_whitMonday = new GregorianCalendar(gc_eastersunday.get(Calendar.YEAR), gc_eastersunday.get(Calendar.MONTH), (gc_eastersunday.get(Calendar.DATE) + 50));
            holiday.put("whitMonday", gc_whitMonday);
            GregorianCalendar gc_corpusChristy = new GregorianCalendar(gc_eastersunday.get(Calendar.YEAR), (gc_eastersunday.get(Calendar.MONTH) - 1), (gc_eastersunday.get(Calendar.DATE) + 60));
            holiday.put("corpusChristy", gc_corpusChristy);
            GregorianCalendar gc_laborDay = new GregorianCalendar(year, 4, 1);
            holiday.put("laborDay", gc_laborDay);
            GregorianCalendar gc_nationaHoliday = new GregorianCalendar(year, 9, 26);
            holiday.put("nationaHoliday", gc_nationaHoliday);
            GregorianCalendar gc_immaculateConception = new GregorianCalendar(year, 11, 8);
            holiday.put("immaculateConception", gc_immaculateConception);
            GregorianCalendar gc_christmasday_1 = new GregorianCalendar(gc_eastersunday.get(Calendar.YEAR), 11, 25);
            holiday.put("christmasday_1", gc_christmasday_1);
            GregorianCalendar gc_christmaseve_2 = new GregorianCalendar(gc_eastersunday.get(Calendar.YEAR), 11, 26);
            holiday.put("christmaseve_2", gc_christmaseve_2);
            GregorianCalendar gc_silvester = new GregorianCalendar(gc_eastersunday.get(Calendar.YEAR), 11, 31);
            holiday.put("silvester", gc_silvester);
            GregorianCalendar newyear = new GregorianCalendar(gc_silvester.get(Calendar.YEAR), 0, 1);
            holiday.put("newyear", newyear);
            GregorianCalendar epiphany = new GregorianCalendar(year, 0, 6);
            holiday.put("epiphany", epiphany);
            holidayMap.put(year, holiday);

        }
        Map<String, GregorianCalendar> holiday = holidayMap.get(year);

        GregorianCalendar gc_eastersunday = holiday.get("easterSunday");
        GregorianCalendar gc_ostermonday = holiday.get("easterMonday");
        GregorianCalendar gc_karfriday = holiday.get("karfriday");
        GregorianCalendar gc_rosemonday = holiday.get("rosemonday");
        GregorianCalendar gc_ascensionDay = holiday.get("ascensionDay");
        GregorianCalendar gc_whitSunday = holiday.get("whitSunday");
        GregorianCalendar gc_whitMonday = holiday.get("whitMonday");
        GregorianCalendar gc_corpusChristy = holiday.get("corpusChristy");
        GregorianCalendar gc_laborDay = holiday.get("laborDay");
        GregorianCalendar gc_nationaHoliday = holiday.get("nationaHoliday");
        GregorianCalendar gc_immaculateConception = holiday.get("immaculateConception");
        GregorianCalendar gc_christmasday_1 = holiday.get("christmasday_1");
        GregorianCalendar gc_christmaseve_2 = holiday.get("christmaseve_2");
        GregorianCalendar gc_silvester = holiday.get("silvester");
        GregorianCalendar newyear = holiday.get("newyear");
        GregorianCalendar epiphany = holiday.get("epiphany");

        if (gc_ostermonday.equals(gCDate) || gc_karfriday.equals(gCDate) || gc_rosemonday.equals(gCDate)
                || gc_ascensionDay.equals(gCDate) || gc_whitMonday.equals(gCDate) || gc_corpusChristy.equals(gCDate)
                || gc_christmasday_1.equals(gCDate) || gc_christmaseve_2.equals(gCDate) || gc_silvester.equals(gCDate)
                || newyear.equals(gCDate) || gc_nationaHoliday.equals(gCDate) || gc_laborDay.equals(gCDate)
                || gc_immaculateConception.equals(gCDate) || epiphany.equals(gCDate)) {

            return true;
        } else {
            return false;
        }
    }

}
