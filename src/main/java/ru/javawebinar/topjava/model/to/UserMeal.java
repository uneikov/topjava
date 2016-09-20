package ru.javawebinar.topjava.model.to;

import ru.javawebinar.topjava.model.Meal;

public class UserMeal {
    private final Meal meal;
    private final int userId;

    public UserMeal(int userId, Meal meal) {
        this.userId = userId;
        this.meal = meal;
    }

    public Meal getMeal() {
        return meal;
    }

    public int getUserId() {
        return userId;
    }

}
