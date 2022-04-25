module com.lap.lapproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires com.calendarfx.view;


    opens com.lap.lapproject to javafx.fxml;
    exports com.lap.lapproject;
    exports com.lap.lapproject.controller;
    opens com.lap.lapproject.controller to javafx.fxml;
    exports com.lap.lapproject.application;
    opens com.lap.lapproject.application to javafx.fxml;
}