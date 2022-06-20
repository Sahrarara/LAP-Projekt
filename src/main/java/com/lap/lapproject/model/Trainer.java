package com.lap.lapproject.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Trainer extends User {

    private static final Logger log = LoggerFactory.getLogger(Trainer.class);


    public Trainer(String authority) {
        super(authority);
    }

    public Trainer(int id) {
        super(id);
    }

    //BookingRepositoryJDBC
    public Trainer(int id, String username, String firstName, String lastName, String authority, String email, String phoneNmbr, String description, Boolean activeStatus) {
        super(id, username, firstName, lastName, authority, email, phoneNmbr, description, activeStatus);
    }

    //UserRepositoryJDBC
    public Trainer(int id, String username, String title, Boolean activeStatus, String firstName, String lastName,
                   String userPassword,String authority, String description, String phoneNmbr, String email, byte[] photo,
                   Boolean descriptionVisibility, Boolean phoneNmbrVisibility, Boolean emailVisibility, Boolean photoVisibility) {
        super(id, username, title, activeStatus, firstName, lastName,userPassword, authority, description, phoneNmbr,
                email, photo, descriptionVisibility, phoneNmbrVisibility, emailVisibility, photoVisibility);
    }

    // konstruktor addUser
    public Trainer(String username, String title, Boolean activeStatus, String fName, String lName,
                   String userPassword,String authority, String description, String phoneNmbr,
                   String email, byte[] photo, Boolean descriptionVisibility,
                   Boolean phoneNmbrVisibility, Boolean emailVisibility, Boolean photoVisibility) {
        super(username, title, activeStatus, fName, lName, userPassword, authority, description, phoneNmbr, email,
                photo, descriptionVisibility, phoneNmbrVisibility, emailVisibility,photoVisibility);
    }


    public Trainer(int id, String description, String phoneNmbr, String email, byte[] photo, Boolean photoVisibility) {
        super(id, description, phoneNmbr, email, photo, photoVisibility);
    }
}





















