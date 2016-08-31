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
/*
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 24,9,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 24,14,0), "Обед", 780),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 24,18,0), "Ужин", 800),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 26,11,0), "Завтрак", 550),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 26,15,0), "Обед", 800),
                new UserMeal(LocalDateTime.of(2016, Month.AUGUST, 26,19,0), "Ужин", 850)

        );
        //getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        long startTime = System.currentTimeMillis();
        System.out.println( getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).toString());
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
        startTime = System.currentTimeMillis();
        //System.out.println( getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).toString());
        //stopTime = System.currentTimeMillis();
        //elapsedTime = stopTime - startTime;
        //System.out.println(elapsedTime);
        startTime = System.currentTimeMillis();
        System.out.println( getFilteredWithExceeded3(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).toString());
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
*/

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
        exceedList = getFilteredWithExceeded3(mealList, LocalTime.of(7, 0), LocalTime.of(13,0), 2000);
        runTimeMs = Duration.between(beforeRun, Instant.now()).toMillis();
        trueCount = exceedList.stream().filter(UserMealWithExceed::isExceed).count();
        System.out.printf("Implementation 2, mealList.size() = %d, exceedList.size() = %d, TRUE count = %d, runtime = %d%n",
                mealList.size(), exceedList.size(), trueCount, runTimeMs);

//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

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

    public static List<UserMealWithExceed>  getFilteredWithExceeded1(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        LocalDate date;
        List<UserMealWithExceed> result = new ArrayList<>();
        List<UserMeal> test;
        Stack<UserMeal> stack = new Stack<>();
        int countcal=0;

        //Sort by time
        test = mealList.parallelStream()
                .sorted((t1, t2) -> t1.getDateTime().compareTo(t2.getDateTime()))
                .collect(Collectors.toList());

        // Populating result list

        for (UserMeal userMeal : test){
            date = userMeal.getDateTime().toLocalDate();
            if (stack.empty()) {
                stack.add(userMeal);
                countcal += userMeal.getCalories();
            }
            else {
                if (date.equals(stack.peek().getDateTime().toLocalDate())) {
                    stack.push(userMeal);
                    countcal += userMeal.getCalories();
                }
                else {
                    while (!stack.empty()){
                        UserMeal t = stack.pop();
                        if (TimeUtil.isBetween(t.getDateTime().toLocalTime(), startTime, endTime)) {
                            result.add(new UserMealWithExceed(
                                    t.getDateTime(),
                                    t.getDescription(),
                                    t.getCalories(),
                                    countcal > caloriesPerDay)
                            );
                        }
                    }
                    countcal = 0;
                    stack.push(userMeal);
                    countcal += userMeal.getCalories();
                }
            }
        }
        while (!stack.empty()){
            UserMeal t = stack.pop();
            if (TimeUtil.isBetween(t.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(new UserMealWithExceed(
                        t.getDateTime(),
                        t.getDescription(),
                        t.getCalories(),
                        countcal > caloriesPerDay)
                );
            }
        }

        return result;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        return mealList.parallelStream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList())
                .parallelStream().map(meal ->
                        new UserMealWithExceed(
                                meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories(),
                                mealList.parallelStream()
                                        .filter(ml -> (ml.getDateTime().toLocalDate()).equals(meal.getDateTime().toLocalDate()))
                                        .mapToInt(UserMeal::getCalories)
                                        .sum() > caloriesPerDay
                        )
                ).collect(Collectors.toList());
    }

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

        //caloriesPerDayMap.entrySet().forEach(System.out::println);

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