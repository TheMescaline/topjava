package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsListInitializer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMealRepository implements MealRepository {
    private static AtomicLong counter = new AtomicLong(0L);

    private final ConcurrentMap<Long, Meal> mealStorage = new ConcurrentHashMap<>();

    {
        MealsListInitializer.getAllMeals().forEach(this::add);
    }

    @Override
    public void delete(Long id) {
        mealStorage.remove(id);
    }

    @Override
    public void update(Meal meal) {
        mealStorage.replace(meal.getId(), meal);
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(counter.getAndIncrement());
        mealStorage.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal get(Long id) {
        return mealStorage.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealStorage.values());
    }
}
