package ama.post;

import ama.account.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Stephane on 2017-03-19.
 */
@Entity
public class SubmissionPost extends Post {

    private String title;
    private Date votingCloses;
    private Date answerCloses;

    @ManyToMany
    private Set<User> usersWhoLiked = new HashSet<>();

    public SubmissionPost() {}

    public SubmissionPost(User op, String title, String text) {
        super(op, text);
        this.title = title;
    }

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
