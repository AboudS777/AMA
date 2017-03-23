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

    @ManyToOne
    private User op;

    private String text;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<CommentPost> replies;

    public Post() {op = null;}

    public Post(User op, String text) {
        this.op = op;
        this.text = text;
    }

    public String getText() {return text;}

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {return op;}

    public void setUser(User user) {
        this.op = user;
    }

    public Long getId() { return id; }
}
