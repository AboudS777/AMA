package tests;

import ama.Application;
import ama.account.User;
import ama.account.UserRepository;
import ama.account.UserService;
import ama.post.CommentPost;
import ama.post.CommentPostRepository;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.validation.BindException;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
        CommentPost comment = new CommentPost();
        comment.setText("some comment.");
        commentPostRepository.save(comment);
    }

    @Test
    public void testUpvoteValidComment() throws Exception {
        mvc
                .perform(get("/comments/upvote")
                        .param("id", commentPostRepository.findAll().get(0).getId().toString())
                        .with(csrf())
                        .with(user("sarran")))
                .andExpect(view().name("redirect:null"));
    }

    @Test
    public void testUpvoteInvalidComment() throws Exception {
        mvc
                .perform(get("/comments/upvote")
                        .param("id", "10")
                        .with(csrf())
                        .with(user("sarran")))
                .andExpect(view().name("pageNotFound"));
    }

    @Test
    public void testDownvoteValidComment() throws Exception {
        mvc
                .perform(get("/comments/downvote")
                        .param("id", commentPostRepository.findAll().get(0).getId().toString())
                        .with(csrf())
                        .with(user("sarran")))
                .andExpect(status().isFound());
    }

    @Test
    public void testDownvoteInvalidComment() throws Exception {
        mvc
                .perform(get("/comments/downvote")
                        .param("id", "10")
                        .with(csrf())
                        .with(user("sarran")))
                .andExpect(view().name("pageNotFound"));
    }
}
