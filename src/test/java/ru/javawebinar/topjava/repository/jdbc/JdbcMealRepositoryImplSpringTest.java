package ru.javawebinar.topjava.repository.jdbc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collection;
import java.util.List;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcMealRepositoryImplSpringTest {

    @Autowired
    private MealRestController controller;

    @Autowired
    private  MealRepository repository;

    @Before
    public void setUp() throws Exception {
        Meal meal1 = new Meal(LocalDateTime.of(2016, Month.AUGUST, 9, 12, 33), "breakfast", 450);
        Meal meal2 = new Meal(LocalDateTime.of(2016, Month.SEPTEMBER, 9, 17, 5), "dinner", 800);
        Meal meal3 = new Meal(LocalDateTime.of(2016, Month.SEPTEMBER, 10, 20, 5), "supper", 800);

        int userId = AuthorizedUser.id();
        repository.getAll(AuthorizedUser.id()).forEach(meal -> repository.delete(meal.getId(), userId));
        repository.save(meal1, userId);
        repository.save(meal2, userId);
        repository.save(meal3, userId);
    }

    @Test
    public void save() throws Exception {
        Meal meal4 = new Meal(LocalDateTime.of(2016, Month.SEPTEMBER, 10, 12, 55), "dinner", 800);
        controller.create(meal4);
        Collection<MealWithExceed> meals = controller.getAll();
        Assert.assertEquals(meals.size(), 4);
    }

    @Test
    public void delete() throws Exception {
        controller.delete(controller.getAll().get(0).getId());
        Collection<MealWithExceed> meals = controller.getAll();
        Assert.assertEquals(meals.size(), 2);
    }

    @Test
    public void get() throws Exception {
        Meal meal = controller.get(controller.getAll().get(1).getId());
        Assert.assertEquals(meal.getDate().getYear(), 2016);
    }

    @Test
    public void getAll() throws Exception {
        Collection<MealWithExceed> meals = controller.getAll();
        Assert.assertEquals(meals.size(), 3);
    }

    @Test
    public void getBetween() throws Exception {
        List<MealWithExceed> mealsBetween = controller.getBetween(
                LocalDate.of(2016, 8, 9), LocalTime.MIN,
                LocalDate.of(2016, 8, 9), LocalTime.MAX
        );
        Assert.assertEquals(mealsBetween.size(), 1);
    }

}