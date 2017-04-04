

import ama.Application;
import ama.account.User;
import ama.account.UserRepository;
import ama.post.SubmissionPost;
import ama.post.SubmissionPostRepository;
import ama.validation.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestValidator {

    @Autowired
    private Validator validator;

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    @Autowired
    private UserRepository userRepository;

    private User sarran;
    private SubmissionPost submissionPost;
    private BindingResult result;

    @Before
    public void setUp() throws Exception {
        sarran = new User("sarrankan","theman");
        submissionPost = new SubmissionPost();
    }

    @After
    public void tearDown() throws Exception{
        result = null;
        sarran = null;
        submissionPost = null;
    }

    @Test
    public void testValidUser() throws Exception {
        validator.validate(sarran,result);
        result = new BeanPropertyBindingResult(sarran,"sarran");
        assertEquals(result.hasErrors(),false);
    }

    @Test
    public void testInvalidUsername() throws Exception {
        sarran.setUsername("s");
        result = new BeanPropertyBindingResult(sarran,"sarran");
        validator.validate(sarran,result);
        assertEquals(result.hasFieldErrors("username"),true);
    }

    @Test
    public void testInvalidPassword() throws Exception {
        sarran.setPassword("p");
        result = new BeanPropertyBindingResult(sarran,"sarran");
        validator.validate(sarran,result);
        assertEquals(result.hasFieldErrors("password"),true);
    }

    @Test
    public void testDuplicateUser() throws Exception {
        userRepository.save(sarran);
        User newUser = new User(sarran.getUsername(),sarran.getPassword());
        result = new BeanPropertyBindingResult(newUser,"newUser");
        validator.validate(newUser,result);
        assertEquals(result.hasFieldErrors("username"),true);
    }

    @Test
    public void testValidPost() throws Exception {
        Date futureDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(futureDate);
        calendar.add(Calendar.DATE, 1);

        futureDate = calendar.getTime();
        submissionPost.setTitle("TITLE");
        submissionPost.setText("Description");
        submissionPost.setVotingCloses(futureDate);
        submissionPost.setAnswerCloses(futureDate);
        result = new BeanPropertyBindingResult(submissionPost,"submissionPost");
        validator.validate(submissionPost,result);
        assertEquals(result.hasErrors(),false);
    }

    @Test
    public void testInvalidDate() throws Exception {
        Date pastDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pastDate);
        calendar.add(Calendar.DATE, -1);
        pastDate = calendar.getTime();

        submissionPost.setTitle("invalid date");
        submissionPost.setText("description");
        submissionPost.setVotingCloses(pastDate);
        submissionPost.setAnswerCloses(pastDate);
        result = new BeanPropertyBindingResult(submissionPost,"submissionPost");
        validator.validate(submissionPost,result);
        assertEquals(result.hasFieldErrors("votingCloses"), true);
        assertEquals(result.hasFieldErrors("answerCloses"), true);
    }

    @Test
    public void testInvalidPost() throws Exception {
        submissionPost.setTitle("T");
        submissionPost.setText("Description");
        result = new BeanPropertyBindingResult(submissionPost,"submissionPost");
        validator.validate(submissionPost,result);
        assertEquals(result.hasFieldErrors("title"),true);
        assertEquals(result.hasFieldErrors("votingCloses"),true);
        assertEquals(result.hasFieldErrors("answerCloses"),true);
    }

    @Test
    public void testDuplicatePost() throws Exception {
        submissionPost.setTitle("T");
        submissionPostRepository.save(submissionPost);
        SubmissionPost newPost = new SubmissionPost();
        newPost.setTitle(submissionPost.getTitle());
        result = new BeanPropertyBindingResult(newPost,"newPost");
        validator.validate(newPost,result);
        assertEquals(result.hasFieldErrors("title"),true);
    }

}
