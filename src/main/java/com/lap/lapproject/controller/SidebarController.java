package com.lap.lapproject.controller;

import com.lap.lapproject.application.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SidebarController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(SidebarController.class);

    @FXML
    void onCoreDataButtonPressed(ActionEvent event){
        log.debug("Button pressed");
        model.setPathForDetailView(Constants.PATH_TO_FXML_COREDATA);
    }
}
