package ru.javawebinar.topjava;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.web.user.InMemoryAdminRestControllerSpringTest;
import ru.javawebinar.topjava.web.user.InMemoryAdminRestControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MealServiceTest.class,
        UserServiceTest.class,
        InMemoryAdminRestControllerSpringTest.class,
        InMemoryAdminRestControllerTest.class
})
public class RunAllTests {
}
