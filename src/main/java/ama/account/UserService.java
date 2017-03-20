package ama.account;

import ama.post.*;
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
    private SubmissionPostRepository submissionPostRepository;

    @Autowired
    private CommentPostRepository commentPostRepository;

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
        return submissionPostRepository.save(new SubmissionPost(user, title, text));
    }
/*
    public CommentPost addComment(User user, Post post, String text) {
        return commentPostRepository.save(new CommentPost(user, post, text));
    }*/
}
