package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User registerNewUserAccount(User account){
        User user = new User();
        user.setUsername(account.getUsername());
        user.setPassword(account.getPassword());
        repository.save(user);
        return user;
    }
}
