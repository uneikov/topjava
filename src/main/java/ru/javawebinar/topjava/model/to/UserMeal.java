package ru.javawebinar.topjava.model.to;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

public class UserMeal {
    private final Meal meal;
    private final User user;

    public UserMeal(Meal meal, User user) {
        this.meal = meal;
        this.user = user;
    }

    public Meal getMeal() {
        return meal;
    }

    public User getUser() {
        return user;
    }

}
