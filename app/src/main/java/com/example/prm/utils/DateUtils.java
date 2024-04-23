package com.example.prm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class DateUtils {
    private static String dateFormat = "dd/MM/yy";
    private static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    public static String formatDate(Date date) {
        return sdf.format(date);
    }

    public static Date parseDate(String date){
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(date + " cannot be parsed.");
        }
    }


    public static int compareDatesFromMaps(Map<String, String> map1, Map<String, String> map2) {
        Date firstDate = DateUtils.parseDate(map1.get(TaskDataUtils.TASK_DEADLINE));
        Date secondDate = DateUtils.parseDate(map2.get(TaskDataUtils.TASK_DEADLINE));
        return firstDate.compareTo(secondDate);
    }

    public static boolean validateDate(String date) {
        parseDate(date);
        return true;
    }

    public static void prepareCalendar(Calendar cal, int year, int month, int day) {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
    }

    public static boolean filterPastDates(Map<String, String> map) {
        Date currentDate = Calendar.getInstance().getTime();
        Date mapDate = parseDate(map.get(TaskDataUtils.TASK_DEADLINE));
        return currentDate.before(mapDate);
    }
}
