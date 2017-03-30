package tests.UnitTests;

import ama.account.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestUser {

    private User sarran;

    @Before
    public void setUp() throws Exception {
        sarran  = new User();
    }

    @After
    public void tearDown() throws Exception {
       sarran = null;
    }

    @Test
    public void testUser() throws Exception {
        assertNotNull(sarran);
    }

    @Test
    public void testUsername() throws Exception {
        sarran.setUsername("sarrankan");
        assertEquals(sarran.getUsername(),"sarrankan");
    }

    @Test
    public void testPassword() throws Exception {
        sarran.setPassword("themans");
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

}
