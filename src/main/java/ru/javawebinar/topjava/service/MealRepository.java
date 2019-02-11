package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import java.util.List;

public interface MealRepository {
    void delete(Long id);

    void update(Meal meal);

    void add(Meal meal);

    Meal get(Long id);

    List<Meal> getAll();
}
