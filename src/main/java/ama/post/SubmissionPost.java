package ama.post;

import ama.account.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Stephane on 2017-03-19.
 */
@Entity
public class SubmissionPost extends Post {

    private String title;
    private Date votingCloses;
    private Date answerCloses;

    @ManyToMany
    private Collection<User> usersWhoLiked;

    public SubmissionPost() {}

    public SubmissionPost(User op, String title, String text) {
        super(op, text);
        this.title = title;
        usersWhoLiked.add(op);
    }

    /* title field is probably better as final*/
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getUsersWhoLiked() {
        return usersWhoLiked.size();
    }

    public void likePost(User user) {
        usersWhoLiked.add(user);
    }
}
