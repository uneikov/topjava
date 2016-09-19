package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello Topjava Enterprise!\n");
        //UserService userService = new UserServiceImpl();
        //userService.save(new User(1,"usr", "mail", "1234", Role.ROLE_USER));
        //System.out.println(userService.getAll());
        MealRepository mealRepository = new InMemoryMealRepositoryImpl();
        List<Meal> mealList= new ArrayList<>(mealRepository.getAll());
        System.out.println(mealList.stream().map(Meal::getId).collect(Collectors.toList()));
    }
}
