package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

public class StringToLocalDateFormatter implements Formatter<LocalDate> {

    private Type type = Type.DATE;

    public void setType (Type type) {
        this.type = type;
    }

    @Override
    public LocalDate parse(String date, Locale locale) throws ParseException {
        if (date != null) {
            String[] parts = date.split("[-./]");
            if (parts.length == 3) {
                int year = Integer.parseInt(parts[0].trim());
                int month = Integer.parseInt(parts[1].trim());
                int day = Integer.parseInt(parts[2].trim());
                return LocalDate.of(year, month, day);
            }
        }
        return null;
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object == null ? null : object.toString();
    }

    private enum Type {
        DATE,
        TIME
    }
}
