
import ama.Application;
import ama.account.User;
import ama.account.UserService;
import ama.post.CommentPost;
import ama.post.CommentPostRepository;
import ama.post.SubmissionPost;
import ama.post.SubmissionPostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by stephanernst on 3/22/2017.
 */

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class CommentVoteTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CommentPostRepository commentPostRepository;

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    @Autowired
    private UserService userService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        User user = new User("sarran", "theman");
        if(userService.loadUserByUsername(user.getUsername()) == null) {
            userService.registerNewUserAccount(user);
        }

        CommentPost comment = new CommentPost();
        comment.setText("This is a comment.");
        commentPostRepository.save(comment);
    }

    @Test
    public void testUpvoteValidComment() throws Exception {
        mvc
                .perform(get("/comments/upvote")
                        .param("id", commentPostRepository.findAll().get(0).getId().toString())
                        .with(csrf())
                        .with(user("sarran")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:null"));

        assert(commentPostRepository.findAll().get(0).getPoints() == 1);
    }

    @Test
    public void testUpvoteInvalidComment() throws Exception {
        mvc
                .perform(get("/comments/upvote")
                        .param("id", "100")
                        .with(csrf())
                        .with(user("sarran")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testDownvoteValidComment() throws Exception {
        mvc
                .perform(get("/comments/downvote")
                        .param("id", commentPostRepository.findAll().get(0).getId().toString())
                        .with(csrf())
                        .with(user("sarran")))
                .andExpect(status().is3xxRedirection());

        assert(commentPostRepository.findAll().get(0).getPoints() == -1);
    }

    @Test
    public void testDownvoteInvalidComment() throws Exception {
        mvc
                .perform(get("/comments/downvote")
                        .param("id", "10")
                        .with(csrf())
                        .with(user("sarran")))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testUpvoteCommentAfterVotingCloses() throws Exception {
        Date pastDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pastDate);
        calendar.add(Calendar.DATE, -1);

        SubmissionPost post = new SubmissionPost();
        post.setTitle("upvotecommentaftervote");
        post.setText("aftervotingcloses");
        post.setVotingCloses(pastDate);
        post.setAnswerCloses(pastDate);
        submissionPostRepository.save(post);

        CommentPost comment = new CommentPost();
        comment.setText("This is a comment.");
        comment.setContext(post);
        comment = commentPostRepository.save(comment);

        int commentPostRepositoryPoints = comment.getPoints();
        mvc
                .perform(get("/comments/upvote")
                        .param("id", comment.getId().toString())
                        .with(csrf())
                        .with(user("sarran")))
                .andExpect(status().is3xxRedirection());
        //ensure that comment hasn't been upvoted
        assert(commentPostRepositoryPoints == commentPostRepository.findById(comment.getId()).getPoints());
    }
}
