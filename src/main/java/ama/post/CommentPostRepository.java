package ama.post;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by stephanernst on 3/20/2017.
 */
@Transactional
public interface CommentPostRepository extends PostRepository<CommentPost> {

    List<CommentPost> findByContext(Post context);
}
