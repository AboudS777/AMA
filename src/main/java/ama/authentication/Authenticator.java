package ama.authentication;

import ama.account.User;
import ama.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Stephane on 2017-03-29.
 */

@Service
public class Authenticator {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String username = auth.getName();
            return userRepository.findByUsername(username);
        } else {
            return null;
        }
    }
}
