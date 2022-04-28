package com.lap.lapproject.model;

public class Trainer extends User {
    public Trainer(String username, String fName, String lName, String authority, Boolean adminPrivilege, String email, int phoneNmbr, String description) {
        super(username, fName, lName, authority, adminPrivilege);
    }

    public Trainer(long aLong, String username, byte[] photos) {
        super(aLong, username, photos);
    }

    public Trainer() {

    }
}
