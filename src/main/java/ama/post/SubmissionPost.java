package ama.post;

import ama.account.User;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date votingCloses;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date answerCloses;


    @ManyToMany
    private Set<User> usersWhoLiked = new HashSet<>();

    @ElementCollection
    private Set<String> tags = new HashSet<>();

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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public int getAmountOfLikes() {
        return usersWhoLiked.size();
    }

    public void likePost(User user) {
        usersWhoLiked.add(user);
    }
    public Date getVotingCloses() {
        return votingCloses;
    }

    public void setVotingCloses(Date votingCloses) {
        this.votingCloses = votingCloses;
    }

    public Date getAnswerCloses() {
        return answerCloses;
    }

    public void setAnswerCloses(Date answerCloses) {
        this.answerCloses = answerCloses;
    }
}
