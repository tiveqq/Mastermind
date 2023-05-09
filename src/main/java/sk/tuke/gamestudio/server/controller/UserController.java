package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.UserServiceJPA;

import java.util.Date;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    @Autowired
    private UserServiceJPA userService;
    private User loggedUser;

    @PostMapping("/login")
    public String login(@RequestParam("login") String login, @RequestParam("passwd") String passwd, Model model) {
        if (userService.login(login, passwd)) {
            loggedUser = new User(login, passwd, new Date());
            return "redirect:/mastermind/new";
        } else {
            model.addAttribute("errorMessageLogin", "Incorrect password or login");
        }
        return "index";
    }

    @PostMapping("/register")
    public String register(String login, String passwd, Model model) {
        var existingUser = userService.findByUsername(login);
        if (existingUser != null) {
            model.addAttribute("errorMessageRegister", "The user with this name already exists");
            return "index";
        }
        userService.register(new User(login, passwd, new Date()));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        loggedUser = null;
        return "redirect:/";
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return loggedUser != null;
    }

}
