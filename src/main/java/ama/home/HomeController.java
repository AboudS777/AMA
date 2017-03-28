package ama.home;

import ama.account.User;
import ama.account.UserRepository;
import ama.post.CommentPost;
import ama.post.SubmissionPost;
import ama.post.SubmissionPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * Created by Stephane on 2017-03-19.
 */
@Controller
public class HomeController {

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("currentUser", user);
        model.addAttribute("submissionPosts", submissionPostRepository.findAll());
        return "home";
    }
}
