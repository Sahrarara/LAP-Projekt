package com.lap.lapproject.utility;

import com.lap.lapproject.application.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

//        return BCrypt.hashpw(password, BCrypt.gensalt());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(plainNewPassword);
        return hashedPassword;
    }


    // compare plainPassword and hashedPassword
    public static boolean checkPass(String plainPassword, String hashedPassword) {

        return BCrypt.checkpw(plainPassword, hashedPassword);

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder.matches(plainPassword, hashedPassword);

    }




}
