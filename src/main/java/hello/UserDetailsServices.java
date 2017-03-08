package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by stephanernst on 3/7/2017.
 * Provides Services for User
 */
@Service
public class UserDetailsServices implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserDetailsServices() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
