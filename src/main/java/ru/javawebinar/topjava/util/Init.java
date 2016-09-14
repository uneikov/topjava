package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.dao.InMemoryDBDAO;
import ru.javawebinar.topjava.dao.InMemoryDBDAOImpl;
import ru.javawebinar.topjava.model.InMemoryDB;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Init {

    public static AtomicInteger id = new AtomicInteger(0);
    public static final List<Meal> meals = new CopyOnWriteArrayList<>();
    public static List<MealWithExceed> mealWithExceedsTable = new CopyOnWriteArrayList<>();

    public static final List<Meal> mealsTable = new CopyOnWriteArrayList<>();

    public static InMemoryDB inMemoryDB;

    static {
        // create database with 2 tables
        InMemoryDBDAO inMemoryDBDAO = new InMemoryDBDAOImpl();
        inMemoryDB = inMemoryDBDAO.createDB(mealsTable, mealWithExceedsTable);

        // create initial info in meals table
        inMemoryDBDAO.insert( mealsTable, new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        inMemoryDBDAO.insert( mealsTable, new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        inMemoryDBDAO.insert( mealsTable, new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        inMemoryDBDAO.insert( mealsTable, new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        inMemoryDBDAO.insert( mealsTable, new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        inMemoryDBDAO.insert( mealsTable, new Meal(id.getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

        //meals.forEach(DBUtil::addToDB);
        //for (Meal meal : meals) DBUtil.addToDB(meal);

        //InMemoryDB inMemoryDB = new InMemoryDB()
                /*
        DBUtil.addToDB(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        DBUtil.addToDB(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        DBUtil.addToDB(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        DBUtil.addToDB(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        DBUtil.addToDB(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        DBUtil.addToDB(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        */

    }

    public static void main(String[] args) {
        InMemoryDBDAO inMemoryDBDAO = new InMemoryDBDAOImpl();
        System.out.println(mealsTable.size());
        System.out.println(inMemoryDBDAO.select(mealsTable, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        //System.out.println(inMemoryDBDAO.selectMeal(2, mealsTable).toString());
        System.out.println(inMemoryDBDAO.selectRow(1, mealsTable).toString());
        System.out.println(inMemoryDBDAO.selectRow(5, mealsTable).toString());
        //inMemoryDBDAO.delete(5, mealsTable);
        System.out.println(mealsTable.size());
    }
}
