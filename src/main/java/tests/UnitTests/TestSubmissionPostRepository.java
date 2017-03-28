package tests.UnitTests;

import ama.Application;
import ama.post.SubmissionPost;
import ama.post.SubmissionPostRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestSubmissionPostRepository {

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    private SubmissionPost submissionPost;

    @Before
    public void setUp() throws Exception {
        submissionPost = new SubmissionPost();
    }

    @After
    public void tearDown() throws Exception {
        submissionPost = null;
    }

    @Test
    public void testCommentPost() throws Exception {
        assertNotNull(submissionPost);
    }

    @Test
    public void testFindPostByTitle() throws Exception{
        submissionPost.setTitle("Title");
        submissionPost.setText("Description");
        submissionPostRepository.save(submissionPost);
        SubmissionPost foundPost = submissionPostRepository.findByTitle(submissionPost.getTitle());
        assertEquals(foundPost.getTitle(),submissionPost.getTitle());
        assertEquals(foundPost.getText(),submissionPost.getText());
    }
}
