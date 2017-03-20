package ama.post;

import ama.account.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Stephane on 2017-03-19.
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByOp(User user);
}
