package tests;

import ama.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import java.net.URL;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubmissionPostTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    private MultiValueMap<String,Object> user;
    private MultiValueMap<String,Object> post;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        user = new LinkedMultiValueMap<>();
        post = new LinkedMultiValueMap<>();
        user.add("username","sarran");
        user.add("password","theman");
        post.add("title","Hello World!");
        post.add("text","Hi this is sarrankan! AMA!");
        template.postForEntity(base.toString()+"/registration",user,String.class);
        template.postForEntity(base.toString()+"/login",user,String.class);
    }

    @Test
    public void submitPost() throws Exception {
        ResponseEntity<String> response = template.postForEntity(base.toString()+"/create_submission",post,String.class);
        assertThat(response.getStatusCode(),equalTo(HttpStatus.FOUND));
    }

    @Test
    public void viewSubmittedPost() throws Exception{
        ResponseEntity<String> response = template.getForEntity(base.toString()+ "/posts/Hello%20World!" ,String.class);
        assertThat(response.getStatusCode(),equalTo(HttpStatus.OK));
    }
}
