package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsListInitializer;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Entering meal servlet");
        String caloriesObj = request.getParameter("calories");
        int calories = caloriesObj == null || caloriesObj.isEmpty() ?  0 : Integer.parseInt(caloriesObj);

        request.setAttribute("meals", calories <= 0 ? MealsUtil.getAllWithoutExcess(MealsListInitializer.getAllMeals()) : MealsUtil.getAllWithExcess(MealsListInitializer.getAllMeals(), calories));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
