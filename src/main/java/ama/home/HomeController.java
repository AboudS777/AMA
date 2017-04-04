package ama.home;

import ama.account.User;
import ama.authentication.Authenticator;
import ama.post.SubmissionPost;
import ama.post.SubmissionPostComparator;
import ama.post.SubmissionPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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

    @GetMapping("/search")
    public String searchByTag(@RequestParam(value="search") String search, Model model) {
        model.addAttribute("foundSubmissions", getFoundSubmissions(search));
        return "search";
    }

    private List<SubmissionPost> getAllSubmissions() {
        List<SubmissionPost> posts = submissionPostRepository.findAll();
        posts.sort(new SubmissionPostComparator());
        return posts;
    }

    private List<SubmissionPost> getFoundSubmissions(String search) {
        HashSet<SubmissionPost> foundPosts = new HashSet<>();
        if(!search.equals("")) {
            HashSet<String> searchWords = new HashSet<>(Arrays.asList(search.split(" ")));
            for (String word :searchWords) {
                List<SubmissionPost> tagPosts = submissionPostRepository.findByTags(word);
                for(SubmissionPost post : tagPosts) {
                    foundPosts.add(post);
                }
            }
        }
        List<SubmissionPost> postList = new ArrayList<>(foundPosts);
        postList.sort(new SubmissionPostComparator());
        return postList;
    }
}
