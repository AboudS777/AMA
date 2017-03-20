package ama.post;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Stephane on 2017-03-19.
 */
@Controller
public class PostController {

    @GetMapping("/create_submission")
    public String createAMASubmission(Model model) {
        model.addAttribute(new SubmissionPost());
        return "hello";
    }
}
