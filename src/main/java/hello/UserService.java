package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by sarrankanpathmanatha on 3/5/2017.
 */
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public User registerNewUserAccount(User account){
        User user = new User();
        user.setFirstName(account.getFirstName());
        user.setLastName(account.getLastName());
        user.setPassword(account.getPassword());
        return repository.save(user);
    }
}
