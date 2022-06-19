module com.lap.lapproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires com.calendarfx.view;
    requires org.kordamp.ikonli.fontawesome;
    requires java.desktop;

    requires spring.security.core;
    requires spring.security.crypto;
    requires junit;

    opens com.lap.lapproject to javafx.fxml;
    exports com.lap.lapproject;
    exports com.lap.lapproject.controller;
    opens com.lap.lapproject.controller to javafx.fxml;
    exports com.lap.lapproject.application;
    opens com.lap.lapproject.application to javafx.fxml;
    exports com.lap.lapproject.utility;
    opens com.lap.lapproject.utility to javafx.fxml;


//Negin
    exports com.lap.lapproject.repos to junit;
}