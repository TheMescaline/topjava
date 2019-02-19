package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.FIRST_USER_MEALS.forEach(meal -> save(SecurityUtil.authUserId(), meal));
        SecurityUtil.setAuthUserId(2);
        MealsUtil.SECOND_USER_MEALS.forEach(meal -> save(SecurityUtil.authUserId(), meal));
        SecurityUtil.setAuthUserId(1);
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> currentUserMeals = repository.computeIfAbsent(userId, HashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            currentUserMeals.put(meal.getId(), meal);
            return meal;
        }
        return currentUserMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> currentUserMeals = repository.get(userId);
        return currentUserMeals != null && currentUserMeals.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> currentUserMeals = repository.get(userId);
        return currentUserMeals == null ? null : currentUserMeals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> currentUserMeals = repository.get(userId);
        return currentUserMeals == null ? Collections.emptyList() : new ArrayList<>(currentUserMeals.values());
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        List<Meal> currentUserMeals = getAll(userId);
        return currentUserMeals.isEmpty() ? currentUserMeals : currentUserMeals.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

