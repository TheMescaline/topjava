package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Collections;
import java.util.Set;

public final class DateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<WebDateTimeFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return Collections.singleton(String.class);
    }

    @Override
    public Printer<?> getPrinter(WebDateTimeFormat annotation, Class<?> fieldType) {
        return resolveFormatter(annotation);
    }

    @Override
    public Parser<?> getParser(WebDateTimeFormat annotation, Class<?> fieldType) {
        return resolveFormatter(annotation);
    }

    private Formatter resolveFormatter(WebDateTimeFormat annotation) {
        switch (annotation.state()) {
            case START_DATE: return new StartDateFormatter();
            case END_DATE: return new EndDateFormatter();
            case START_TIME: return new StartTimeFormatter();
            case END_TIME: return new EndTimeFormatter();
            default: throw new IllegalStateException("Pick right state for WebDateTimeFormat");
        }
    }
}
