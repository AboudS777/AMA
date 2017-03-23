package tests;

import ama.Application;
import ama.post.CommentPost;
import ama.post.Post;
import ama.post.SubmissionPost;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URL;
import java.net.URLEncoder;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by sarrankanpathmanatha on 3/22/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentPostTests {
    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    private MultiValueMap<String,Object> user;
    private SubmissionPost post;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        user = new LinkedMultiValueMap<>();
        post = new SubmissionPost();
        user.add("username","sarran");
        user.add("password","theman");
        post.setTitle("HelloWorld");
        post.setText("Hi this is sarrankan! AMA!");
        template.postForEntity(base.toString()+"/registration",user,String.class);
        template.postForEntity(base.toString()+"/login",user,String.class);
    }

    @Test
    public void addComment() throws Exception {
        CommentPost comment = new CommentPost();
        comment.setContext(post);
        comment.setText("This is a test comment");
        ResponseEntity<String> response = template.postForEntity(base.toString()+"/posts/" + URLEncoder.encode(post.toString(), "UTF-8").toString(), comment, String.class);
        assertThat(response.getStatusCode(),equalTo(HttpStatus.FOUND));
    }
}
