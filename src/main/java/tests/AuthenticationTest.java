package tests;
import account.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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

/**
 * Created by sarrankanpathmanatha on 3/9/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {

    @LocalServerPort
    private int port;

    private URL base;


    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {

        this.base = new URL("http://localhost:" + port + "/");

    }


    @Test
    public void registerUser() throws Exception {
        MultiValueMap<String,Object> user = new LinkedMultiValueMap<>();
        user.add("username","sarran");
        user.add("password","s");
        ResponseEntity<String> response = template.postForEntity(base.toString()+"/registration",user,String.class);
        assertThat(response.getStatusCode(),equalTo(HttpStatus.OK));
    }

    @Test
    public void loginUser() throws Exception {
        MultiValueMap<String,Object> user = new LinkedMultiValueMap<>();
        user.add("username","sarran");
        user.add("password","s");
        ResponseEntity<String> response = template.postForEntity(base.toString()+"/login",user,String.class);
        assertThat(response.getStatusCode(),equalTo(HttpStatus.FOUND));
    }

    @Test
    public void viewAccount() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString()+"/account",String.class);
        assertThat(response.getStatusCode(),equalTo(HttpStatus.OK));
    }





}
