package tests;

import ama.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

/**
 * Created by abdallahsaket3 on 3/28/2017.
 */
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;


    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testShowRegistrationForm() throws Exception {
        mvc.perform(get("/registration").with(user("user"))).andExpect(status().isOk());

    }

    @Test
    public void testRegisterUserAccountOnSuccess() throws Exception {
        mvc.perform(post("/registration").with(user("user"))).andExpect(status().isOk());
    }

    @Test
    public void testRegisterUserAccountOnFailure() throws Exception {
        mvc.perform(post("/registration").with(anonymous())).andExpect(status().isOk());
    }

    @Test
    public void testLogIn() throws Exception {
        mvc.perform(get("/login").with(anonymous())).andExpect(status().isOk());

    }

    @Test
    public void testLoggedIn() throws Exception {
        mvc.perform(post("/login").with(user("user"))).andExpect(status().isOk());
    }

    @Test
    public void testViewAccount() throws Exception {
        mvc.perform(get("/account").with(user("user"))).andExpect(status().isOk());

    }

    @Test
    public void testSignOut() throws Exception {
        mvc.perform(logout()).andExpect(unauthenticated());

    }




}
