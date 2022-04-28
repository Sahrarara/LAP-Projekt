package com.lap.lapproject.model;

public abstract class User {
    private String username;
    private String fName;
    private String lName;
    private String authority;
    private Boolean adminPrivilege;
    private String description;
    private int phoneNmbr;
    private String email;
    private byte[] photo;
    private Boolean descriptionVisibility;
    private Boolean phoneNmbrVisibility;
    private Boolean emailVisibility;
    private Boolean photoVisibility;

    protected User(String username, String fName, String lName, String authority, Boolean adminPrivilege, String description, int phoneNmbr, String email, byte[] photo, Boolean descriptionVisibility, Boolean phoneNmbrVisibility, Boolean emailVisibility, Boolean photoVisibility) {
        this.username = username;
        this.fName = fName;
        this.lName = lName;
        this.authority = authority;
        this.adminPrivilege = adminPrivilege;
        this.description = description;
        this.phoneNmbr = phoneNmbr;
        this.email = email;
        this.photo = photo;
        this.descriptionVisibility = descriptionVisibility;
        this.phoneNmbrVisibility = phoneNmbrVisibility;
        this.emailVisibility = emailVisibility;
        this.photoVisibility = photoVisibility;
    }


    protected User(String username, String fName, String lName, String authority, Boolean adminPrivilege) {
        this.username = username;
        this.fName = fName;
        this.lName = lName;
        this.authority = authority;
        this.adminPrivilege = adminPrivilege;
        this.email = email;
        this.phoneNmbr = phoneNmbr;
        this.description = description;
    }

    protected User(){}

    protected String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    public String getfName() {
        return fName;
    }

    protected void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    protected void setlName(String lName) {
        this.lName = lName;
    }

    public String getAuthority() {
        return authority;
    }

    protected void setAuthority(String authority) {
        this.authority = authority;
    }

    public Boolean getAdminPrivilege() {
        return adminPrivilege;
    }

    protected void setAdminPrivilege(Boolean adminPrivilege) {
        this.adminPrivilege = adminPrivilege;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public int getPhoneNmbr() {
        return phoneNmbr;
    }

    @Override
    public String toString() {
        return "" + phoneNmbr;
    }

    protected void setPhoneNmbr(int phoneNmbr) {
        this.phoneNmbr = phoneNmbr;
    }

    public String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDescriptionVisibility() {
        return descriptionVisibility;
    }

    protected void setDescriptionVisibility(Boolean descriptionVisibility) {
        this.descriptionVisibility = descriptionVisibility;
    }

    protected Boolean getPhoneNmbrVisibility() {
        return phoneNmbrVisibility;
    }

    protected void setPhoneNmbrVisibility(Boolean phoneNmbrVisibility) {
        this.phoneNmbrVisibility = phoneNmbrVisibility;
    }

    protected Boolean getEmailVisibility() {
        return emailVisibility;
    }

    protected void setEmailVisibility(Boolean emailVisibility) {
        this.emailVisibility = emailVisibility;
    }

    protected Boolean getPhotoVisibility() {
        return photoVisibility;
    }

    protected void setPhotoVisibility(Boolean photoVisibility) {
        this.photoVisibility = photoVisibility;
    }

    protected byte[] getPhoto() {
        return photo;
    }

    protected void setPhoto(byte[] photo) {
        this.photo = photo;
    }

}
