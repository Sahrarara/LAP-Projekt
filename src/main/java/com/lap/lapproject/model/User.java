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
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
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

    protected User(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    //konstruktor für choiceBox befüllen in Formular addUser
    public User(String authority) {
        this.authority = new SimpleStringProperty(authority);
    }

    // addUser konstruktor für Formular
    public User(String username, String title, Boolean activeStatus,String firstName, String lastName,
                String userPassword,
                String authority, String description, String phoneNmbr,
                String email, byte[] photo, Boolean descriptionVisibility,
                Boolean phoneNmbrVisibility, Boolean emailVisibility, Boolean photoVisibility) {
        this.id = new SimpleIntegerProperty();
        this.username = new SimpleStringProperty(username);
        this.activeStatus = new SimpleBooleanProperty(activeStatus);
        this.title = new SimpleStringProperty(title);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
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

    public User(int id, String username, String firstName, String lastName, String authority, String email, String phoneNmbr, String description, Boolean activeStatus) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.authority = new SimpleStringProperty(authority);
        this.email = new SimpleStringProperty(email);
        this.phoneNmbr = new SimpleStringProperty(phoneNmbr);
        this.description = new SimpleStringProperty(description);
        this.activeStatus = new SimpleBooleanProperty(activeStatus);
    }

    // UserRepositoryJDBC
    public User(int id, String username, String title, Boolean activeStatus,String firstName, String lastName,
                String userPassword,
                String authority, String description, String phoneNmbr,
                String email, byte[] photo, Boolean descriptionVisibility,
                Boolean phoneNmbrVisibility, Boolean emailVisibility, Boolean photoVisibility) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.activeStatus = new SimpleBooleanProperty(activeStatus);
        this.title = new SimpleStringProperty(title);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
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

   /* // ProfileController
    public User(int id, String description, String phoneNmbr, String email, byte[] photo) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.phoneNmbr = new SimpleStringProperty(phoneNmbr);
        this.email = new SimpleStringProperty(email);
        this.photo = photo;
    }*/
   // ProfileController //TODO
   public User(int id, String description, String phoneNmbr, String email, byte[] photo, Boolean photoVisibility) {
       this.id = new SimpleIntegerProperty(id);
       this.description = new SimpleStringProperty(description);
       this.phoneNmbr = new SimpleStringProperty(phoneNmbr);
       this.email = new SimpleStringProperty(email);
       this.photo = photo;
       this.photoVisibility = new SimpleBooleanProperty(photoVisibility);
   }

    @Override
    public boolean equals (Object r) {
        if(!(r instanceof User)) {
            return false;
        }
        User u1 = (User) r;
        boolean b =  this.id.getValue().equals(u1.id.getValue());
        return b;
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
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getlName() {
        return lastName.get();
    }

    public SimpleStringProperty lNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
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
