package ama.validation;

import ama.account.User;
import ama.account.UserService;
import ama.post.SubmissionPost;
import ama.post.SubmissionPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Validator {

    @Autowired
    private UserService userService;

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    public void validate(User user, BindingResult result) {
        Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(user.getUsername());
        if (m.find()) {
            result.rejectValue("username", "SpecialChar.user.username", "Username cannot contain any special characters.");
        }
        if (user.getUsername().length() < 2 || user.getUsername().length() > 20) {
            result.rejectValue("username", "Size.user.username", "Username must be between 2 and 20 characters.");
        }
        if (user.getPassword().length() < 2 || user.getPassword().length() > 20) {
            result.rejectValue("password", "Size.user.password", "Password must be between 2 and 20 characters.");
        }
        if (userService.loadUserByUsername(user.getUsername()) != null) {
            result.rejectValue("username", "Duplicate.user.username", "Username is already taken.");
        }
    }

    public void validate(SubmissionPost post, BindingResult result) {
        if (post == null) {
            result.reject("Post is null");
        } else {
            if (post.getTitle().length() < 4 || post.getTitle().length() > 120) {
                result.rejectValue("title", "Size.post.title", "Title must be between 4 and 120 characters.");
            }
            if (submissionPostRepository.findByTitle(post.getTitle()) != null) {
                result.rejectValue("title", "Duplicate.post.title", "An AMA with this title already exists.");
            }

            if (post.getVotingCloses() == null) {
                result.rejectValue("votingCloses","Null.post.votingCloses","Must select a date that voting closes.");
            } else if (post.getVotingCloses().before(new Date())) {
                result.rejectValue("votingCloses","Past.post.votingCloses","This date is in the past.");
            }

            if (post.getAnswerCloses() == null) {
                result.rejectValue("answerCloses","Null.post.answerCloses","Must select a date that answer closes.");
            } else if (post.getAnswerCloses().before(new Date())) {
                result.rejectValue("answerCloses","Past.post.answerCloses","This date is in the past.");
            }
        }
    }
}