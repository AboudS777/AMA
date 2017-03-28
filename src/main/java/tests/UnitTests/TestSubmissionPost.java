package tests.UnitTests;

import ama.account.User;
import ama.post.SubmissionPost;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSubmissionPost {

    private SubmissionPost submissionPost;
    private User sarran;

    @Before
    public void setUp() throws Exception {
        sarran  = new User("sarran","theman");
        submissionPost = new SubmissionPost(sarran,"Title","This is a submissionPost");
    }

    @After
    public void tearDown() throws Exception {
        sarran = null;
        submissionPost = null;
    }

    @Test
    public void testSubmissionPost() throws Exception {
        assertNotNull(sarran);
        assertNotNull(submissionPost);
    }

    @Test
    public void testPostUser() throws Exception {
        assertEquals(submissionPost.getUser(),sarran);
    }

    @Test
    public void testOpLikedPost() throws Exception {
        assertEquals(submissionPost.getUsersWhoLiked(), 1);
    }

    @Test
    public void testUserLikedPost() throws Exception{
        User kan = new User("kan", "theman");
        submissionPost.likePost(kan);
        assertEquals(submissionPost.getUsersWhoLiked(),2);
    }



}
