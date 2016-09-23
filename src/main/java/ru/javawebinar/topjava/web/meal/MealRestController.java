package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired private MealService service;

    public Meal save(int userId, Meal meal) {
        LOG.info("save " + meal);
        return service.save(userId, meal);
    }

    public void delete(int id) {
        LOG.info("delete" + id);
        service.delete(id);
    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public Collection<Meal> getAll(int userId) {
        LOG.info("get All");
        return service.getAll(userId);
    }

   /* public void setUserId(int id) {
        service.setUserId(id);
    }*/

    /*public int getUserId() {
        return service.getUserId();
    }*/
}
