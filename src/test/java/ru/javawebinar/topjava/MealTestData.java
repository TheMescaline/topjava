package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int USER_MEAL_ID = 100010;
    public static final Meal FIRST_ADMIN_MEAL = new Meal(100012, LocalDateTime.of(2018, Month.MAY, 21, 10, 0, 0), "Админ Завтрак", 1600);
    public static final Meal MID_ADMIN_MEAL = new Meal(100011, LocalDateTime.of(2018, Month.MAY, 22, 14, 0, 0), "Админ Обед", 2100);
    public static final Meal LAST_ADMIN_MEAL = new Meal(100013, LocalDateTime.of(2018, Month.MAY, 23, 20, 0, 0), "Админ Ужин", 1700);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
