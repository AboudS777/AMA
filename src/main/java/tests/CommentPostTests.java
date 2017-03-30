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

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by sarrankanpathmanatha on 3/22/2017.
 */

@Transactional
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

        userService.registerNewUserAccount(new User("user", "pass"));
    }

    @Test
    public void testAddCommentToValidSubmission() throws Exception {
        SubmissionPost post = new SubmissionPost();
        post.setTitle("This is my AMA");
        post.setText("Test text.");
        submissionPostRepository.save(post);
        mvc
                .perform(post("/posts/" + post.getTitle())
                        .with(csrf())
                        .with(user("user"))
                        .param("text", "This is a test comment"))
                .andExpect(view().name("redirect:/posts/{submission}"));
    }

    @Test
    public void testAddCommentToInvalidSubmission() throws Exception {
        mvc
                .perform(post("/posts/invalid")
                        .with(csrf())
                        .with(user("user"))
                        .param("text", "This is a test comment"))
                .andExpect(view().name("pageNotFound"));
    }

    @Test
    public void testAddInvalidCommentToValidSubmission() throws Exception {
        SubmissionPost post = new SubmissionPost();
        post.setTitle("This is my AMA");
        post.setText("Test text.");
        submissionPostRepository.save(post);
        mvc
                .perform(post("/posts/" + post.getTitle())
                        .with(csrf())
                        .with(user("user"))
                        .param("text", ""))
                .andExpect(view().name("pageNotFound"));
    }

    @Test
    public void testReplyToValidComment() throws Exception {
        CommentPost comment = new CommentPost();
        comment.setText("this is a base comment.");
        comment = commentPostRepository.save(comment);
        mvc
                .perform(post("/comments/" + comment.getId().toString() + "/reply")
                        .with(csrf())
                        .with(user("user"))
                        .param("reply", "this is a reply"))
                .andExpect(view().name("redirect:null"));
    }

    @Test
    public void testReplyToInValidComment() throws Exception {
        mvc
                .perform(post("/comments/2/reply")
                        .with(csrf())
                        .with(user("user"))
                        .param("reply", "this is a reply"))
                .andExpect(view().name("pageNotFound"));
    }
}
