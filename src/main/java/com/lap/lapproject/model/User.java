package com.lap.lapproject.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class User {

    private static final Logger log = LoggerFactory.getLogger(User.class);

    private SimpleIntegerProperty id;
    private SimpleStringProperty username;
    private SimpleStringProperty title;
    private SimpleBooleanProperty activeStatus;
    private SimpleStringProperty fName;
    private SimpleStringProperty lName;
    private SimpleStringProperty userPassword;
    private SimpleStringProperty authority;
    private SimpleStringProperty description;
    private SimpleStringProperty phoneNmbr;
    private SimpleStringProperty email;
    private byte[] photo;
    private SimpleBooleanProperty descriptionVisibility;
    private SimpleBooleanProperty phoneNmbrVisibility;
    private SimpleBooleanProperty emailVisibility;
    private SimpleBooleanProperty photoVisibility;


    public User() {
    }

    //konstruktor für choiceBox befüllen in Formular addUser
    public User(String authority) {
        this.authority = new SimpleStringProperty(authority);
    }

    // addUser konstruktor für Formular
    public User(String username, String title, Boolean activeStatus,String fName, String lName, String userPassword,
                String authority, String description, String phoneNmbr,
                String email, byte[] photo, Boolean descriptionVisibility,
                Boolean phoneNmbrVisibility, Boolean emailVisibility, Boolean photoVisibility) {
        this.username = new SimpleStringProperty(username);
        this.activeStatus = new SimpleBooleanProperty(activeStatus);
        this.title = new SimpleStringProperty(title);
        this.fName = new SimpleStringProperty(fName);
        this.lName = new SimpleStringProperty(lName);
        this.userPassword = new SimpleStringProperty(userPassword);
        this.authority = new SimpleStringProperty(authority);
        this.description = new SimpleStringProperty(description);
        this.phoneNmbr = new SimpleStringProperty(phoneNmbr);
        this.email = new SimpleStringProperty(email);
        this.photo = photo;
        this.descriptionVisibility = new SimpleBooleanProperty(descriptionVisibility);
        this.phoneNmbrVisibility = new SimpleBooleanProperty(phoneNmbrVisibility);
        this.emailVisibility = new SimpleBooleanProperty(emailVisibility);
        this.photoVisibility = new SimpleBooleanProperty(photoVisibility);
    }

    //konstruktor für add select Abfrage und Tabelle mit Trainer befüllen
    public User(int id, String fName, String lName, String email,String phoneNmbr, Boolean activeStatus) {
        this.id = new SimpleIntegerProperty(id);
        this.fName = new SimpleStringProperty(fName);
        this.lName = new SimpleStringProperty(lName);
        this.email = new SimpleStringProperty(email);
        this.phoneNmbr = new SimpleStringProperty(phoneNmbr);
        this.activeStatus = new SimpleBooleanProperty(activeStatus);
    }

    public User(String username, String fName, String lName, String authority, String email, String phoneNmbr, String description) {
        this.username = new SimpleStringProperty(username);
        this.fName = new SimpleStringProperty(fName);
        this.lName = new SimpleStringProperty(lName);
        this.authority = new SimpleStringProperty(authority);
        this.email = new SimpleStringProperty(email);
        this.phoneNmbr = new SimpleStringProperty(phoneNmbr);
        this.description = new SimpleStringProperty(description);
    }




    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public boolean getActiveStatus() {
        return activeStatus.get();
    }

    public SimpleBooleanProperty activeStatusProperty() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus.set(activeStatus);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getfName() {
        return fName.get();
    }

    public SimpleStringProperty fNameProperty() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName.set(fName);
    }

    public String getlName() {
        return lName.get();
    }

    public SimpleStringProperty lNameProperty() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName.set(lName);
    }

    public String getUserPassword() {
        return userPassword.get();
    }

    public SimpleStringProperty userPasswordProperty() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword.set(userPassword);
    }

    public String getAuthority() {
        return authority.get();
    }

    public SimpleStringProperty authorityProperty() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority.set(authority);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getPhoneNmbr() {
        return phoneNmbr.get();
    }

    public SimpleStringProperty phoneNmbrProperty() {
        return phoneNmbr;
    }

    public void setPhoneNmbr(String phoneNmbr) {
        this.phoneNmbr.set(phoneNmbr);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public boolean getDescriptionVisibility() {
        return descriptionVisibility.get();
    }

    public SimpleBooleanProperty descriptionVisibilityProperty() {
        return descriptionVisibility;
    }

    public void setDescriptionVisibility(boolean descriptionVisibility) {
        this.descriptionVisibility.set(descriptionVisibility);
    }

    public boolean getPhoneNmbrVisibility() {
        return phoneNmbrVisibility.get();
    }

    public SimpleBooleanProperty phoneNmbrVisibilityProperty() {
        return phoneNmbrVisibility;
    }

    public void setPhoneNmbrVisibility(boolean phoneNmbrVisibility) {
        this.phoneNmbrVisibility.set(phoneNmbrVisibility);
    }

    public boolean getEmailVisibility() {
        return emailVisibility.get();
    }

    public SimpleBooleanProperty emailVisibilityProperty() {
        return emailVisibility;
    }

    public void setEmailVisibility(boolean emailVisibility) {
        this.emailVisibility.set(emailVisibility);
    }

    public boolean getPhotoVisibility() {
        return photoVisibility.get();
    }

    public SimpleBooleanProperty photoVisibilityProperty() {
        return photoVisibility;
    }

    public void setPhotoVisibility(boolean photoVisibility) {
        this.photoVisibility.set(photoVisibility);
    }

    @Override
    public String toString() {
        return getfName() + " " + getlName();
    }

}
