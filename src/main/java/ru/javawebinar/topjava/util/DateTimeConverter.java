package ru.javawebinar.topjava.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/*@Converter(autoApply = true)*/
public class DateTimeConverter /*implements AttributeConverter<LocalDateTime, Timestamp> */{

    /*@Override*/
    public static Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
    }

   /* @Override*/
    public static LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}