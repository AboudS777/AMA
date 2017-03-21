package ama.post;

import ama.account.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Stephane on 2017-03-19.
 */
@Entity
public class SubmissionPost extends Post {

    private String title;   //will need to validate title's upon form submission to ensure that there are no duplicate titles
    private Date votingCloses;
    private Date answerCloses;
    private ArrayList<User> usersWhoLiked = new ArrayList<User>();

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
