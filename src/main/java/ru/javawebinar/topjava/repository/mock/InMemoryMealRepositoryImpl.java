package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.UserMeal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    //private int userId;

    {
        MealsUtil.MEALS.forEach(meal -> this.save(0, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), new UserMeal(userId, meal));
        return meal;
    }

    @Override // id - номер строки в репо
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override // id - номер строки в репо
    public Meal get(int id) {
        UserMeal userMeal = repository.getOrDefault(id, null);
        return userMeal != null ? userMeal.getMeal() : null;
    }

    @Override
    public Collection<Meal> getAll(int authId) {

        return repository
                .values()
                .stream()
                .filter( userMeal -> userMeal.getUserId() == authId)
                .map(UserMeal::getMeal)
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList());
    }

    /*@Override
    public void setUserId(int id){
        this.userId = id;
    }

    @Override
    public int getUserId() {
        return userId;
    }*/

/*private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        //boolean result = repository.containsKey(id);
        //if (result) repository.remove(id);
        //return result;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        return repository.getOrDefault(id, null);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository
                .values()
                .stream()
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList());
    }*/


}

