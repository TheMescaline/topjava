package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

public class StartDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        if (StringUtils.isEmpty(text)) {
            return LocalDate.MIN;
        }
        return LocalDate.parse(text);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.toString();
    }
}
