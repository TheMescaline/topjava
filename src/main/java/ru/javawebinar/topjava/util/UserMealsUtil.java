package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 13, 0), "Обед", 1000)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        getFilteredWithExceededByStream(mealList, LocalTime.of(7, 0), LocalTime.of(15, 0), 2000);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapOfCaloriesByDay= new HashMap<>();
        Map<LocalDate, List<UserMeal>> mapOfMealsByDay= new HashMap<>();
        for (UserMeal userMeal : mealList) {
            LocalDateTime dateTime = userMeal.getDateTime();
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();
            mapOfCaloriesByDay.merge(date, userMeal.getCalories(), (calories1, calories2) -> calories1 + calories2);
            if (TimeUtil.isBetween(time, startTime, endTime)) {
                mapOfMealsByDay.putIfAbsent(date, new ArrayList<>());
                mapOfMealsByDay.get(date).add(userMeal);
            }
        }
        List<UserMealWithExceed> result = new ArrayList<>();
        for (Map.Entry<LocalDate, List<UserMeal>> pair : mapOfMealsByDay.entrySet()) {
            for (UserMeal userMeal : pair.getValue()) {
                result.add(mapOfCaloriesByDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay ?
                        new UserMealWithExceed(userMeal, true) :
                        new UserMealWithExceed(userMeal, false));
            }
        }
        return result;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate()))
                .values()
                .stream()
                .map(userMeals -> generateUserMealsWithExceed(userMeals, startTime, endTime, caloriesPerDay))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private static List<UserMealWithExceed> generateUserMealsWithExceed(List<UserMeal> userMeals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return userMeals.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal, isExceed(caloriesPerDay, userMeals)))
                .collect(Collectors.toList());
    }

    private static boolean isExceed(int caloriesPerDay, List<UserMeal> userMeals) {
        return userMeals
                .stream()
                .mapToInt(UserMeal::getCalories)
                .sum() > caloriesPerDay;
    }
}
