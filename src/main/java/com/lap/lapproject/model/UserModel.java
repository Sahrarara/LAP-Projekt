package com.lap.lapproject.model;

public abstract class UserModel {
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

    protected String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected String getfName() {
        return fName;
    }

    protected void setfName(String fName) {
        this.fName = fName;
    }

    protected String getlName() {
        return lName;
    }

    protected void setlName(String lName) {
        this.lName = lName;
    }

    protected String getAuthority() {
        return authority;
    }

    protected void setAuthority(String authority) {
        this.authority = authority;
    }

    protected Boolean getAdminPrivilege() {
        return adminPrivilege;
    }

    protected void setAdminPrivilege(Boolean adminPrivilege) {
        this.adminPrivilege = adminPrivilege;
    }

    protected String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected int getPhoneNmbr() {
        return phoneNmbr;
    }

    protected void setPhoneNmbr(int phoneNmbr) {
        this.phoneNmbr = phoneNmbr;
    }

    protected String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    protected Boolean getDescriptionVisibility() {
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

    protected UserModel(String username, String fName, String lName, String authority, Boolean adminPrivilege) {
        this.username = username;
        this.fName = fName;
        this.lName = lName;
        this.authority = authority;
        this.adminPrivilege = adminPrivilege;
    }

    protected UserModel(String username, String fName, String lName, String authority, Boolean adminPrivilege, String description, int phoneNmbr, String email, byte[] photo, Boolean descriptionVisibility, Boolean phoneNmbrVisibility, Boolean emailVisibility, Boolean photoVisibility) {
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
}
