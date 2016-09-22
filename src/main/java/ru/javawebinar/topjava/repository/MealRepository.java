package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(int userId, Meal meal);

    boolean delete(int id);

    Meal get(int id);

    Collection<Meal> getAll(int authId);

    int getUserId(int id);

}
