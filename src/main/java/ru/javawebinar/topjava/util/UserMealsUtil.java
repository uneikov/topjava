package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        // Summarize calories for each day
        Map<LocalDate, Integer> caloriesPerDayMap = mealList
                .parallelStream()
                .collect(Collectors.toConcurrentMap(meal -> meal.getDateTime().toLocalDate(),
                UserMeal::getCalories, (mealCalories1, mealCalories2) -> mealCalories1 + mealCalories2));

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