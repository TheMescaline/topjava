package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL2;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.assertMatch;

@ActiveProfiles(Profiles.DATAJPA)
public class UserDataJpaServiceTest extends UserServiceTest {
    @Test
    public void getWithMealsTest() {
        User user = service.getWithMeals(ADMIN_ID);
        assertMatch(user, ADMIN);
        Assert.assertArrayEquals(user.getMeals().toArray(), new Meal[]{ADMIN_MEAL1, ADMIN_MEAL2});
    }
}
