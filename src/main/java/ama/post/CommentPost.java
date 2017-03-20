package ama.post;

import ama.account.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by Stephane on 2017-03-19.
 */
@Entity
public class CommentPost extends Post {

    @ManyToOne
    private final Post context;

    public CommentPost() {this.context = null;}

    public CommentPost(User op, SubmissionPost context, String text) {
        super(op, text);
        this.context = context;
    }
}
