package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.InMemoryDB;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

public interface InMemoryDBDAO {
    InMemoryDB createDB(List<Meal> mealsTable, List<MealWithExceed> mealsWithExceedsTable); // create table

    void insert(List<Meal> table, Meal meal); // insert row to table

    void update(Meal meal, List<Meal> table);

    Meal select(List<Meal> table, LocalDateTime dateTime, String description, int calories);

    Meal selectRow(int id, List<Meal> table);

    void delete(Meal meal, List<Meal> table); // delete table row

    Integer count(); // table size

}
