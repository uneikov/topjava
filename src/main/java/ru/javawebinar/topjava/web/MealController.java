package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
@Controller
public class MealController {
    private static final Logger LOG = LoggerFactory.getLogger(MealController.class);

    @Autowired
    private MealRestController mealController;

    @RequestMapping(value="/meals", method=RequestMethod.GET)
    public String getAll(HttpServletRequest request) {
        LOG.info("getAll");
        request.setAttribute("meals", mealController.getAll());
        return "meals";
    }

    @RequestMapping(value="/meals/delete/{id}", method=RequestMethod.GET)
    public String deleteMeal(@PathVariable Integer id) {
        LOG.info("Delete meal {}", id);
        mealController.delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(value="/meals/create", method=RequestMethod.GET)
    public String createMeal(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value="/meals/update/{id}", method=RequestMethod.GET)
    public String updateMeal(Model model, @PathVariable Integer id) {
        final Meal meal = mealController.get(id);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value="/meals/create_or_update", method= RequestMethod.POST)
    public String addMeal(HttpServletRequest request) {
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories"))
        );
        if (request.getParameter("id").isEmpty()) {
            LOG.info("Create meal {}", meal);
            mealController.create(meal);
        } else {
            LOG.info("Update meal {}", meal);
            mealController.update(meal, getId(request));
        }
        return "redirect:/meals";
    }

    @RequestMapping(value="/meals/filter", method=RequestMethod.POST)
    public String filterMeal(HttpServletRequest request) {
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        request.setAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
        return  "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }
}
