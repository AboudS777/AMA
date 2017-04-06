package ama.post;

import ama.account.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Stephane on 2017-03-19.
 */
@Entity
public class CommentPost extends Post {

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<User> upvoters = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<User> downvoters = new HashSet<>();

    @ManyToOne
    private Post context;

    public CommentPost() {}

    public CommentPost(User op, Post context, String text) {
        super(op, text);
        this.context = context;
    }

    public void setContext(Post post) {
        this.context = post;
    }

    public Post getContext() {
        return this.context;
    }

    public SubmissionPost getSubmissionPost() {
        if(this.context instanceof SubmissionPost) {
            return (SubmissionPost) context;
        } else {
            return ((CommentPost)(this.context)).getSubmissionPost();
        }
    }

    public int getUpvotes() {
        return this.upvoters.size();
    }

    public int getDownvotes() {
        return this.downvoters.size();
    }

    public void upvote(User user) {
        upvoters.add(user);
        downvoters.remove(user);
    }

    public void downvote(User user) {
        downvoters.add(user);
        upvoters.remove(user);
    }

    public int getPoints() {
        return this.getUpvotes() - this.getDownvotes();
    }
}
