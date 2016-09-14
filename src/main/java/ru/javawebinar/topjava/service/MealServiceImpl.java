package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealServiceImpl implements MealService {

    private final MealDAO mealDAO = new MealDAOImpl();

    @Override
    public void addMeal(Meal meal) {
        mealDAO.addMeal(meal);
    }

    @Override
    public void updateMeal(int id, Meal meal) {
        mealDAO.updateMeal(id, meal);
    }

    @Override
    public Meal getMeal(int id) {
        return mealDAO.getMeal(id);
    }

    @Override
    public void deleteMeal(int id) {
        mealDAO.deleteMeal(id);
    }

    @Override
    public Integer count() {
        return mealDAO.count();
    }

    @Override
    public List<Meal> getMeals() {
        return mealDAO.getMeals();
    }

}
