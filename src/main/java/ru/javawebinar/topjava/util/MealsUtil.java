package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealsUtil {
    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {
        List<MealWithExceed> filteredMealsWithExceeded = getFilteredWithExceeded(MEALS, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY);
        filteredMealsWithExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceededByCycle(MEALS, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY));
    }

    public static List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        //Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealWithExceed> mealExceeded = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealExceeded.add(createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealExceeded;
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }

    public static List<MealWithExceed> getFiltered(List<MealWithExceed> mealWithExceeds, String _fromDate, String _toDate, String _fromTime, String _toTime){

        boolean noDate = _fromDate.isEmpty() || _fromDate.isEmpty();
        boolean noTime = _fromTime.isEmpty() || _toTime.isEmpty();

        List<MealWithExceed> result;

        if (noDate && noTime){
            return mealWithExceeds;
        }else if (noTime) {
            LocalDate fromDate = LocalDate.parse(_fromDate);
            LocalDate toDate = LocalDate.parse(_toDate);
            result = getFilteredByDate(mealWithExceeds, fromDate, toDate);
        }else if (noDate){
            LocalTime fromTime = LocalTime.parse(_fromTime);
            LocalTime toTime = LocalTime.parse(_toTime);
            result = getFilteredByTime(mealWithExceeds, fromTime, toTime);
        }else {
            LocalDate fromDate = LocalDate.parse(_fromDate);
            LocalDate toDate = LocalDate.parse(_toDate);
            LocalTime fromTime = LocalTime.parse(_fromTime);
            LocalTime toTime = LocalTime.parse(_toTime);
            result = getFilteredByDate(mealWithExceeds, fromDate, toDate);
            result = getFilteredByTime(result, fromTime, toTime);
        }

        return result;
    }

    private static List<MealWithExceed> getFilteredByDate(List<MealWithExceed> mealWithExceeds, LocalDate startDate, LocalDate endDate) {
        return mealWithExceeds.stream()
                .filter(meal -> DateUtil.isBetween(meal.getDateTime().toLocalDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    private static List<MealWithExceed> getFilteredByTime(List<MealWithExceed> mealWithExceeds, LocalTime startTime, LocalTime endTime) {
        return mealWithExceeds.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

}