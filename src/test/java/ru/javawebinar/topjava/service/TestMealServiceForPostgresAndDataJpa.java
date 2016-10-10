package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.MATCHER;

@ActiveProfiles({Profiles.HSQLDB, Profiles.DATAJPA})
public class TestMealServiceForPostgresAndDataJpa extends MealServiceTest{
    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    @Test
    public void testGetUser(){
        Meal meal = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertEquals(meal.getUser(), ADMIN);
    }
}
