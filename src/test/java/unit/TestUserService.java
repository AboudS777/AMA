
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
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
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
        if(userService.loadUserByUsername(sarran.getUsername()) == null) {
            userService.registerNewUserAccount(sarran);
        }
        assertEquals(sarran.getUsername(),sarran.getUsername());
        assertEquals(sarran.getPassword(),sarran.getPassword());
    }

    @Test
    public void testLoadUserThatDoesNotExistByUsername() throws Exception {
        User loadedUser = userService.loadUserByUsername(ryan.getUsername());
        assertEquals(loadedUser, null);
    }
    @Test
    public void testLoadUserThatExistsByUsername() throws Exception {
        if(userService.loadUserByUsername(ryan.getUsername()) == null) {
            userService.registerNewUserAccount(ryan);
        }
        UserDetails loadedUser = userService.loadUserByUsername(ryan.getUsername());
        assertEquals(loadedUser.getUsername(),ryan.getUsername());
    }

}
