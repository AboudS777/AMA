package tests;

import ama.Application;
import ama.account.User;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Stephane on 2017-03-30.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class RegistrationTests {
    @Autowired
    private WebApplicationContext context;

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
    public void testRegisterValidUser() throws Exception {
        mvc
                .perform(post("/registration")
                        .with(csrf())
                        .param("username", "newUser")
                        .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("registered"));
    }

    @Test
    public void testRegisterTooShortUsernameAndPassword() throws Exception {
        mvc
                .perform(post("/registration")
                        .with(csrf())
                        .param("username", "u")
                        .param("password", "p"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void testRegisterTooLongUsernameAndPassword() throws Exception {
        mvc
                .perform(post("/registration")
                        .with(csrf())
                        .param("username", "ushetfdbdatdftheanghh")
                        .param("password", "phgtyhgfdsadethedafet"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void testRegisterSpecialCharsInUsername() throws Exception {
        mvc
                .perform(post("/registration")
                        .with(csrf())
                        .param("username", "@$sh&")
                        .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void testRegisterDuplicateUser() throws Exception {
        mvc
                .perform(post("/registration")
                        .with(csrf())
                        .param("username", "sarran")
                        .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(view().name("registration"));
    }
}
