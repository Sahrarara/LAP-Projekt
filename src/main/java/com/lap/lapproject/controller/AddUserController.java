package com.lap.lapproject.controller;

import com.lap.lapproject.utility.PasswordSecurity;
import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.utility.UsabilityMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddUserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AddUserController.class);
    public Button addAndSaveBtn;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private ChoiceBox authorizationChoiceBox;
    @FXML
    private TextField passwordTextFieldHidden;
    @FXML
    private TextField passwordText;
    @FXML
    private CheckBox showPassword;
    @FXML
    private TextField emailTextField;
    @FXML
    private CheckBox emailCheckBox;
    @FXML
    private TextField phoneNmbrTextField;
    @FXML
    private CheckBox phoneNmbrCheckBox;
    @FXML
    private CheckBox activeCheckBox;
    @FXML
    private TextField photoPathTextField;
    @FXML
    private CheckBox photoCheckBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private CheckBox descriptionCheckBox;
    @FXML
    private Label errorUsername;
    @FXML
    private Label errorPassword;
    @FXML
    private Label errorEmail;
    @FXML
    File file;
    @FXML
    private Label errorNoPhotoInDB;


    @FXML
    private void onFileBtnClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        // Öffnet einen Öffnen-Dialog
        file = fileChooser.showOpenDialog(getCurrentStage());
        try {
            if (file != null) {
                //zeigt Pfad ins Fenster
                photoPathTextField.setText(file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private Stage getCurrentStage() {
        return (Stage) firstNameTextField.getScene().getWindow();
    }

    //Zeigt passwort in Klartext
    @FXML
    public void changeVisibility(ActionEvent event) {
        if (showPassword.isSelected()) {
            showPassword.setText("Verstecken");
            passwordText.setText(passwordTextFieldHidden.getText());
            passwordText.setVisible(true);
            passwordTextFieldHidden.setVisible(false);
        } else if (!showPassword.isSelected()) {
            showPassword.setText("Anzeigen  ");
            passwordTextFieldHidden.setText(passwordText.getText());
            passwordText.setVisible(false);
            passwordTextFieldHidden.setVisible(true);
        }
    }


    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException, IOException {
        userAddOrUpdate();    //diese funktion hier wechselt nur je nach dem Text im Button zu der update oder add
    }


    private void userAddOrUpdate() throws SQLException, IOException {
        String btnText = addAndSaveBtn.getText().toLowerCase(Locale.ROOT);
        switch (btnText) {
            case "hinzufügen":
                addNewUser();
                break;
            case "speichern":
                updateUser();
                break;
        }
    }

    private void addNewUser() throws SQLException, IOException {
        String username = usernameTextField.getText();
        boolean active = activeCheckBox.isSelected();
        String title = titleTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();

        String password = !showPassword.isSelected() ? passwordTextFieldHidden.getText() : passwordText.getText();

        String authorization = String.valueOf(authorizationChoiceBox.getValue());
        String description = descriptionTextArea.getText();
        String telephone = phoneNmbrTextField.getText();
        String email = emailTextField.getText();
        String photoPath = file == null ? "" : file.getPath();
        boolean descriptionVisible = descriptionCheckBox.isSelected();
        boolean telephoneVisible = phoneNmbrCheckBox.isSelected();
        boolean emailVisible = emailCheckBox.isSelected();
        boolean photoVisible = photoCheckBox.isSelected();


        //neuen User im Datenbank speichern
        //UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();

        if (!username.isBlank() && !firstName.isBlank() && !lastName.isBlank()
                && !(authorizationChoiceBox.getValue() == null) &&
                !password.isBlank() && !email.isBlank() && !telephone.isBlank()
        ) {
            if (listModel.getSelectedUser() == null) {
                if (checkUser(username) && PasswordSecurity.isPasswordValid(password, errorPassword) && UsabilityMethods.isEmailValid(email)) {
                    Trainer trainer = new Trainer(username, title, active, firstName, lastName, password,
                            authorization, description, telephone, email,
                            photoPath.equals("") ? null : convertToBytes(photoPath),
                            descriptionVisible, telephoneVisible, emailVisible, photoVisible);
                    // userRepositoryJDBC.add(trainer);
                    listModel.trainerList.add(trainer);
                    getCurrentStage().close();
                }
            }
        } else {
            QuickAlert.showError("Bitte folgende Felder ausfüllen:\nNutzername\nVorname\nNachname\nAuthorization\npassword\ne-mail\nTelefon");
        }
    }

    private void updateUser() throws IOException, SQLException {

        if (!usernameTextField.getText().isBlank() && !firstNameTextField.getText().isBlank() && !lastNameTextField.getText().isBlank()
                && !(authorizationChoiceBox.getValue() == null) &&
                !emailTextField.getText().isBlank() && !phoneNmbrTextField.getText().isBlank()
        ) {
            //if (listModel.getSelectedUser() != null) {
            String newUsername = usernameTextField.getText();
            String selectedUserUsername = listModel.getSelectedUser().getUsername();

            if (!newUsername.equals(selectedUserUsername)) {
                if (checkUser(usernameTextField.getText())) {
                    setNewDataForTrainer();
                }
            } else {
                if (UsabilityMethods.isEmailValid(emailTextField.getText())) {
                    setNewDataForTrainer();
                }
                logger.info("Das hat nicht funktioniert");
            }

            //}

        } else {
            QuickAlert.showError("Bitte folgende Felder ausfüllen:\nNutzername\nVorname\nNachname\nAuthorization\ne-mail\nTelefon");
        }
    }


    public void setNewDataForTrainer() throws IOException {
        checkImageInDB();
        Trainer trainer = listModel.getSelectedUser();
        trainer.setUsername(usernameTextField.getText());
        trainer.setActiveStatus(activeCheckBox.isSelected());
        trainer.setTitle(titleTextField.getText());
        trainer.setFirstName(firstNameTextField.getText());
        trainer.setLastName(lastNameTextField.getText());

        if (!showPassword.isSelected()) {
            if (!passwordTextFieldHidden.getText().isBlank()) {
                trainer.setUserPassword(PasswordSecurity.hashPassword(passwordTextFieldHidden.getText()));
                //  logger.info("PASSWOR DHIDDEN{}", " " + passwordTextFieldHidden.getText());
            } else {
                trainer.setUserPassword(listModel.getSelectedUser().getUserPassword());
                //   logger.info("OLDPASSWORD HIDDEN{}", " " + listModel.getSelectedUser().getUserPassword());
            }
        } else {
            if (!passwordText.getText().isBlank()) {
                trainer.setUserPassword(PasswordSecurity.hashPassword(passwordText.getText()));
                // logger.info("PASSWORD PLAINTEXT{}", " " + passwordText.getText());
            } else {
                trainer.setUserPassword(listModel.getSelectedUser().getUserPassword());
                //  logger.info("OLDPASSWORD PLAINTEXT{}", " " + listModel.getSelectedUser().getUserPassword());
            }
        }

        trainer.setAuthority(String.valueOf(authorizationChoiceBox.getValue()));
        trainer.setPhoneNmbr(phoneNmbrTextField.getText());

        trainer.setEmail(emailTextField.getText());


        String photoPath = file == null ? "" : file.getPath();
        if (!photoPath.equals("")) {
            trainer.setPhoto(convertToBytes(photoPath));
        } else {
            trainer.setPhoto(listModel.getSelectedUser().getPhoto());
        }

        trainer.setDescription(descriptionTextArea.getText());
        trainer.setDescriptionVisibility(descriptionCheckBox.isSelected());
        trainer.setPhoneNmbrVisibility(phoneNmbrCheckBox.isSelected());
        trainer.setEmailVisibility(emailCheckBox.isSelected());
        trainer.setPhotoVisibility(photoCheckBox.isSelected());

        try {
            logger.info("Speichern nach Update");
            listModel.trainerList.set(listModel.trainerList.indexOf(trainer), trainer);
            getCurrentStage().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public byte[] convertToBytes(String pathToImage) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(pathToImage);
        byte[] imageBytes = fileInputStream.readAllBytes();
        return imageBytes;
    }




    @FXML
    void initialize() throws IOException {
        assert activeCheckBox != null : "fx:id=\"activeCheckBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert authorizationChoiceBox != null : "fx:id=\"authorizationChoiceBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert descriptionCheckBox != null : "fx:id=\"descriptionCheckBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert descriptionTextArea != null : "fx:id=\"descriptionTextArea\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert emailCheckBox != null : "fx:id=\"emailCheckBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert emailTextField != null : "fx:id=\"emailTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert firstNameTextField != null : "fx:id=\"firstNameTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert lastNameTextField != null : "fx:id=\"lastNameTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert passwordTextFieldHidden != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert phoneNmbrCheckBox != null : "fx:id=\"phoneNmbrCheckBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert phoneNmbrTextField != null : "fx:id=\"phoneNmbrTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert photoCheckBox != null : "fx:id=\"photoCheckBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert photoPathTextField != null : "fx:id=\"photoPathTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert titleTextField != null : "fx:id=\"titleTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";

        fillChoiceBox();
        textLabelInvisible();
        fillFormToUpdate();
        checkImageInDB();

    }


    //Befüllt ChoiceBox mit authorization
    @FXML
    public void fillChoiceBox() {
        ObservableList<String> authorizationName = FXCollections.observableArrayList(
                listModel.filteredAuthorizationList.stream()
                        .map(authorization -> authorization.getAuthority())
                        .collect(Collectors.toList()));
        authorizationChoiceBox.setItems(authorizationName);
    }

    // Macht Text Feldär unsichtbar
    @FXML
    public void textLabelInvisible() {
        //setzt Label auf unsichtbar
        errorUsername.setVisible(false);
        errorPassword.setVisible(false);
        passwordText.setVisible(false);
        errorEmail.setVisible(false);

        errorNoPhotoInDB.setVisible(false);

    }

    //Prüft, ob Username schon in Datenbank vorhanden ist
    @FXML
    public boolean checkUser(String username) throws SQLException {

        UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
        if (userRepositoryJDBC.checkUniqueUsername(username)) {
//            logger.info("newUsername is unique: {}", userRepositoryJDBC.checkUniqueUsername(username));
            errorUsername.setVisible(false);
            return true;
        } else {
            errorUsername.setVisible(true);
            errorUsername.setText("Benutzername existiert bereits");
            return false;
        }
    }



    //Email validieren
//    @FXML
//    public boolean checkEmail(String email) {
//        Pattern compile = Pattern.compile("[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,}){1}");
//        Matcher matcher = compile.matcher(email);
//        boolean isEmailTrue = matcher.matches();
//        if (!matcher.matches()) {
//            logger.info("ICH BIN HIER");
//            errorEmail.setVisible(true);
//            errorEmail.setText("Email nicht gültig");
//            return false;
//        } else {
//            errorEmail.setVisible(false);
//            return true;
//        }
//    }

    //prüft ob ein Foto in DB vorhanden ist
    public  boolean checkImageInDB() throws IOException {
        while (listModel.getSelectedUser() != null){
            if (listModel.getSelectedUser().getPhoto() == null) {
                System.out.println("foto empty");
                errorNoPhotoInDB.setVisible(true);
                errorNoPhotoInDB.setText("Das Foto ist nicht in der Datenbank gespeichert!");
                return false;
            } else {
                errorNoPhotoInDB.setVisible(false);
                return true;
            }
        }
        return true;
    }



    //Befüllt das Formular für Update Function
    @FXML
    public void fillFormToUpdate() {
        if (listModel.getSelectedUser() != null) {
            addAndSaveBtn.setText("SPEICHERN");
            usernameTextField.setText(listModel.getSelectedUser().getUsername());
            titleTextField.setText(listModel.getSelectedUser().getTitle());
            activeCheckBox.setSelected(listModel.getSelectedUser().getActiveStatus());
            firstNameTextField.setText(listModel.getSelectedUser().getfName());
            lastNameTextField.setText(listModel.getSelectedUser().getlName());
            authorizationChoiceBox.setValue(listModel.getSelectedUser().getAuthority());
            descriptionTextArea.setText(listModel.getSelectedUser().getDescription());
            phoneNmbrTextField.setText(listModel.getSelectedUser().getPhoneNmbr());
            emailTextField.setText(listModel.getSelectedUser().getEmail());
            descriptionCheckBox.setSelected(listModel.getSelectedUser().getDescriptionVisibility());
            phoneNmbrCheckBox.setSelected(listModel.getSelectedUser().getPhoneNmbrVisibility());
            emailCheckBox.setSelected(listModel.getSelectedUser().getEmailVisibility());
            photoCheckBox.setSelected(listModel.getSelectedUser().getPhotoVisibility());
        }

    }


}