package tests;

import ama.Application;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    SubmissionPostRepository submissionPostRepository;

    @Autowired
    CommentPostRepository commentPostRepository;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testBaseCommentPost() throws Exception {
        SubmissionPost post = new SubmissionPost();
        post.setTitle("This is my AMA.");
        post.setText("Test text.");
        submissionPostRepository.save(post);
        post = submissionPostRepository.findAll().get(0);
        CommentPost comment = new CommentPost();
        comment.setText("This is a test comment.");
        comment.setContext(post);
        mvc.perform(post("/posts/" + post.getTitle(), comment).with(user("user"))).andExpect(status().isOk());
    }
}
