package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealsListInitializer {
    private static final List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 28, 10, 0), "Завтрак", 450),
            new Meal(LocalDateTime.of(2015, Month.MAY, 28, 13, 0), "Обед", 900),
            new Meal(LocalDateTime.of(2015, Month.MAY, 28, 20, 0), "Ужин", 660),
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 400),
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 13, 0), "Обед", 900),
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 20, 0), "Ужин", 600),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static List<Meal> getAllMeals() {
        return meals;
    }
}
