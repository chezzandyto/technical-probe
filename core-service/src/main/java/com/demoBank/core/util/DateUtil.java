package com.demoBank.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtil {

    public static LocalDateTime atEndOfDay(LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }

    public static LocalDateTime atStartOfDay(LocalDate date) {
        return date.atStartOfDay();
    }
}
