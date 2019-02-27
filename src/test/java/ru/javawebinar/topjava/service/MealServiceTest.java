package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-main.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

    @Test
    public void get() {
        Meal meal = service.get(LAST_ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(meal, LAST_ADMIN_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(USER_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(LAST_ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), MID_ADMIN_MEAL, FIRST_ADMIN_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(USER_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        LocalDate filterDate = LocalDate.of(2018, Month.MAY, 22);
        List<Meal> filteredMeals = service.getBetweenDates(filterDate, filterDate, ADMIN_ID);
        assertMatch(filteredMeals, MID_ADMIN_MEAL);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(ADMIN_ID);
        assertMatch(meals, LAST_ADMIN_MEAL, MID_ADMIN_MEAL, FIRST_ADMIN_MEAL);
    }

    @Test
    public void update() {
        Meal updated = new Meal(LAST_ADMIN_MEAL);
        updated.setDescription("UPDATED");
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(LAST_ADMIN_MEAL_ID, ADMIN_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal updated = new Meal(LAST_ADMIN_MEAL);
        updated.setDescription("UPDATED");
        service.update(updated, USER_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2100, Month.MAY, 10, 10, 10), "test description", 10_000);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID), newMeal, LAST_ADMIN_MEAL, MID_ADMIN_MEAL, FIRST_ADMIN_MEAL);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeCreate() {
        service.create(new Meal(null, LocalDateTime.of(2018, Month.MAY, 21, 10, 0, 0), "test description", 10_000), ADMIN_ID);
    }
}