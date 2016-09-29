package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    protected MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        int userId = AuthorizedUser.id();
        Meal meal = service.get(USER_MEAL_1_ID, userId);
        MATCHER.assertEquals(USER_MEAL_1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        int userId = AuthorizedUser.id();
        service.get(1, userId);
    }

    @Test (expected = NotFoundException.class)
    public void getAlien() throws Exception {
        Meal meal = service.get(USER_MEAL_1_ID, ADMIN_ID);
        MATCHER.assertEquals(USER_MEAL_1, meal);
    }

    @Test
    public void delete() throws Exception {
        int userId = AuthorizedUser.id();
        service.delete(USER_MEAL_2_ID, userId);
        MATCHER.assertCollectionEquals(Collections.singletonList(USER_MEAL_1), service.getAll(userId));
    }

    @Test (expected = NotFoundException.class)
    public void deleteAlien() throws Exception {
        service.delete(USER_MEAL_2_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(USER_MEAL_1), service.getAll(USER_ID));
    }

    @Test
    public void getBetweenDates() throws Exception {
        int userId = AuthorizedUser.id();
        Collection<Meal> dates = service.getBetweenDates(
                LocalDate.of(2016, 9, 9),
                LocalDate.of(2016, 9, 9),
                userId
        );
        MATCHER.assertCollectionEquals(dates, Arrays.asList(USER_MEAL_2, USER_MEAL_1));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        int userId = AuthorizedUser.id();
        Collection<Meal> dates = service.getBetweenDateTimes(
                LocalDateTime.of(2016, 9, 9, 10, 0),
                LocalDateTime.of(2016, 9, 9, 15, 0),
                userId);
        MATCHER.assertCollectionEquals(dates, Collections.singletonList(USER_MEAL_1));
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(
                Arrays.asList(USER_MEAL_2, USER_MEAL_1),
                service.getAll(AuthorizedUser.id())
        );
    }

    @Test
    public void update() throws Exception {
        int userId = AuthorizedUser.id();
        Meal updated = new Meal(USER_MEAL_1);
        updated.setId(USER_MEAL_1_ID);
        updated.setCalories(550);
        updated.setDescription("night meal");
        service.update(updated, userId);
        MATCHER.assertEquals(updated, service.get(USER_MEAL_1_ID, userId));
    }

    @Test
    public void save() throws Exception {
        Meal created = service.save(USER_MEAL_3, AuthorizedUser.id());
        USER_MEAL_3.setId(created.getId());
        MATCHER.assertCollectionEquals(
                Arrays.asList(USER_MEAL_3, USER_MEAL_2, USER_MEAL_1),
                service.getAll(AuthorizedUser.id())
        );
    }

}