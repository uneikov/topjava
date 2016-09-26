package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx =
                     new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            /*adminUserController.create(UserTestData.USER);*/
            System.out.println();

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            System.out.println(adminUserController.getAll());
            System.out.println(mealController.getAll());
            System.out.println(mealController.getBetween(LocalDate.of(2016, 9, 9), LocalTime.of(13, 0), LocalDate.of(2016, 9, 9), LocalTime.of(22, 0)));
            Meal meal = mealController.get(100007);
            meal.setCalories(800);
            //mealController.delete(100006);
            //System.out.println(mealController.get(100003));
            mealController.update(meal, 100007);
            System.out.println(mealController.getAll());
            /*List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);*/
        }
    }
}
