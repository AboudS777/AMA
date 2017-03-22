package ama.post;

import javax.transaction.Transactional;

/**
 * Created by stephanernst on 3/20/2017.
 */
@Transactional
public interface SubmissionPostRepository extends PostRepository<SubmissionPost> {
    SubmissionPost findByTitle(String title);
}
