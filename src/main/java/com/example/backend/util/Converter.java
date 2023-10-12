package com.example.backend.util;

import java.time.LocalDateTime;

public class Converter {

    public static LocalDateTime stringToDate(String date) {
        String[] dateArray = date.split("-");
        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);
        return LocalDateTime.of(year, month, day, 0, 0, 0);
    }

    public static String dateToString(LocalDateTime date) {
        return date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth();
    }

}
