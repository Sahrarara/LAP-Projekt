package com.lap.lapproject.model;

public class Admin extends Trainer{
    public Admin(String username, String fName, String lName, String authority, Boolean adminPrivilege, String email, int phoneNmbr, String description) {
        super(username, fName, lName, authority, adminPrivilege, email, phoneNmbr, description);
    }

    public Admin(long aLong, String username, byte[] photos) {
        super(aLong, username, photos);
    }

    public Admin() {
        super();
    }
}
