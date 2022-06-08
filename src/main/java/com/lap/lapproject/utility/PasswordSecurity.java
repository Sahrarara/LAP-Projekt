package com.lap.lapproject.utility;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordSecurity {

    private static final Logger logger = LoggerFactory.getLogger(PasswordSecurity.class);

    // pwDemo from Priebsch
    public static String pwDemo(String plainPassword, String plainNewPassword) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(plainNewPassword);

        logger.info("From Database: {}", hashedPassword);
        logger.info("Login successful: {}", encoder.matches(plainPassword, hashedPassword));

        return hashedPassword;
    }


    public static String hashPassword(String plainNewPassword) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(plainNewPassword);
        return hashedPassword;
    }


    // compare plainPassword and hashedPassword
    public static boolean checkPass(String plainPassword, String hashedPassword) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(plainPassword, hashedPassword);
    }



    // 1 Großbuchstabe, 1 Kleinbuchstabe, 1 Ziffer, 1 Sonderzeichen enthält und eine Länge von mindestens 8 hat
    @FXML
    public static boolean isPasswordValid(String password, Label label) {
        Pattern compile = Pattern.compile("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$");
        Matcher matcher = compile.matcher(password);
        if (!matcher.matches()) {
            label.setVisible(true);
            label.setText("8 Zeichen,1 Großbuchstabe, 1 Kleinbuchstabe, 1 Ziffer, 1 Sonderzeichen");
            return false;
        } else {
            label.setVisible(false);
            return true;
        }
    }


    @FXML
    public static boolean isPasswordValid(String password) {
        Pattern compile = Pattern.compile("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$");
        Matcher matcher = compile.matcher(password);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }




}
