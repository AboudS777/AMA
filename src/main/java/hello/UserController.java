package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by sarrankanpathmanatha on 3/5/2017.
 */

@Controller
public class UserController {

    @GetMapping("/")
    public String showRegistrationForm(WebRequest request, Model model) {
        User userDto = new User();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/")
    public ModelAndView registerUserAccount
            (@ModelAttribute("user") @Valid User accountDto,
             BindingResult result, WebRequest request, Errors errors) {

        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        else {
            return new ModelAndView("successRegister", "user", accountDto);
        }

        return new ModelAndView("successRegister", "user", accountDto);

    }
    private User createUserAccount(User accountDto, BindingResult result) {
        User registered = null;
        UserService service = null;
        registered = service.registerNewUserAccount(accountDto);
        return registered;
    }




}
