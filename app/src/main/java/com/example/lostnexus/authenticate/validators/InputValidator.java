package com.example.lostnexus.authenticate.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    public static  boolean isContactValid(String contact)
    {

        String regex = "[^\\d]";
        String PhoneDigits = contact.toString().replaceAll(regex, "");
        return ( !contact.equals("") && PhoneDigits.length() >= 10);

    }

    public static  boolean isEmailValid(String email)
    {
        String regex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return (!email.equals("") && email.toString().matches(regex));
    }
    public static  boolean isPasswordValid(String password)
    {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == "") {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();

    }





}
