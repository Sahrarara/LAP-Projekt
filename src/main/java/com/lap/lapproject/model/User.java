package com.lap.lapproject.model;

public abstract class User {
    private long id;
    private String username;
    private Boolean activeStatus;
    private String fName;
    private String lName;
    private String authority;
    private String description;
    private String phoneNmbr;
    private String email;
    private byte[] photo;
    private Boolean descriptionVisibility;
    private Boolean phoneNmbrVisibility;
    private Boolean emailVisibility;
    private Boolean photoVisibility;

    public User(String username, String fName, String lName, String authority, String description, String phoneNmbr,
                String email, byte[] photo, Boolean descriptionVisibility, Boolean phoneNmbrVisibility, Boolean emailVisibility, Boolean photoVisibility) {
        this.username = username;
        this.fName = fName;
        this.lName = lName;
        this.authority = authority;
        this.description = description;
        this.phoneNmbr = phoneNmbr;
        this.email = email;
        this.photo = photo;
        this.descriptionVisibility = descriptionVisibility;
        this.phoneNmbrVisibility = phoneNmbrVisibility;
        this.emailVisibility = emailVisibility;
        this.photoVisibility = photoVisibility;
    }


    public User() {
    }

    public User(long id, String fName, String lName, String email,String phoneNmbr, Boolean activeStatus) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNmbr = phoneNmbr;
        this.activeStatus = activeStatus;
    }
    public User(String username, String fName, String lName, String authority) {
        this.username = username;
        this.fName = fName;
        this.lName = lName;
        this.authority = authority;
        this.email = email;
        this.phoneNmbr = phoneNmbr;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User(long aLong, String username, byte[] photos) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNmbr() {
        return phoneNmbr;
    }

    @Override
    public String toString() {
        return "" + phoneNmbr;
    }

    public void setPhoneNmbr(String phoneNmbr) {
        this.phoneNmbr = phoneNmbr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDescriptionVisibility() {
        return descriptionVisibility;
    }

    public void setDescriptionVisibility(Boolean descriptionVisibility) {
        this.descriptionVisibility = descriptionVisibility;
    }

    public Boolean getPhoneNmbrVisibility() {
        return phoneNmbrVisibility;
    }

    public void setPhoneNmbrVisibility(Boolean phoneNmbrVisibility) {
        this.phoneNmbrVisibility = phoneNmbrVisibility;
    }

    public Boolean getEmailVisibility() {
        return emailVisibility;
    }

    public void setEmailVisibility(Boolean emailVisibility) {
        this.emailVisibility = emailVisibility;
    }

    public Boolean getPhotoVisibility() {
        return photoVisibility;
    }

    public void setPhotoVisibility(Boolean photoVisibility) {
        this.photoVisibility = photoVisibility;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
}
