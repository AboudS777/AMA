package ama.account;

import ama.validation.Validator;
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
    private UserService userService;

    @Autowired
    private Validator validator;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid User user, BindingResult result) {
        validator.validate(user, result);

        if (result.hasErrors()) {
            return "registration";
        }
        userService.registerNewUserAccount(user);
        return "registered";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loggedIn(Model model) {
        return "redirect:/account";
    }

    @GetMapping("/account")
    public String viewAccount(Model model) {
        return "account";
    }

    @PostMapping("/logout")
    public String signOut(Model model) {
        return "home";
    }
}
