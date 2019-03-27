package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping
    public String meals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/mealForm/{id}")
    public String meal(@PathVariable(value = "id") int id, Model model) {
        model.addAttribute(super.get(id));
        return "mealForm";
    }

    @GetMapping("/mealForm")
    public String createMeal(Model model) {
        model.addAttribute(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @PostMapping
    public String saveMeal(
            @RequestParam String dateTime,
            @RequestParam String description,
            @RequestParam String calories,
            @RequestParam String id) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        if (StringUtils.isEmpty(id)) {
            super.create(meal);
        } else {
            super.update(meal, Integer.parseInt(id));
        }
        return "redirect:meals";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeal(@PathVariable(value = "id") int id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String getFiltered(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String endTime,
            Model model) {
        model.addAttribute("meals", super.getBetween(
                parseLocalDate(startDate),
                parseLocalTime(startTime),
                parseLocalDate(endDate),
                parseLocalTime(endTime)));
        return "meals";
    }
}
