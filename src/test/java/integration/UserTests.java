
import ama.Application;
import ama.account.User;
import ama.account.UserRepository;
import ama.account.UserService;

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

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by ryanbillard on 4/3/2017.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class UserTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private MockMvc mvc;
    private User user1 = new User("ryan", "abcd");
    private User user2 = new User("sarran", "theman");

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        if(userService.loadUserByUsername(user1.getUsername()) == null) {
            userService.registerNewUserAccount(user1);
        }
        if(userService.loadUserByUsername(user2.getUsername()) == null) {
            userService.registerNewUserAccount(user2);
        }
    }


    @Test
    public void testShowUserDetailsPage() throws Exception {
        mvc
                .perform(get("/user/" + user1.getUsername())
                .with(csrf())
                .with(user(user2)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user"));
    }

    @Test
    public void testFollowUser() throws Exception {
        mvc
                .perform(get("/user/" + user1.getUsername() + "/follow")
                .with(csrf())
                .with(user(user2)))
                .andExpect(status().is3xxRedirection());
        assertTrue(userRepository.findByUsername(user2.getUsername()).getFollowing().contains(user1));
    }

}
