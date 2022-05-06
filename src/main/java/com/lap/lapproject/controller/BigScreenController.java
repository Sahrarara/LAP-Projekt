package com.lap.lapproject.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BigScreenController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(BigScreenController.class);

    @FXML
    private BorderPane borderPane;

    private void loadFXMLInBorderPaneCenter(String fxmlPath) throws IOException {
        log.info("Tryint to load path: {}", fxmlPath);
        Parent newCenterRoot = FXMLLoader.load(getClass().getResource(fxmlPath));
        borderPane.setCenter(newCenterRoot);
    }

    @FXML
    private void initialize(){
        log.info("initialized");
        model.pathForDetailViewProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (newValue != null){
                    try{
                        loadFXMLInBorderPaneCenter(newValue);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        log.info("Listener registered");
    }

}
