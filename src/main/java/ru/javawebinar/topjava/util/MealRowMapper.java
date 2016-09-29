package ru.javawebinar.topjava.util;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Meal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MealRowMapper implements RowMapper
{
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        /*return new Meal(
                rs.getInt("id"),
                rs.getTimestamp("date_time").toLocalDateTime(),
                rs.getString("description"),
                rs.getInt("calories")
        );*/
        Meal meal = new Meal();
        meal.setId(rs.getInt("id"));
        meal.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());
        meal.setDescription(rs.getString("description"));
        meal.setCalories(rs.getInt("calories"));
        return meal;
    }
}