package ru.javawebinar.topjava.to;

import java.sql.Timestamp;

public class UserMeal {
//    private  Meal meal;
//    private  int userId;
//
//    public UserMeal() {
//    }
//
//    public UserMeal(int userId, Meal meal) {
//        this.meal = meal;
//        this.userId = userId;
//    }
//
//    public Meal getMeal() {
//        return meal;
//    }
//
//    public int getUserId() {
//        return userId;
//    }

    private Integer id;

    private Timestamp datetime;

    private  String description;

    private  int calories;

    private Integer userId;

    public UserMeal(){}

    public UserMeal(Timestamp dateTime, String description, int calories, int userId) {
        this(null, dateTime, description, calories, userId);
    }

    public UserMeal(Integer id, Timestamp dateTime, String description, int calories, int userId) {
        this.id = id;
        this.datetime = dateTime;
        this.description = description;
        this.calories = calories;
        this.userId = userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}