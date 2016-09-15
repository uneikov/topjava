package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.InMemoryDB;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

public class InMemoryDBDAOImpl implements InMemoryDBDAO{

    @Override
    public InMemoryDB createDB(List<Meal> mealsTable, List<MealWithExceed> mealsWithExceedsTable) {
        return new InMemoryDB(mealsTable, mealsWithExceedsTable);
    }

    @Override
    public void insert(List<Meal> table, Meal meal) {
        table.add(meal);
    }

    @Override
    public void update(Meal newMeal, List<Meal> table) {
        int id= newMeal.getId();
        Meal oldMeal = selectRow(id, table);
        int row = table.indexOf(oldMeal);
        table.set(row, newMeal);
    }

    @Override
    public Meal selectRow(int id, List<Meal> table) {
        return table.stream().filter(meal -> meal.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Meal select(List<Meal> table, LocalDateTime dateTime, String description, int calories){
        return table.stream()
                .filter(meal -> (meal.getDateTime().equals(dateTime) &&
                meal.getDescription().equals(description) &&
                meal.getCalories() == calories)).findFirst().orElse(null);
    }

    @Override
    public void delete(Meal meal, List<Meal> table) {
        table.remove(meal);
    }

    @Override
    public Integer count(List<Meal> table) {
        return table.size();
    }

}
