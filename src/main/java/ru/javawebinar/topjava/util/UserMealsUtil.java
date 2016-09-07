package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {


        List<UserMeal> mealList = new ArrayList<>();
        LocalDate date = LocalDate.of(2012, Month.JANUARY, 1 );
        Random r = new Random();
        for (int i=0; i<500000; i++) {
            date = date.plus(1, ChronoUnit.DAYS);
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(10, 0)), "Завтрак", 300 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(13, 0)), "Обед", 800 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(20, 0)), "Ужин", 300 + r.nextInt(400)));
        }

        Instant beforeRun = Instant.now();
        List<UserMealWithExceed> exceedList = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(13,0), 2000);
        long runTimeMs = Duration.between(beforeRun, Instant.now()).toMillis();
        long trueCount = exceedList.stream().filter(UserMealWithExceed::isExceed).count();
        System.out.printf("Implementation 1, mealList.size() = %d, exceedList.size() = %d, TRUE count = %d, runtime = %d%n",
                mealList.size(), exceedList.size(), trueCount, runTimeMs);

        beforeRun = Instant.now();
        exceedList = getFilteredWithExceeded_1(mealList, LocalTime.of(7, 0), LocalTime.of(13,0), 2000);
        runTimeMs = Duration.between(beforeRun, Instant.now()).toMillis();
        trueCount = exceedList.stream().filter(UserMealWithExceed::isExceed).count();
        System.out.printf("Implementation 1, mealList.size() = %d, exceedList.size() = %d, TRUE count = %d, runtime = %d%n",
                mealList.size(), exceedList.size(), trueCount, runTimeMs);

        beforeRun = Instant.now();
        exceedList = getFilteredWithExceeded_2(mealList, LocalTime.of(7, 0), LocalTime.of(13,0), 2000);
        runTimeMs = Duration.between(beforeRun, Instant.now()).toMillis();
        trueCount = exceedList.stream().filter(UserMealWithExceed::isExceed).count();
        System.out.printf("Implementation 1, mealList.size() = %d, exceedList.size() = %d, TRUE count = %d, runtime = %d%n",
                mealList.size(), exceedList.size(), trueCount, runTimeMs);

    }
    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

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

    public static List<UserMealWithExceed>  getFilteredWithExceeded_1(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

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

    public static List<UserMealWithExceed>  getFilteredWithExceeded_2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        LocalDate date;
        LocalTime time;
        boolean isExceeded;
        List<UserMealWithExceed> result = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();

        // Sum calories for each day
        for (UserMeal userMeal : mealList){
            date = userMeal.getDateTime().toLocalDate();
            int calories = userMeal.getCalories();

            if(caloriesPerDayMap.containsKey(date))
                caloriesPerDayMap.replace(date, caloriesPerDayMap.get(date) + calories);
            else caloriesPerDayMap.put(date, calories);
        }

        //caloriesPerDayMap.entrySet().forEach(System.out::println);

        //Populate result list
        for (UserMeal userMeal : mealList){
            date = userMeal.getDateTime().toLocalDate();
            time = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(time, startTime, endTime)){
                isExceeded = caloriesPerDayMap.get(date) > caloriesPerDay;
                result.add(new UserMealWithExceed(userMeal.getDateTime(),userMeal.getDescription(), userMeal.getCalories(), isExceeded));
            }
        }

        return result;
    }
}