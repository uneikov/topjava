package ru.javawebinar.topjava.service;

import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.HSQLDB, Profiles.JDBC})
public class TestMealServiceForPostgresAndJdbc extends MealServiceTest{
    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }
}
