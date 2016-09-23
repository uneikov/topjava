package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(int userId, Meal meal);

    void delete(int id);

    Meal get(int id);

    Collection<Meal> getAll(int authId);

   /* void setUserId(int id);*/
}
