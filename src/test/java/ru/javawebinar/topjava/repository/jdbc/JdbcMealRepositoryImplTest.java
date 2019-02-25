package ru.javawebinar.topjava.repository.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static ru.javawebinar.topjava.MealTestData.FIRST_ADMIN_MEAL;
import static ru.javawebinar.topjava.MealTestData.LAST_ADMIN_MEAL;
import static ru.javawebinar.topjava.MealTestData.MID_ADMIN_MEAL;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JdbcMealRepositoryImplTest {

    @Autowired
    JdbcMealRepositoryImpl jdbcMealRepository;

    @Test
    public void save() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2100, Month.MAY, 10, 10, 10), "test description", 10_000);
        Meal created = jdbcMealRepository.save(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(jdbcMealRepository.getAll(ADMIN_ID), newMeal, LAST_ADMIN_MEAL, MID_ADMIN_MEAL, FIRST_ADMIN_MEAL);
    }

    @Test
    public void update() {
        Meal updated = new Meal(LAST_ADMIN_MEAL);
        updated.setDescription("UPDATED");
        assertMatch(updated, jdbcMealRepository.save(updated, ADMIN_ID));
    }

    @Test
    public void updateSomeoneElses() {
        Meal updated = new Meal(LAST_ADMIN_MEAL);
        updated.setDescription("UPDATED");
        assertNull(jdbcMealRepository.save(updated, USER_ID));
    }

    @Test
    public void delete() {
        jdbcMealRepository.delete(LAST_ADMIN_MEAL.getId(), ADMIN_ID);
        assertMatch(jdbcMealRepository.getAll(ADMIN_ID), MID_ADMIN_MEAL, FIRST_ADMIN_MEAL);
    }

    @Test
    public void deleteSomeoneElses() {
        assertFalse(jdbcMealRepository.delete(USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void get() {
        Meal meal = jdbcMealRepository.get(LAST_ADMIN_MEAL.getId(), ADMIN_ID);
        assertMatch(meal, LAST_ADMIN_MEAL);
    }

    @Test
    public void getSomeoneElses() {
        assertNull(jdbcMealRepository.get(USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getAll() {
        List<Meal> meals = jdbcMealRepository.getAll(ADMIN_ID);
        assertMatch(meals, LAST_ADMIN_MEAL, MID_ADMIN_MEAL, FIRST_ADMIN_MEAL);
    }

    @Test
    public void getBetween() {
        LocalDateTime startDate = LocalDateTime.of(2018, Month.MAY, 22, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2018, Month.MAY, 22, 23, 59, 59);
        List<Meal> filteredMeals = jdbcMealRepository.getBetween(startDate, endDate, ADMIN_ID);
        assertMatch(filteredMeals, MID_ADMIN_MEAL);
    }
}