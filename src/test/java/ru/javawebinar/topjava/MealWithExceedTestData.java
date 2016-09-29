package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.Objects;

public class MealWithExceedTestData {
    public static final ModelMatcher<MealWithExceed> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                            && Objects.equals(expected.isExceed(), actual.isExceed())
                    )
    );
}
