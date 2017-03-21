package ama.post;

import ama.account.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
/**
 * Created by Stephane on 2017-03-19.
 */
@Controller
public class PostController {

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    @Autowired
    private Validator validator;

    @GetMapping("/create_submission")
    public String createAMASubmission(Model model) {
        model.addAttribute("submissionPost",new SubmissionPost());
        return "postsubmission";
    }

    @PostMapping("/create_submission")
    public String postAMASubmission(@ModelAttribute("submissionPost") SubmissionPost post, BindingResult result){
        //need to add validation for post with result
        submissionPostRepository.save(post);
        return "home";
    }

    @GetMapping("/posts/{submission}")
    public String getSubmissionView(@PathVariable(value = "submission") String submission, Model model) {
        SubmissionPost post = submissionPostRepository.findByTitle(validator.parseURLParam(submission));
        //find all comments associated with post
        if (post != null) {
            model.addAttribute("post", post);
            //add all comments as attributes
            return "ama";
        }
        return "pageNotFound";
    }
}
