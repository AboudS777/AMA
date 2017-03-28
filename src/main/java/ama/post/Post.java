package ama.post;

import ama.account.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


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

    @OneToMany(mappedBy="context", cascade = CascadeType.PERSIST)
    private List<CommentPost> replies;

    public Post() {op = null;}

    public Post(User op, String text) {
        this.op = op;
        this.text = text;
    }

    public String getText() {return text;}

    public void setText(String text) {
        this.text = text;
    }

    public User getOp() {return this.op;}

    public void setOp(User op) {
        this.op = op;
    }

    public Long getId() { return id; }

    public List<CommentPost> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentPost> replies) {
        this.replies = replies;
    }

    public Collection<CommentPost> getSortedReplies() {
        List<CommentPost> replies = getReplies();
        replies.sort(new CommentPostComparator());
        return replies;
    }
}
