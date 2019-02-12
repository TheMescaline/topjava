package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import java.util.List;

public interface MealRepository {
    void delete(Long id);

    void update(Meal meal);

    Meal add(Meal meal);

    Meal get(Long id);

    List<Meal> getAll();
}
