package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int MAX_CALORIES_PER_DAY = 2000;

    private InMemoryMealRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTimeMeal"));

        String idFromRequest = request.getParameter("id");
        Meal meal = new Meal(localDateTime, description, calories);
        if (idFromRequest == null || idFromRequest.isEmpty()) {
            repository.add(meal);
        } else {
            meal.setId(Long.parseLong(idFromRequest));
            repository.update(meal);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Entering meal servlet");

        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
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
                meal = new Meal(LocalDateTime.MIN, "", 0);
                break;
            default:
                List<MealTo> mealsTo = MealsUtil.getAllWithExcess(repository.getAll(), MAX_CALORIES_PER_DAY);
                request.setAttribute("mealsTo", mealsTo);
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("edit.jsp").forward(request, response);
    }
}
