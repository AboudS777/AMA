package ama.account;

import ama.authentication.Authenticator;
import ama.post.SubmissionPostRepository;
import ama.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by sarrankanpathmanatha on 3/5/2017.
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private Authenticator authenticator;

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

    @GetMapping("/user/{username}")
    public String viewUserAccount(@PathVariable(value = "username") String username, Model model) {
        User user = userRepository.findByUsername(username);
        if (!username.equals("") && user != null) {
            model.addAttribute("user", user);
            model.addAttribute("submissionPosts", submissionPostRepository.findByOp(user));
            model.addAttribute("likedPosts", submissionPostRepository.findAllByUsersWhoLiked(user));
            return "user";
        }
        return "pageNotFound";
    }

    @GetMapping("user/{username}/follow")
    public String followUser(HttpServletRequest request, @PathVariable(value = "username") String username) {
        User loggedIn = authenticator.getCurrentUser();
        User user = userRepository.findByUsername(username);
        if (user != null && loggedIn != null && user != loggedIn) {
            loggedIn.follow(user);
            userRepository.save(loggedIn);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
        return "pageNotFound";
    }

    @GetMapping("user/{username}/unfollow")
    public String unfollowUser(HttpServletRequest request, @PathVariable(value = "username") String username) {
        User loggedIn = authenticator.getCurrentUser();
        User user = userRepository.findByUsername(username);
        if (user != null && loggedIn != null && user != loggedIn) {
            loggedIn.unfollow(user);
            userRepository.save(loggedIn);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
        return "pageNotFound";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loggedIn(Model model) {
        return "redirect:/account";
    }

    @PostMapping("/logout")
    public String signOut(Model model) {
        return "home";
    }
}
