package com.nnk.springboot.util;

public class Utils {

    public static void validNumber(Integer num, String argName) {
        if (num == null) {
            throw new IllegalArgumentException(argName + " cannot be null.");
        }
        if (num < 0) {
            throw new IllegalArgumentException(argName + " cannot be less than 0.");
        }
    }
}
