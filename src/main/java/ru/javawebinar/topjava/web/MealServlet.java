package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            this.mealRestController = appCtx.getBean(MealRestController.class);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        String id = request.getParameter("id");
        Meal meal;
        if (id.isEmpty()) {
            meal = new Meal(null, dateTime, description, calories);
            log.info("Create {}", meal);
            mealRestController.create(meal);
        } else {
            meal = new Meal(Integer.valueOf(id), dateTime, description, calories);
            log.info("Update {}", meal);
            mealRestController.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                String startDateFromRequest = request.getParameter("startDate");
                String endDateFromRequest = request.getParameter("endDate");
                String startTimeFromRequest = request.getParameter("startTime");
                String endTimeFromRequest = request.getParameter("endTime");
                LocalDate startDate = startDateFromRequest == null || startDateFromRequest.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDateFromRequest);
                LocalDate endDate = endDateFromRequest == null || endDateFromRequest.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDateFromRequest);
                LocalTime startTime = startTimeFromRequest == null || startTimeFromRequest.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTimeFromRequest);
                LocalTime endTime = endTimeFromRequest == null || endTimeFromRequest.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTimeFromRequest);
                log.info("getAll");
                request.setAttribute("meals",
                        mealRestController.getAll(startDate, endDate, startTime, endTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
