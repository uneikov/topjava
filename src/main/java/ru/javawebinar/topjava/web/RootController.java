package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.web.user.AbstractUserController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController extends AbstractUserController {
    private Logger LOG = LoggerFactory.getLogger(RootController.class);
    @GetMapping("/")
    public String root() {
        return "redirect:meals";
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model,
                        @RequestParam(value = "error", required = false) boolean error,
                        @RequestParam(value = "message", required = false) String message) {
        model.put("error", error);
        model.put("message", message);
        return "login";
    }

    @GetMapping("/meals")
    public String meals() {
        return "meals";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            //  TODO: 14.11.2016
            return "profile";
        } else {
            userTo.setId(AuthorizedUser.id());
            super.update(userTo);
            AuthorizedUser.get().update(userTo);
            status.setComplete();
            return "redirect:meals";
        }
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        model.addAttribute("error", false);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            //StringBuilder sb = new StringBuilder();
            //result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
            //return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
            //throw new InternalServerErrorException("Unprocessable form input:<br>" + sb.toString());
            model.addAttribute("register", true);
            return "profile";
        } else {
            super.create(UserUtil.createNewFromTo(userTo));
            status.setComplete();
            return "redirect:login?message=app.registered";
        }
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userTo", new UserTo());
        modelAndView.addObject("register", true);
        modelAndView.addObject("error", true);
        //modelAndView.addObject("url", request.getRequestURL());
        modelAndView.setViewName("profile");
        return modelAndView;
    }

   /* @ExceptionHandler(DataIntegrityViolationException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ResponseBody
    public String handleDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException ex){
        ModelMap model =new ModelMap();
       *//* model.addAttribute("register", true);
        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());*//*
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        model.addAttribute("error", true);
        return "profile";
    }*/

}
