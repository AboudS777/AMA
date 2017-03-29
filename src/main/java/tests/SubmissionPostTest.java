package tests;

import ama.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import ama.account.User;
import ama.account.UserService;
import ama.post.SubmissionPostRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class SubmissionPostTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    @Autowired
    private UserService userService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        userService.registerNewUserAccount(new User("user", "theman"));
    }


    @Test
    public void testPostAMASubmission() throws Exception {
        mvc.perform(post("/create_submission")
                .param("title", "AMA Title")
                .param("text", "AMA Text")
                .with(csrf())
                .with(user("user")))
                .andExpect(view().name("redirect:/"));

    }

    @Test
    public void testGetSubmissionView() throws Exception {
        mvc.perform(get("/posts/AMA Title")
                .with(csrf())
                .with(user("user")))
                .andExpect(view().name("ama"));
    }

    @Test
    public void testPostInvalidAMASubmission() throws Exception {
        mvc.perform(post("/create_submission")
                .param("title", "V")
                .param("text", "title is too short.")
                .with(csrf())
                .with(user("user")))
                .andExpect(view().name("createsubmission"));
    }

    @Test
    public void testGetNonExistentSubmissionView() throws Exception{
        mvc.perform(get("/posts/DoesntExist")
                .with(csrf())
                .with(user("user")))
                .andExpect(view().name("pageNotFound"));
    }

}
