package tests;

import ama.Application;
import ama.post.CommentPost;
import ama.post.CommentPostRepository;
import ama.post.SubmissionPost;
import ama.post.SubmissionPostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.test.web.*;

/**
 * Created by stephanernst on 3/22/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class CommentVoteTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    CommentPostRepository postRepository;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testUpvoteComment() throws Exception {
        CommentPost post = new CommentPost();
        post.setText("hello");
        postRepository.save(post);
        post = postRepository.findAll().get(0);
        mvc.perform(get("/comments/upvote")
                .param("id", post.getId().toString())
                .with(csrf())
                .with(user("user")))
                .andExpect(status().isOk());
    }

    @Test
    public void testDownvoteComment() throws Exception {
        CommentPost post = new CommentPost();
        post.setText("hello");
        postRepository.save(post);
        post = postRepository.findAll().get(0);
        mvc.perform(get("/comments/downvote")
                .param("id", post.getId().toString())
                .with(csrf())
                .with(user("user")))
                .andExpect(status().isOk());
    }
}
