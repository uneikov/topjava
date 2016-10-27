package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class StringToLocalDateFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<StringToLocalDate> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> setTypes = new HashSet<Class<?>>();
        setTypes.add(LocalDate.class);
        return setTypes;
    }

    @Override
    public Printer<?> getPrinter(StringToLocalDate annotation, Class<?> fieldType) {
        return new StringToLocalDateFormatter();
    }

    @Override
    public Parser<?> getParser(StringToLocalDate annotation, Class<?> fieldType) {
        return new StringToLocalDateFormatter();
    }
}

