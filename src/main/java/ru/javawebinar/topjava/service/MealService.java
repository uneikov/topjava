package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    void addMeal(Meal meal);
    void updateMeal(int id, Meal meal);
    Meal getMeal(int id);
    void deleteMeal(int id);
    Integer count();
    List<Meal> getMeals();
}
