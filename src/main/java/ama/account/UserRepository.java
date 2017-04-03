package ama.account;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    Set<User> findByFollowers(User user);

    @Override
    void delete(User user);
}
