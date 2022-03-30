package com.lap.lapproject.model;

public class Admin extends Trainer{
    public Admin(String username, String fName, String lName, String authority, Boolean adminPrivilege) {
        super(username, fName, lName, authority, adminPrivilege);
    }

    public Admin(String username, String fName, String lName, String authority, Boolean adminPrivilege, String description, int phoneNmbr, String email, Boolean descriptionVisibility, Boolean phoneNmbrVisibility, Boolean emailVisibility, Boolean photoVisibility) {
        super(username, fName, lName, authority, adminPrivilege, description, phoneNmbr, email, descriptionVisibility, phoneNmbrVisibility, emailVisibility, photoVisibility);
    }
}
