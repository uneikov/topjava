package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

public class StringToLocalTimeFormat implements Formatter<LocalTime> {
    private Type type = Type.TIME;

    public void setType (Type type) {
        this.type = type;
    }

    @Override
    public LocalTime parse(String time, Locale locale) throws ParseException {
        if (time != null) {
            String[] parts = time.split(":");
            if (parts.length == 2) {
                int hour = Integer.parseInt(parts[0].trim());
                int minutes = Integer.parseInt(parts[1].trim());
                return LocalTime.of(hour, minutes);
            }
        }
        return null;
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object == null ? null : object.toString();
    }

    public enum Type {
        DATE,
        TIME
    }
}
