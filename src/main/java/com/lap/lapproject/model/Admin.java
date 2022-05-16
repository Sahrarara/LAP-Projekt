package com.lap.lapproject.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Admin extends Trainer {

    private static final Logger log = LoggerFactory.getLogger(Admin.class);

    // UserRepositoryJDBC
    public Admin(int id, String username, String title, Boolean activeStatus, String firstName, String lastName,
                   String authority, String description, String phoneNmbr, String email, byte[] photo,
                   Boolean descriptionVisibility, Boolean phoneNmbrVisibility, Boolean emailVisibility, Boolean photoVisibility) {
        super(id, username, title, activeStatus, firstName, lastName, authority, description, phoneNmbr,
                email, photo, descriptionVisibility, phoneNmbrVisibility, emailVisibility, photoVisibility);
    }


}
