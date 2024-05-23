package com.nnk.springboot.util;

import java.util.List;

public class Utils {
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
