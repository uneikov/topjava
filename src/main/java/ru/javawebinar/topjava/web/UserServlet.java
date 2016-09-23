package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class UserServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);
    private ProfileRestController profileRestController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to userList");
      /*  String _userId = request.getParameter("userId");
        //int userId = _userId != null ? Integer.parseInt(_userId) : 0;
        AuthorizedUser.setId(_userId != null ? Integer.parseInt(_userId) : 0);
        request.getRequestDispatcher("meals").forward(request, response);*/
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to userList");

        String password = request.getParameter("password");
        String redirect = "1234".equals(password) ? "meals" : "index.html";

        String userIdFromAuthPage = request.getParameter("userId");
        AuthorizedUser.setId(Integer.valueOf(userIdFromAuthPage));

        request.getSession().setAttribute("userId", userIdFromAuthPage);
        response.sendRedirect(redirect);
    }
}
