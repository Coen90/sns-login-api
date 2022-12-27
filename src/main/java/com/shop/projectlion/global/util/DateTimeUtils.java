package com.shop.projectlion.global.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}