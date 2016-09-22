package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
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
    private int userId;

    @Override
    public Meal save(int userId, Meal meal) { //? userID всегда как у AuthorizedUser ?
        if (userId == AuthorizedUser.id())
            return repository.save(userId, meal);
        else
            throw new NotFoundException("Unauthorized operation.");
    }

    @Override
    public void delete(int id) {
        if (repository.getUserId(id) == AuthorizedUser.id())
            ExceptionUtil.checkNotFoundWithId(repository.delete(id), id);
        else
            ExceptionUtil.checkNotFound(false, "Unauthorized operation.");
    }

    @Override
    public Meal get(int id) {
        if (repository.getUserId(id) == AuthorizedUser.id())
            return ExceptionUtil.checkNotFound(repository.get(id), "id=" + id);
        else
            ExceptionUtil.checkNotFound(false, "Unauthorized operation.");
        return null;
    }

    @Override
    public Collection<Meal> getAll(int authId) {
        if (userId == authId)
            return repository.getAll(userId);
        else
            throw new NotFoundException("Unauthorized operation.");
    }

    @Override
    public void setUserId(int id){
        this.userId = id;
    }
}
