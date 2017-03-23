package ama.post;

import ama.account.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Stephane on 2017-03-19.
 */
@Repository
public interface PostRepository<T extends Post> extends CrudRepository<T, Long> {
    List<T> findByOp(User user);
    List<T> findAll();
    List<T> findById(long id);
}
