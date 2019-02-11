package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.IMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private IMealRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new IMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Long id = Long.parseLong(request.getParameter("id"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDate date = LocalDate.parse(request.getParameter("mealDate"));
        LocalTime time = LocalTime.parse(request.getParameter("mealTime"));
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        Meal meal = new Meal(id, dateTime, description, calories);
        if (id < 0L) {
            repository.add(meal);
        } else {
            repository.update(meal);
        }
        response.sendRedirect("meals");
}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Entering meal servlet");

        String action = request.getParameter("action");
        if (action == null) {
            String calories = request.getParameter("calories");
            List<MealTo> mealsTo;
            if (calories == null || calories.isEmpty()) {
                mealsTo = MealsUtil.getAllWithoutExcess(repository.getAll());
            } else {
                mealsTo = MealsUtil.getAllWithExcess(repository.getAll(), Integer.parseInt(calories));
            }
            request.setAttribute("mealsTo", mealsTo);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }

        Meal meal;
        switch (action) {
            case "delete":
                repository.delete(Long.parseLong(request.getParameter("id")));
                response.sendRedirect("meals");
                return;
            case "edit":
                meal = repository.get(Long.parseLong(request.getParameter("id")));
                break;
            case "add":
                meal = Meal.EMPTY;
                break;
            default:
                throw new IllegalArgumentException("Illegal action!");
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("edit.jsp").forward(request, response);
    }
}
