package com.lap.lapproject.model;

public class Trainer extends User {
    public Trainer(String username, String fName, String lName, String authority, String email, String phoneNmbr,
                   String description) {
        super(username, fName, lName, authority);
    }

    public Trainer(long aLong, String username, byte[] photos) {
        super(aLong, username, photos);
    }

    public Trainer() {
    }




    public Trainer(long id, String fName, String lName, String email, String phoneNmbr, Boolean activeStatus) {
        super(id, fName, lName, email, phoneNmbr, activeStatus);
    }
}
