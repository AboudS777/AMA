package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarrankanpathmanatha on 3/5/2017.
 */
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String firstname) {

        User user = userRepository.findByFirstname(firstname);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user found with name" + firstname);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword().toLowerCase(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,authorities);
    }

}
