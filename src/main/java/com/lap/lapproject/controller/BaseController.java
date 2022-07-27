package com.lap.lapproject.controller;

import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Model;

/**
 * Diese Klasse Instanziiert die Klassen Model und ListModel
 */
public class BaseController {
    static final Model model = new Model();
    static final ListModel listModel = new ListModel();
}
