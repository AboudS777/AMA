package tests.UnitTests;

import ama.Application;
import ama.account.User;
import ama.account.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestUserService {

    @Autowired
    private UserService userService;

    private User ryan;
    private User sarran;
    @Before
    public void setUp() throws Exception {
        ryan = new User("ryan","theman");
        sarran = new User("sarran","theman");
    }

    @After
    public void tearDown() throws Exception {
        ryan = null;
    }

    @Test
    public void testRegisterNewUserAccount() throws Exception {
        User registeredUser = userService.registerNewUserAccount(sarran);
        assertEquals(registeredUser.getUsername(),sarran.getUsername());
        assertEquals(registeredUser.getPassword(),sarran.getPassword());
    }

    @Test(expected= NullPointerException.class)
    public void testLoadUserThatDoesNotExistByUsername() throws Exception {
        UserDetails loadedUser = userService.loadUserByUsername(ryan.getUsername());
        assertEquals(loadedUser.getUsername(),ryan.getUsername());
    }
    @Test
    public void testLoadUserThatExistsByUsername() throws Exception {
        userService.registerNewUserAccount(ryan);
        UserDetails loadedUser = userService.loadUserByUsername(ryan.getUsername());
        assertEquals(loadedUser.getUsername(),ryan.getUsername());
    }

}
