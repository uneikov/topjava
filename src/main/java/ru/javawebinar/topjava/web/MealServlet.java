package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryDBDAO;
import ru.javawebinar.topjava.dao.InMemoryDBDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
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

/**
 * User: uran
 * Date: 08.09.2016
 */

public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);
    private MealService mealService = new MealServiceImpl();
    private InMemoryDBDAO inMemoryDBDAO = new InMemoryDBDAOImpl();
    private static final String HOME = "index.html";
    private static final String ADD = "/addMeal.jsp";
    private static final String LIST= "/mealList.jsp";
    private static final String EDIT = "/editMeal.jsp";
    private int currentID = 0;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to mealList");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        LOG.debug("ACTION: " + (action==null ? "list" : "null"));
        action = action==null ? "list" : action;

        if (action.equals("delete")){
            LOG.debug("_delete in GET");
            String dd =request.getParameter("date");
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"));
            String description = request.getParameter("desc");
            int calories = Integer.parseInt(request.getParameter("cal"));
            Meal meal = inMemoryDBDAO.select(mealsTable, dateTime, description, calories);
            inMemoryDBDAO.delete(meal, mealsTable);
            request.setAttribute("mealListWithExceeded", DBUtil.getDBContentAsList(mealsTable));
            request.getRequestDispatcher(LIST).forward(request, response);
        }else if(action.equals("edit")){
            LOG.debug("_edit in GET");
            currentID = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("id", id);
            request.setAttribute("mealDate", inMemoryDBDAO.selectRow(currentID, mealsTable).getDate());
            request.setAttribute("mealTime", inMemoryDBDAO.selectRow(currentID, mealsTable).getTime());
            request.setAttribute("mealType", inMemoryDBDAO.selectRow(currentID, mealsTable).getDescription());
            request.setAttribute("mealCal", inMemoryDBDAO.selectRow(currentID, mealsTable).getCalories());
            request.getRequestDispatcher(EDIT).forward(request, response);
        }else if (action.equals("add")){
            LOG.debug("_add in GET");
            request.getRequestDispatcher(ADD).forward(request, response);
        }else if (action.equals("list")){
            LOG.debug("_list in GET");
            request.setAttribute("mealListWithExceeded", DBUtil.getDBContentAsList(mealsTable));
            //response.sendRedirect("mealList.jsp");
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to mealList, method POST");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if(action.equals("addMeal")){

            LOG.debug("make ADD to meal List");
            inMemoryDBDAO.insert(mealsTable, getNewMealItem(request));
            request.setAttribute("mealListWithExceeded", DBUtil.getDBContentAsList(mealsTable));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        }else if(action.equals("deleteMeal")){
            LOG.debug("make delete to meal List");
            int id = Integer.parseInt(request.getParameter("id"));
            mealService.deleteMeal(id);
        }else if(action.equals("editMeal")){
            LOG.debug("make edit to meal List");
            Meal meal = setNewMealItem(request);
            inMemoryDBDAO.update(meal, mealsTable);
            request.setAttribute("mealListWithExceeded", DBUtil.getDBContentAsList(mealsTable));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        }else if (action.equals("list")){
            LOG.debug("make listAll to meal List");
        }
    }

    private Meal getNewMealItem(HttpServletRequest request){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
        Object mealDate = request.getParameter("mealDate");
        Object mealTime = request.getParameter("mealTime");
        LocalDateTime localDateTime = LocalDateTime.parse(mealDate.toString().concat(mealTime.toString()), formatter);
        String mealType = request.getParameter("mealType");
        Integer mealCal = Integer.parseInt(request.getParameter("mealCal"));
        return new Meal(id.getAndIncrement(), localDateTime, mealType, mealCal);
    }

    private Meal setNewMealItem(HttpServletRequest request){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
        //int id = Integer.parseInt(request.getParameter("id"));
        Object mealDate = request.getParameter("mealDate");
        Object mealTime = request.getParameter("mealTime");
        LocalDateTime localDateTime = LocalDateTime.parse(mealDate.toString().concat(mealTime.toString()), formatter);
        String description = request.getParameter("mealType");
        Integer calories = Integer.parseInt(request.getParameter("mealCal"));
        return new Meal(currentID, localDateTime, description, calories);
    }
}
