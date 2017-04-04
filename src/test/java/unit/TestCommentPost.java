
import ama.Application;
import ama.account.User;
import ama.post.CommentPost;
import ama.post.SubmissionPost;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestCommentPost {

    private CommentPost commentPost;
    private SubmissionPost submissionPost;
    private User sarran;
    private User kan;

    @Before
    public void setUp() throws Exception {
        sarran  = new User("sarran","theman");
        kan = new User("kan","theman");
        submissionPost = new SubmissionPost(sarran,"Title","This is a post");
        commentPost = new CommentPost(kan,submissionPost,"This a comment");
    }

    @After
    public void tearDown() throws Exception {
        sarran = null;
        kan = null;
        submissionPost = null;
        commentPost = null;
    }

    @Test
    public void testCommentOnPost() throws Exception {
        assertEquals(commentPost.getOp(),kan);
    }

    @Test
    public void testGetContext() throws Exception {
        assertEquals(commentPost.getContext(),submissionPost);
    }

    @Test
    public void testUpvotes() throws Exception{
        commentPost.upvote(sarran);
        assertEquals(commentPost.getUpvotes(),1);
        commentPost.upvote(sarran);
        assertEquals(commentPost.getUpvotes(),1);
    }

    @Test
    public void testDownvote() throws Exception{
        commentPost.downvote(sarran);
        assertEquals(commentPost.getDownvotes(),1);

    }

    @Test
    public void testUpvoteThenDownVote() throws Exception {
        commentPost.upvote(sarran);
        commentPost.downvote(sarran);
        assertEquals(commentPost.getUpvotes(),0);
        assertEquals(commentPost.getDownvotes(), 1);
    }
    @Test
    public void testgetPoints() throws Exception{
        commentPost.upvote(sarran);
        commentPost.downvote(kan);
        assertEquals(commentPost.getPoints(),0);
    }


}
