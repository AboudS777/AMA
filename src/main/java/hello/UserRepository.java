package hello;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by sarrankanpathmanatha on 3/5/2017.
 */

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByFirstname(String firstname);
}
