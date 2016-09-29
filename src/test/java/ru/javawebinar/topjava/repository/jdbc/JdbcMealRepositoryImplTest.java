package ru.javawebinar.topjava.repository.jdbc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealWithExceedTestData.MATCHER;

public class JdbcMealRepositoryImplTest {

    private static ConfigurableApplicationContext appCtx;
    private Meal meal1 = new Meal(LocalDateTime.of(2016, Month.AUGUST, 9, 12, 33), "breakfast", 450);
    private Meal meal2 = new Meal(LocalDateTime.of(2016, Month.SEPTEMBER, 9, 17, 5), "dinner", 800);
    private Meal meal3 = new Meal(LocalDateTime.of(2016, Month.SEPTEMBER, 10, 20, 5), "supper", 800);

    @Autowired
    private static MealRestController controller;

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Before
    public void setUp() throws Exception {

        /*Meal meal1 = new Meal(LocalDateTime.of(2016, Month.AUGUST, 9, 12, 33), "breakfast", 450);
        Meal meal2 = new Meal(LocalDateTime.of(2016, Month.SEPTEMBER, 9, 17, 5), "dinner", 800);
        Meal meal3 = new Meal(LocalDateTime.of(2016, Month.SEPTEMBER, 10, 20, 5), "supper", 800);*/

        MealRepository repository = appCtx.getBean(MealRepository.class);

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
        MATCHER.assertCollectionEquals(
                Collections.singletonList(MealsUtil.createWithExceed(meal1, false)),
                mealsBetween
        );
       /* Assert.assertEquals(mealsBetween.size(), 1);*/
    }

}
