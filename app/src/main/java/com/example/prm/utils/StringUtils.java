package com.example.prm.utils;

public class StringUtils {
    public static String appendPercentageSign(String value) {
        return value + "%";
    }

    public static String removePercentageSign(String value) {
        return value.replace("%", "");
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.equals("");
    }
}
