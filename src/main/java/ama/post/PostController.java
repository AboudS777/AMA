package ama.post;

import ama.account.User;
import ama.account.UserRepository;
import ama.authentication.Authenticator;
import ama.validation.Validator;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Stephane on 2017-03-19.
 */
@Controller
public class PostController {

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    @Autowired
    private CommentPostRepository commentPostRepository;

    @Autowired
    private Authenticator authenticator;

    @Autowired
    private Validator validator;

    @GetMapping("/create_submission")
    public String createAMASubmission(Model model) {
        model.addAttribute("submissionPost",new SubmissionPost());
        return "createsubmission";
    }

    @PostMapping("/create_submission")
    public String postAMASubmission(@ModelAttribute("submissionPost") SubmissionPost post, @RequestParam("postTags") String postTags, BindingResult result){
        validator.validate(post, result);
        User user = authenticator.getCurrentUser();
        if(result.hasErrors()||user == null) {
            return "createsubmission";
        } else {
            post.setOp(user);
            if(!postTags.equals("")) {
                post.setTags(new HashSet<>(Arrays.asList(postTags.split(","))));
            }
            submissionPostRepository.save(post);
            return "redirect:/";
        }
    }

    @GetMapping("/posts/{submission}")
    public String getSubmissionView(@PathVariable(value = "submission") String submission, Model model) {
        SubmissionPost post = submissionPostRepository.findByTitle(submission);
        if (post != null) {
            model.addAttribute("commentPost", new CommentPost());
            model.addAttribute("post", post);
            model.addAttribute("baseComments", getBaseComments(post));
            return "ama";
        }
        return "pageNotFound";
    }

    @PostMapping("/posts/{submission}")
    public String addCommentToSubmission(@PathVariable(value = "submission") String submission,@ModelAttribute("commentPost") CommentPost commentPost) {
        SubmissionPost post = submissionPostRepository.findByTitle(submission);
        User user = authenticator.getCurrentUser();
        if (post != null && commentPost != null && !commentPost.getText().equals("")){
            commentPost.setContext(post);
            commentPost.setOp(user);
            commentPostRepository.save(commentPost);
            return "redirect:/posts/{submission}";
        }
        return "pageNotFound";
    }

    @GetMapping("/posts/like")
    public String likeSubmissionPost(HttpServletRequest request, @RequestParam(value="id") String submissionId) {
        SubmissionPost post = submissionPostRepository.findById(Long.parseLong(submissionId));
        User user = authenticator.getCurrentUser();
        if (user != null && post != null) {
            post.likePost(user);
            submissionPostRepository.save(post);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
        return "pageNotFound";
    }

    @GetMapping("/comments/upvote")
    public String upvoteComment(HttpServletRequest request, @RequestParam("id") String commentId) {
        CommentPost comment = commentPostRepository.findById(Long.parseLong(commentId));
        User user = authenticator.getCurrentUser();
        if (user != null && comment != null) {
            comment.upvote(user);
            commentPostRepository.save(comment);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
        return "pageNotFound";
    }

    @GetMapping("/comments/downvote")
    public String downvoteComment(HttpServletRequest request, @RequestParam("id") String commentId) {
        CommentPost comment = commentPostRepository.findById(Long.parseLong(commentId));
        User user = authenticator.getCurrentUser();
        if (user != null && comment != null) {
            comment.downvote(user);
            commentPostRepository.save(comment);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
        return "pageNotFound";
    }

    @PostMapping("/comments/{id}/reply")
    public String replyToComment(HttpServletRequest request, @PathVariable(value="id") String id, String reply) {
        CommentPost context = commentPostRepository.findById(Long.parseLong(id));
        User user = authenticator.getCurrentUser();
        if (user != null && context != null && reply != null && reply != "") {
            CommentPost replyPost = new CommentPost(user, context, reply);
            commentPostRepository.save(replyPost);
        }
        return "redirect:" + request.getHeader("Referer");
    }

    private List<CommentPost> getBaseComments(Post context) {
        List<CommentPost> comments = commentPostRepository.findByContext(context);
        comments.sort(new CommentPostComparator());
        return comments;
    }
}

