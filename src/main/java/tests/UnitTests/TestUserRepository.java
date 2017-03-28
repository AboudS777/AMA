package tests.UnitTests;

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
        assertEquals(foundUser.getId().toString(),"1");
    }

}
