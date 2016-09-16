package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryDBDAO;
import ru.javawebinar.topjava.dao.InMemoryDBDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.Init.id;
import static ru.javawebinar.topjava.util.Init.mealsTable;

public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);
    private InMemoryDBDAO inMemoryDBDAO = new InMemoryDBDAOImpl();
    private static final String HOME = "index.html";
    private static final String ADD = "/addMeal.jsp";
    private static final String LIST= "/mealList.jsp";
    private static final String EDIT = "/editMeal.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        action = action == null ? "listMeal" : action;
        LOG.debug("forward to GET, action=" + action  );

        switch (action){
            case "addMeal" : request.getRequestDispatcher(ADD).forward(request, response);
            case "deleteMeal" : {
                int id = Integer.parseInt(request.getParameter("id"));
                Meal deletedMeal = inMemoryDBDAO.selectRow(id, mealsTable);
                inMemoryDBDAO.delete(deletedMeal, mealsTable);
                list(request, response);
            }
            case "editMeal" : {
                int id = Integer.parseInt(request.getParameter("id"));
                Meal editedRow = inMemoryDBDAO.selectRow(id, mealsTable);
                request.setAttribute("meal", editedRow);
                request.setAttribute("mealDate", editedRow.getDate());
                request.setAttribute("mealTime", editedRow.getTime());
                request.getRequestDispatcher(EDIT).forward(request, response);
            }
            case "listMeal" : list(request, response);
            default: request.getRequestDispatcher(HOME).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        LOG.debug("forward to POST, action=" + action  );

        switch (action){
            case "addMeal" : {
                inMemoryDBDAO.insert(mealsTable, _getNewMealItem(request, "new"));
                list(request, response);
            }
            case "deleteMeal" : {/* nothing enters here now*/}
            case "editMeal" : {
                Meal meal = _getNewMealItem(request, "old");
                inMemoryDBDAO.update(meal, mealsTable);
                list(request, response);
            }
            default: request.getRequestDispatcher(HOME).forward(request, response);
        }
    }

    private Meal _getNewMealItem(HttpServletRequest request, String action){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
        Object mealDate = request.getParameter("mealDate");
        Object mealTime = request.getParameter("mealTime");
        LocalDateTime localDateTime = LocalDateTime.parse(mealDate.toString().concat(mealTime.toString()), formatter);
        String description = request.getParameter("mealType");
        Integer calories = Integer.parseInt(request.getParameter("mealCal"));
        switch (action){
            case "new" : return new Meal(id.getAndIncrement(), localDateTime, description, calories);
            case "old" : {
                int id = Integer.parseInt(request.getParameter("id"));
                return new Meal(id, localDateTime, description, calories);
            }
            default: return null;
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("mealListWithExceeded", DBUtil.getDBContentAsList(mealsTable));
        request.getRequestDispatcher(LIST).forward(request, response);
    }
}
