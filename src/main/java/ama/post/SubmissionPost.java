package ama.post;

import ama.account.User;

import javax.persistence.Entity;

/**
 * Created by Stephane on 2017-03-19.
 */
@Entity
public class SubmissionPost extends Post {

    private String title;

    public SubmissionPost() {this.title = null;}

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

}
