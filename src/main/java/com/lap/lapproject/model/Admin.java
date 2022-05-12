package com.lap.lapproject.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Admin extends Trainer {

    private static final Logger log = LoggerFactory.getLogger(Admin.class);

    public Admin(String username, String fName, String lName, String authority, String email, String phoneNmbr,
                 String description) {
        super(username, fName, lName, authority, email, phoneNmbr, description);
    }

   /* public Admin(int id, String username, byte[] photos) {
        super(id, username, photos);
    }*/

    public Admin() {
        super();
    }
}
