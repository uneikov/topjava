package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;

public class MWETestData {

    public static final ModelMatcher<MealWithExceed> MWE_MATCHER = new ModelMatcher<>(MealWithExceed.class);

    public static final MealWithExceed MWE1 = new MealWithExceed(MEAL1_ID,     of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, false);
    public static final MealWithExceed MWE2 = new MealWithExceed(MEAL1_ID + 1, of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, false);
    public static final MealWithExceed MWE3 = new MealWithExceed(MEAL1_ID + 2, of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, false);
    public static final MealWithExceed MWE4 = new MealWithExceed(MEAL1_ID + 3, of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500, true);
    public static final MealWithExceed MWE5 = new MealWithExceed(MEAL1_ID + 4, of(2015, Month.MAY, 31, 13, 0), "Обед", 1000, true);
    public static final MealWithExceed MWE6 = new MealWithExceed(MEAL1_ID + 5, of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, true);
}
