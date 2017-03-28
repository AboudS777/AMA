package tests.UnitTests;

import ama.Application;
import ama.post.CommentPost;
import ama.post.CommentPostRepository;
import ama.post.SubmissionPost;
import ama.post.SubmissionPostRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestCommentPostRepository {

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    @Autowired
    private CommentPostRepository commentPostRepository;

    private CommentPost commentPost;
    private SubmissionPost submissionPost;

    @Before
    public void setUp() throws Exception {
        commentPost = new CommentPost();
        submissionPost = new SubmissionPost();
    }

    @After
    public void tearDown() throws Exception {
        commentPost = null;
        submissionPost = null;
    }

    @Test
    public void testCommentPost() throws Exception {
        assertNotNull(commentPost);
        assertNotNull(submissionPost);
    }

    @Test
    public void testFindPostByTitle() throws Exception{
        submissionPost.setTitle("title");
        submissionPost.setText("Description");
        submissionPostRepository.save(submissionPost);
        commentPost.setText("Comment");
        commentPost.setContext(submissionPost);
        commentPostRepository.save(commentPost);
        List<CommentPost> foundComments = commentPostRepository.findByContext(submissionPost);
        assertEquals(foundComments.get(0).getText(),commentPost.getText());
    }
}
