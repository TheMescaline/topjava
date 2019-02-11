package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsListInitializer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMealRepository implements MealRepository {
    public static AtomicLong counter = new AtomicLong(0L);

    private final ConcurrentMap<Long, Meal> mealStorage = new ConcurrentHashMap<>();

    {
        MealsListInitializer.getAllMeals().forEach(meal -> {
            meal.setId(counter.getAndIncrement());
            mealStorage.put(meal.getId(), meal);});
    }

    @Override
    public void delete(Long id) {
        checkIfNotExist(id);
        mealStorage.remove(id);
    }

    @Override
    public void update(Meal meal) {
        checkIfNotExist(meal.getId());
        mealStorage.put(meal.getId(), meal);
    }

    @Override
    public void add(Meal meal) {
        meal.setId(counter.getAndIncrement());
        mealStorage.put(meal.getId(), meal);
    }

    @Override
    public Meal get(Long id) {
        checkIfNotExist(id);
        return mealStorage.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealStorage.values());
    }

    private void checkIfNotExist(Long id) {
        if (!mealStorage.containsKey(id)) {
            throw new IllegalArgumentException("No such meal in the storage");
        }
    }
}
