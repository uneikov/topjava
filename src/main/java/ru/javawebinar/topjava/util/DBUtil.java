package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalTime;
import java.util.List;



public class DBUtil {

    public DBUtil(){}

    public static List<MealWithExceed> getDBContentAsList(List<Meal> mealList){
        //List<Meal> mealList = dbMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        return MealsUtil.getFilteredWithExceeded(mealList, LocalTime.MIN, LocalTime.MAX, 2000);
    }

}
