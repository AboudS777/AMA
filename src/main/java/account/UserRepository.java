package account;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel ="registration",path = "registration")
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    @Override
    void delete(User user);
}
