package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class TestUserServiceWithDataJpa extends UserServiceTest {
    @Test
    public  void testGetMeals(){
        User user = service.get(USER_ID);
        MEAL_MATCHER.assertCollectionEquals(
                user.getMeals(),
                Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6)
        );
    }
}
