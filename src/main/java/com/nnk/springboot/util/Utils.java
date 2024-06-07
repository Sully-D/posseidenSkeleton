package com.nnk.springboot.util;

import java.util.List;
import java.util.regex.Pattern;

public class Utils {

    // Regular expression for validating password strength and format.
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!?;:#$%^&+=])(?=\\S+$).{8,24}$";

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    /**
     * Validates the format of a password against a regular expression.
     *
     * @param password The password to validate.
     * @throws IllegalArgumentException if the password does not meet complexity requirements.
     */
    public static void checkPasswordFormat(String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("Invalid password format. The password must be between " +
                    "8 and 24 characters long, include upper and lower case letters, numbers, and special " +
                    "symbols among [@#$%^&+=]. Given: ");
        }
    }

    public static void intIsValide(Integer number, String argName) {
        if (number == null) {
            throw new IllegalArgumentException(argName + " cannot be null.");
        }
        if (number < 0) {
            throw new IllegalArgumentException(argName + " cannot be less than 0.");
        }
    }

    public static void stringIsValide(String string, String argName) {
        if (string == null || string.trim().isEmpty()) {
            throw new IllegalArgumentException(argName + " cannot be null or empty.");
        }
    }

    public static void objectIsValide(Object obj, String argName) {
        if (obj == null) {
            throw new IllegalArgumentException(argName + " cannot be null.");
        }
    }

    public static void listIsValide(List<?> list, String argName) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(argName + " cannot be null or empty.");
        }
    }

    public static void emailIsValide(String email, String argName) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException(argName + " cannot be null or empty.");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException(argName + " is not a valid email address.");
        }
    }
}
