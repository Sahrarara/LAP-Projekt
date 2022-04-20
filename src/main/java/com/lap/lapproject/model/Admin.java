package com.lap.lapproject.model;

public class Admin extends Trainer{
    public Admin(String username, String fName, String lName, String authority, Boolean adminPrivilege, String email, int phoneNmbr, String description) {
        super(username, fName, lName, authority, adminPrivilege, email, phoneNmbr, description);
    }

}
