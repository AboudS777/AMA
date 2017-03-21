package ama.post;

import ama.account.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Stephane on 2017-03-19.
 */
@Entity
@Inheritance
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private final User op;
    private int upvotes;
    private int downvotes;
    private String text;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<CommentPost> replies;

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

    public String getText() {return text;}

    public void setText(String text) {
        this.text = text;
    }

    public void upvote() {
        this.upvotes++;
    }

    public void downvote() {
        this.downvotes++;
    }
}
