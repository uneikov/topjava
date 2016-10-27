package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

public class StringToLocalDateFormatFormatterRegistrar implements FormatterRegistrar {
    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new StringToLocalDateFormatAnnotationFormatterFactory());
    }
}
