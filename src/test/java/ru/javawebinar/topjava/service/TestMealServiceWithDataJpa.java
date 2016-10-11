package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class TestMealServiceWithDataJpa extends MealServiceTest{
    @Test
    public void testGetUser(){
        Meal meal = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertEquals(meal.getUser(), ADMIN);
    }
}
