package tests;

import ama.Application;
import ama.account.User;
import ama.account.UserService;
import ama.post.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by sarrankanpathmanatha on 3/22/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class CommentPostTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    @Autowired
    private CommentPostRepository commentPostRepository;

    @Autowired
    private UserService userService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        User user = new User("sarran", "theman");
        if(userService.loadUserByUsername(user.getUsername()) == null) {
            userService.registerNewUserAccount(user);
        }
    }

    @Test
    public void testAddCommentToValidSubmission() throws Exception {
        SubmissionPost post = new SubmissionPost();
        post.setTitle("Valid AMA Submission");
        post.setText("Test text.");
        post = submissionPostRepository.save(post);

        String commentText = "test comment on valid submission";
        mvc
                .perform(post("/posts/" + post.getTitle())
                        .with(csrf())
                        .with(user("sarran"))
                        .param("text", commentText))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/Valid%20AMA%20Submission"));
        assert(commentPostRepository.findByContext(post).get(0).getText().equals(commentText));
    }

    @Test
    public void testAddCommentToInvalidSubmission() throws Exception {
        String commentText = "test comment on invalid submission";
        mvc
                .perform(post("/posts/invalid")
                        .with(csrf())
                        .with(user("sarran"))
                        .param("text", commentText))
                .andExpect(status().isOk())
                .andExpect(view().name("pageNotFound"));
    }

    @Test
    public void testAddInvalidCommentToValidSubmission() throws Exception {
        SubmissionPost post = new SubmissionPost();
        post.setTitle("Add invalid comment to this submission");
        post.setText("Test text.");
        post = submissionPostRepository.save(post);
        mvc
                .perform(post("/posts/" + post.getTitle())
                        .with(csrf())
                        .with(user("sarran"))
                        .param("text", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("pageNotFound"));
        assert(commentPostRepository.findByContext(post).size() == 0);
    }

    @Test
    public void testReplyToValidComment() throws Exception {
        CommentPost comment = new CommentPost();
        comment.setText("this is a base comment.");
        comment = commentPostRepository.save(comment);

        String replyText = "This is a reply.";
        mvc
                .perform(post("/comments/" + comment.getId().toString() + "/reply")
                        .with(csrf())
                        .with(user("sarran"))
                        .param("reply", replyText))
                .andExpect(status().is3xxRedirection());
        assert(commentPostRepository.findByContext(comment).get(0).getText().equals(replyText));
    }

    @Test
    public void testReplyToInvalidComment() throws Exception {
        String replyText = "This is a reply to an invalid comment.";
        int numberOfComments = commentPostRepository.findAll().size();
        mvc
                .perform(post("/comments/10/reply")
                        .with(csrf())
                        .with(user("sarran"))
                        .param("reply", replyText))
                .andExpect(status().is3xxRedirection());

        assert(commentPostRepository.findAll().size() == numberOfComments);
    }
}
