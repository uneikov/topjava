package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.util.Init.meals;

public class MealDAOImpl implements MealDAO {

    @Override
    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void updateMeal(int id, Meal meal) {
        meals.set(id, meal);
    }

    @Override
    public Meal getMeal(int id) {
        return meals.get(id);
    }

    @Override
    public void deleteMeal(int id) {
        meals.remove(id);
    }

    @Override
    public Integer count() {
        return meals.size();
    }

    @Override
    public List<Meal> getMeals() {
        return meals;
    }
}
