package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.datajpa.DataJpaMealRepositoryImpl;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class MealDataJpaServiceTest extends MealServiceTest {
    @Autowired
    MealRepository repository;

    @Test
    public void findMealWithUserTest() {
        Meal meal = ((DataJpaMealRepositoryImpl) repository).getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MealTestData.assertMatch(meal, ADMIN_MEAL1);
        UserTestData.assertMatch(meal.getUser(), ADMIN);
    }
}
