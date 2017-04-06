package ama.post;

import ama.account.User;
import ama.authentication.Authenticator;
import ama.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
            HashSet<String> tags = new HashSet<>();
            if(!postTags.equals("")) {
                tags = new HashSet<>(Arrays.asList(postTags.split(",")));
            }
            List<String> titleWords = Arrays.asList(post.getTitle().split(" "));
            for (String word : titleWords) {
                tags.add(word);
            }
            tags.add(user.getUsername());
            post.setTags(tags);
            submissionPostRepository.save(post);
            return "redirect:/";
        }
    }

    @GetMapping("/posts/{submission}")
    public String getSubmissionView(@PathVariable(value = "submission") String submissionId, Model model) {
        try {
            SubmissionPost post = submissionPostRepository.findById(Long.parseLong(submissionId));
            if (post != null) {
                model.addAttribute("commentPost", new CommentPost());
                model.addAttribute("post", post);
                model.addAttribute("baseComments", getBaseComments(post));
                return "ama";
            }
            return "pageNotFound";
        } catch(NumberFormatException n) {
            return "pageNotFound";
        }
    }

    @PostMapping("/posts/{submission}")
    public String addCommentToSubmission(@PathVariable(value = "submission") String submissionId,@ModelAttribute("commentPost") CommentPost commentPost) {
        SubmissionPost post = submissionPostRepository.findById(Long.parseLong(submissionId));
        User user = authenticator.getCurrentUser();
        Date now = new Date();
        if (post != null && commentPost != null && !commentPost.getText().equals("") && now.before(post.getVotingCloses())){
            commentPost.setContext(post);
            commentPost.setOp(user);
            commentPostRepository.save(commentPost);
        }
        return "redirect:/posts/{submission}";
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
        if (comment != null) {
            SubmissionPost post = (SubmissionPost) comment.getContext();
            Date now = new Date();
            if (user != null && now.before(post.getVotingCloses())) {
                comment.upvote(user);
                commentPostRepository.save(comment);
            }
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/comments/downvote")
    public String downvoteComment(HttpServletRequest request, @RequestParam("id") String commentId) {
        CommentPost comment = commentPostRepository.findById(Long.parseLong(commentId));
        User user = authenticator.getCurrentUser();
        if (comment != null) {
            SubmissionPost post = (SubmissionPost)comment.getContext();
            Date now = new Date();
            if (user != null && now.before(post.getVotingCloses())) {
                comment.downvote(user);
                commentPostRepository.save(comment);
            }
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/comments/{id}/reply")
    public String replyToComment(HttpServletRequest request, @PathVariable(value="id") String id, String reply) {
        CommentPost context = commentPostRepository.findById(Long.parseLong(id));
        User user = authenticator.getCurrentUser();
        if (context != null) {
            SubmissionPost post = (SubmissionPost)context.getContext();
            Date now = new Date();
            if (user != null && reply != null && reply != "" && now.before(post.getAnswerCloses())) {
                CommentPost replyPost = new CommentPost(user, context, reply);
                commentPostRepository.save(replyPost);
            }
            return "redirect:" + request.getHeader("Referer");
        }
        return "pageNotFound";
    }

    private List<CommentPost> getBaseComments(Post context) {
        List<CommentPost> comments = commentPostRepository.findByContext(context);
        comments.sort(new CommentPostComparator());
        return comments;
    }
}

