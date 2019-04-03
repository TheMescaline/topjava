package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

public class EndTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        if (StringUtils.isEmpty(text)) {
            return LocalTime.MAX;
        }
        return LocalTime.parse(text);
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object.toString();
    }
}
