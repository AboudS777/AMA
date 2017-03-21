package ama.post;

import ama.account.User;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Stephane on 2017-03-19.
 */
@Entity
public class SubmissionPost extends Post {

    private String title;
    private boolean open;
    private Date votingCloses;
    private Date answerCloses;
    private ArrayList<User> usersWhoLiked = new ArrayList<User>();

    public SubmissionPost() {this.title = null;}

    public SubmissionPost(User op, String title, String text, boolean open) {
        super(op, text);
        this.title = title;
        this.open = open;
        usersWhoLiked.add(op);
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
