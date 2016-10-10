package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({Profiles.HSQLDB, Profiles.DATAJPA})
public class TestUserServiceForPostgresAndDataJpa extends UserServiceTest {
    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    @Test
    public  void testGetMeals(){
        User user = service.get(USER_ID);
        MEAL_MATCHER.assertCollectionEquals(
                user.getMeals(),
                Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6)
        );
    }
}
