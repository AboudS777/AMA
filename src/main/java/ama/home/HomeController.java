package ama.home;

import ama.account.User;
import ama.account.UserRepository;
import ama.authentication.Authenticator;
import ama.post.CommentPost;
import ama.post.SubmissionPost;
import ama.post.SubmissionPostComparator;
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
    private Authenticator authenticator;

    @GetMapping("/")
    public String home(Model model) {
        User user = authenticator.getCurrentUser();
        model.addAttribute("currentUser", user);
        model.addAttribute("submissionPosts", getAllSubmissions());
        return "home";
    }

    private List<SubmissionPost> getAllSubmissions() {
        List<SubmissionPost> posts = submissionPostRepository.findAll();
        posts.sort(new SubmissionPostComparator());
        return posts;
    }
}
