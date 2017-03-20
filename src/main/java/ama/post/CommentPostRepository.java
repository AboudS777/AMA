package ama.post;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by stephanernst on 3/20/2017.
 */
@Transactional
public interface CommentPostRepository extends PostRepository {
}
