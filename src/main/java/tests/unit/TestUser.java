package tests.unit;

import ama.account.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class TestUser {

    private User sarran;

    @Before
    public void setUp() throws Exception {
        sarran  = new User();
        sarran.setUsername("sarrankan");
        sarran.setPassword("themans");
    }

    @After
    public void tearDown() throws Exception {
       sarran = null;
    }

    @Test
    public void testUsername() throws Exception {
        assertEquals(sarran.getUsername(),"sarrankan");
    }

    @Test
    public void testPassword() throws Exception {
        assertEquals(sarran.getPassword(),"themans");
    }

    @Test
    public void testId() throws Exception{
        Long id = new Long(1);
        sarran.setId(id);
        assertEquals(sarran.getId(),id);
    }

    @Test
    public void testIsAccountNonExpired() throws Exception{
        assertTrue(sarran.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() throws Exception{
        assertTrue(sarran.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() throws Exception{
        assertTrue(sarran.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() throws Exception {
        assertTrue(sarran.isEnabled());
    }

    @Test
    public void testGetAuthorities() throws Exception {
        assertNull(sarran.getAuthorities());
    }

    @Test
    public void testFollow() throws Exception {
        User ryan = new User();
        ryan.setUsername("ryan");
        sarran.follow(ryan);
        assertTrue(sarran.getFollowing().contains(ryan));
    }

    @Test
    public void testUnfollow() throws Exception {
        User ryan = new User();
        ryan.setUsername("ryan");
        sarran.follow(ryan);
        sarran.unfollow(ryan);
        assertFalse(sarran.getFollowing().contains(ryan));
    }

    @Test
    public void testIsFollowedBy() throws Exception {
        User ryan = new User();
        ryan.setUsername("ryan");
        HashSet<User> followers = new HashSet<>();
        followers.add(sarran);
        ryan.setFollowers(followers);
        assertTrue(ryan.isFollowedBy(sarran.getUsername()));
        assertFalse(ryan.isFollowedBy("other"));
    }

}
