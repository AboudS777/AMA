package ama.post;

import ama.account.User;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by stephanernst on 3/20/2017.
 */
@Transactional
public interface SubmissionPostRepository extends PostRepository<SubmissionPost> {

    SubmissionPost findByTitle(String title);

    List<SubmissionPost> findAllByUsersWhoLiked(User user);

    List<SubmissionPost> findByTags(String Tag);
}
