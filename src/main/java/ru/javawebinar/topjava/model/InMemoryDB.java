package ru.javawebinar.topjava.model;

import java.util.List;

public class InMemoryDB {
    private List<Meal> mealsTable;
    private List<MealWithExceed> mealsWithExceedsTable ;

    public InMemoryDB(List<Meal> mealsTable, List<MealWithExceed> mealsWithExceedsTable) {
        this.mealsTable = mealsTable;
        this.mealsWithExceedsTable = mealsWithExceedsTable;
    }

    public List<Meal> getMeals() {
        return mealsTable;
    }

    public List<MealWithExceed> getMealsWithExceeds() {
        return mealsWithExceedsTable;
    }
}
