package account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class Validator {

    @Autowired
    private UserService userService;

    public void validate(User user, BindingResult result) {
        if (user.getUsername().length() < 2 || user.getUsername().length() > 20) {
            result.rejectValue("username", "Size.user.username", "Username must be between 2 and 20 characters.");
        }
        if (user.getPassword().length() < 2 || user.getPassword().length() > 20) {
            result.rejectValue("password", "Size.user.password", "Password must be between 2 and 20 characters.");
        }
        if (userService.loadUserByUsername(user.getUsername()) != null) {
            result.rejectValue("username", "Duplicate.user.username", "Username is already taken.");
        }
    }
}