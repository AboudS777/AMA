
import ama.account.User;
import ama.post.SubmissionPost;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

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
    public void testPostUser() throws Exception {
        assertEquals(submissionPost.getOp(),sarran);
    }

    @Test
    public void testUserLikedPostTwice() throws Exception {
        submissionPost.likePost(sarran);
        submissionPost.likePost(sarran);
        assertEquals(submissionPost.getAmountOfLikes(), 1);
    }

    @Test
    public void testAnotherUserLikedPost() throws Exception{
        User kan = new User("kan", "theman");
        submissionPost.likePost(kan);
        submissionPost.likePost(sarran);
        assertEquals(submissionPost.getAmountOfLikes(),2);
    }

    @Test
    public void addTagsToPost() throws Exception{
        HashSet<String> tags = new HashSet<String>();
        tags.add("Hello");
        tags.add("World");
        submissionPost.setTags(tags);
        assertEquals(submissionPost.getTags().size(),2);
    }



}
