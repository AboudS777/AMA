package account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * Created by Stephane on 2017-03-07.
 */

@Component
public class Validator {

    @Autowired
    private UserService userService;

    public void validate(User user, BindingResult result) {
        if (user.getUsername().length() < 2 || user.getUsername().length() > 20) {
            result.rejectValue("username", "Size.user.username");
        }
        if (userService.loadUserByUsername(user.getUsername()) != null) {
            result.rejectValue("username", "Duplicate.user.username");
        }
    }
}
