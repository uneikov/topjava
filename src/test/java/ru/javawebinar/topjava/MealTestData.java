package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int USER_MEAL_1_ID = START_SEQ + 2;
    public static final int USER_MEAL_2_ID= START_SEQ + 3;
    public static final int USER_MEAL_3_ID= START_SEQ + 4;

    public static final Meal USER_MEAL_1 =
            new Meal(USER_MEAL_1_ID, LocalDateTime.of(2016, Month.SEPTEMBER, 9, 12, 33), "breakfast", 450);
    public static final Meal USER_MEAL_2 =
            new Meal(USER_MEAL_2_ID, LocalDateTime.of(2016, Month.SEPTEMBER, 9, 17, 5), "dinner", 800);
    public static final Meal USER_MEAL_3 =
            new Meal(USER_MEAL_3_ID, LocalDateTime.of(2016, Month.SEPTEMBER, 10, 12, 55), "dinner", 800);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                    )
    );
}
