package ama.post;

import ama.account.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Stephane on 2017-03-19.
 */
@Entity
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected final User op;
    protected int upvotes;
    protected int downvotes;
    protected String text;

    @OneToMany(cascade = CascadeType.PERSIST)
    protected Collection<CommentPost> replies;

    public Post() {op = null;}

    public Post(User op, String text) {
        this.op = op;
        this.text = text;
    }

    public int getUpvotes() {
        return this.upvotes;
    }

    public int getDownvotes() {
        return this.downvotes;
    }

    public int getPoints() {
        return this.upvotes - this.downvotes;
    }

    public void upvote() {
        this.upvotes++;
    }

    public void downvote() {
        this.downvotes++;
    }

    public void edit(String text) {
        this.text = text;
    }
}
