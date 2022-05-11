package com.lap.lapproject.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Trainer extends User {

    private static final Logger log = LoggerFactory.getLogger(Trainer.class);


    public Trainer(int id, String fName, String lName, String email, String phoneNmbr, Boolean activeStatus) {
        super(id, fName, lName, email, phoneNmbr, activeStatus);
    }

    public Trainer() {

    }

    public Trainer(String authority) {
        super(authority);
    }

    public Trainer(String username, String fName, String lName, String authority, String email, String phoneNmbr, String description) {
        super(username, fName, lName, authority, email, phoneNmbr, description);

    }

    // konstruktor addUser
    public Trainer(String username, String title, Boolean activeStatus, String fName, String lName,
                   String userPassword,String authority, String description, String phoneNmbr,
                   String email, byte[] photo, Boolean descriptionVisibility,
                   Boolean phoneNmbrVisibility, Boolean emailVisibility, Boolean photoVisibility) {
        super(username, title, activeStatus, fName, lName, userPassword, authority, description, phoneNmbr, email,
                photo, descriptionVisibility, phoneNmbrVisibility, emailVisibility,photoVisibility);
    }

}
