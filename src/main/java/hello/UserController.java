package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by sarrankanpathmanatha on 3/5/2017.
 */

@Controller
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    UserRepository repository;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid User user, BindingResult result) {
        User registered = new User();
        if (!result.hasErrors()) {
            registered = service.registerNewUserAccount(user);
        }
        if (registered == null) {
            result.rejectValue("username", "message.regError");
        }
        if (result.hasErrors()) {
            return "registration";
        }
        else {
            return "registered";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loggedIn(Model model) {
        return "hello";
    }

    @PostMapping("/registered")
    public String registered(Model model) {
        return "login";
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        return "hello";
    }

    @PostMapping("/hello")
    public String signOut(Model model) {
        return "home";
    }
}
