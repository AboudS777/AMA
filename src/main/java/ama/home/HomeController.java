package ama.home;

import ama.post.SubmissionPost;
import ama.post.SubmissionPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("submissionPosts", submissionPostRepository.findAll());
        return "home";
    }

    @ModelAttribute("allSubmissions")
    public List<SubmissionPost> getAllSubmissions() {
        return submissionPostRepository.findAll();
    }
}
