package tests.unit;

import ama.Application;
import ama.account.User;
import ama.account.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestUserRepository {

    @Autowired
    private UserRepository userRepository;

    private User sarran;

    @Before
    public void setUp() throws Exception {
        sarran  = new User("sarran","theman");
    }

    @After
    public void tearDown() throws Exception {
        sarran = null;
    }

    @Test
    public void testFindUserByUsername() throws Exception {
        userRepository.save(sarran);
        User foundUser = userRepository.findByUsername(sarran.getUsername());
        assertEquals(foundUser.getUsername(),sarran.getUsername());
        assertEquals(foundUser.getPassword(),sarran.getPassword());
        assert(foundUser.getId() != null);
    }

    @Test
    public void testFindByFollowers() throws Exception {
        User ryan = new User("ryan", "abcd");
        User phane = new User("phane", "abcd");
        User aboud = new User("aboud", "abcd");
        userRepository.save(ryan);
        userRepository.save(phane);
        userRepository.save(aboud);
        sarran.follow(ryan);
        sarran.follow(phane);
        userRepository.save(sarran);
        Set<User> following = userRepository.findByFollowers(sarran);
        assertTrue(following.contains(ryan));
        assertTrue(following.contains(phane));
        assertFalse(following.contains(aboud));
    }

}
