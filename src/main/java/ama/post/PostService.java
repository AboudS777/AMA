package ama.post;

import ama.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by stephanernst on 3/21/2017.
 */

@Service
public class PostService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmissionPostRepository submissionPostRepository;

    @Autowired
    private CommentPostRepository commentPostRepository;


}
