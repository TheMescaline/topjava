package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    private final MealService mealService;

    public MealRestController(MealService mealService, UserService userService) {
        this.mealService = mealService;
    }
    
    public Meal create(Meal meal) {
        log.info("Create {}", meal);
        return mealService.create(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id) throws NotFoundException {
        log.info("Delete meal with id={}", id);
        mealService.delete(SecurityUtil.authUserId(), id);
    }

    public Meal get(int id) throws NotFoundException {
        log.info("Get meal with id={}", id);
        return mealService.get(SecurityUtil.authUserId(), id);
    }

    public void update(Meal meal, int id) {
        log.info("Update {}", meal.getId());
        ValidationUtil.assureIdConsistent(meal, id);
        mealService.update(SecurityUtil.authUserId(), meal);
    }

    public List<MealTo> getAll() {
        log.info("Get all");
        int userId = SecurityUtil.authUserId();
        return MealsUtil.getWithExcess(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getAllFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("Get all filtered");
        int userId = SecurityUtil.authUserId();
        return MealsUtil.getFilteredWithExcess(mealService.getAllFiltered(userId,
                startDate == null ? LocalDate.MIN : startDate,
                endDate == null ? LocalDate.MAX : endDate),
                SecurityUtil.authUserCaloriesPerDay(), startTime == null ? LocalTime.MIN : startTime,
                endTime == null ? LocalTime.MAX : endTime);
    }
}