package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.datajpa.DataJpaUserRepositoryImpl;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL2;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles(Profiles.DATAJPA)
public class UserDataJpaServiceTest extends UserServiceTest {
    @Autowired
    DataJpaUserRepositoryImpl repository;

    @Test
    public void getWithMealsTest() {
        User user = ((DataJpaUserRepositoryImpl) repository).getWithMeals(ADMIN_ID);
        assertMatch(user, ADMIN);
        Assert.assertArrayEquals(user.getMeals().toArray(), new Meal[]{ADMIN_MEAL1, ADMIN_MEAL2});
    }
}
