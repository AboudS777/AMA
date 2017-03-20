package ama.account;

import ama.post.CommentPost;
import ama.post.PostRepository;
import ama.post.SubmissionPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerNewUserAccount(User user){
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public SubmissionPost createSubmissionPost(User user, String title, String text) {
        return postRepository.save(new SubmissionPost(user, title, text));
    }

    public CommentPost addComment(User user, SubmissionPost post, String text) {
        return postRepository.save(new CommentPost(user, post, text));
    }
}
