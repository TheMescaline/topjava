package ru.javawebinar.topjava.util.formatter;

public @interface WebDateTimeFormat {

    State state();

    enum State {
        START_DATE,
        END_DATE,
        START_TIME,
        END_TIME
    }
}
