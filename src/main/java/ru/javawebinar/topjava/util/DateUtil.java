package ru.javawebinar.topjava.util;

import java.time.LocalDate;

public class DateUtil {
    public static boolean isBetween(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
    }
}
