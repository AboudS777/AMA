
import ama.Application;
import ama.account.User;
import ama.account.UserRepository;
import ama.authentication.Authenticator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Stephane on 2017-03-31.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestAuthenticator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Authenticator authenticator;

    @Test
    public void testGetCurrentUserWhileLoggedIn() {
        String loggedInUser = "loggedInUser";
        userRepository.save(new User(loggedInUser, "theman"));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loggedInUser, "theman"));
        assert(authenticator.getCurrentUser().getUsername().equals(loggedInUser));
    }

    @Test
    public void testGetCurrentUserWhileNotLoggedIn() {
        assert(authenticator.getCurrentUser() == null);
    }
}
