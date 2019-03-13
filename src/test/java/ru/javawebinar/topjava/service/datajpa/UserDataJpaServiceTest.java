package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.Arrays;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL2;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class UserDataJpaServiceTest extends UserServiceTest {
    @Test
    public void getWithMealsTest() {
        User user = service.getWithMeals(ADMIN_ID);
        UserTestData.assertMatch(user, ADMIN);
        MealTestData.assertMatch(user.getMeals(), Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1));
    }

    @Test
    public void getWithEmptyMealsTest() {
        User user = service.getWithMeals(USER_ID_WITHOUT_MEALS);
        UserTestData.assertMatch(user, USER_WITHOUT_MEALS);
        MealTestData.assertMatch(user.getMeals(), Collections.emptyList());
    }
}
