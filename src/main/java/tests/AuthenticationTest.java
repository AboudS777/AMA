package tests;

import ama.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class AuthenticationTest {

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
    }

    @Test
    public void testLoginValidUser() throws Exception {
        User user = new User("sarran", "theman");
        userService.registerNewUserAccount(user);
        mvc
                .perform(formLogin().user("sarran").password("theman"))
                .andExpect(authenticated());
    }

    @Test
    public void testLoginInvalidUser() throws Exception {
        mvc
                .perform(formLogin().password("invalid"))
                .andExpect(unauthenticated());
    }

    @Test
    public void testLogout() throws Exception {
        mvc
                .perform(logout()).andExpect(unauthenticated());
    }
}
