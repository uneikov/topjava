package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {

    public static List<UserMealWithExceed>  getFilteredWithExceeded3(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesPerDayMap = new ConcurrentHashMap<>();

        // Summarize calories for each day
        mealList.parallelStream().forEach((meal) -> {
            final LocalDate date = meal.getDateTime().toLocalDate();
            final int calories = meal.getCalories();
            if (caloriesPerDayMap.containsKey(date))
                caloriesPerDayMap.replace(date, caloriesPerDayMap.get(date) + calories);
            else
                caloriesPerDayMap.put(date, calories);
        });

        // Populate result list
        return mealList
                .parallelStream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal ->
                        new UserMealWithExceed(
                                meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories(),
                                caloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay
                        )
                ).collect(Collectors.toList());
    }
}