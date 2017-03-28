package ama.post;

import ama.account.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Stephane on 2017-03-19.
 */
@Entity
public class CommentPost extends Post {

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Collection<User> upvoters;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Collection<User> downvoters;

    @ManyToOne
    private Post context;

    public CommentPost() {
        this.upvoters = new ArrayList<>();
        this.downvoters = new ArrayList<>();
    }

    public CommentPost(User op, Post context, String text) {
        super(op, text);
        this.context = context;
        this.upvoters = new ArrayList<>();
        this.downvoters = new ArrayList<>();
    }

    public void setContext(Post post) {
        this.context = post;
    }

    public Post getContext() {
        return this.context;
    }

    public int getUpvotes() {
        return this.upvoters.size();
    }

    public int getDownvotes() {
        return this.downvoters.size();
    }

    public void upvote(User user) {
        if(!upvoters.contains(user)) {
            upvoters.add(user);
            downvoters.remove(user);
        }
    }

    public void downvote(User user) {
        if(!downvoters.contains(user)) {
            downvoters.add(user);
            upvoters.remove(user);
        }
    }

    public int getPoints() {
        return this.getUpvotes() - this.getDownvotes();
    }
}
