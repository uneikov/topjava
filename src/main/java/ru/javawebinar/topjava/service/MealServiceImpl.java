package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        if (repository.getUserId() == AuthorizedUser.id())
            return repository.save(meal);
        else
            throw new NotFoundException("Unauthorized operation.");
    }

    @Override
    public boolean delete(int id) {
        if (repository.getUserId() == AuthorizedUser.id())
            return repository.delete(id);
        else
            throw new NotFoundException("Unauthorized operation.");
    }

    @Override
    public Meal get(int id) {
        if (repository.getUserId() == AuthorizedUser.id())  return repository.get(id);
        else throw new NotFoundException("Unauthorized operation.");
    }

    @Override
    public Collection<Meal> getAll(int authId) {
        if (repository.getUserId() == authId)
            return repository.getAll(authId);
        else
            throw new NotFoundException("Unauthorized operation.");
    }

    @Override
    public void setUserId(int id){ repository.setUserId(id);}

    @Override
    public int getUserId() {
        return repository.getUserId();
    }
}
