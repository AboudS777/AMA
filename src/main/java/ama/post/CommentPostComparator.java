package ama.post;

import java.util.Comparator;

public class CommentPostComparator implements Comparator<CommentPost> {

    @Override
    public int compare(CommentPost o1, CommentPost o2) {
        // -1 is less than, 0 is equal, 1 is greater than
        if (o1.getPoints() > o2.getPoints()) {
            return -1;
        } else if (o1.getPoints() == o2.getPoints()) {
            return 0;
        } else {
            return 1;
        }
    }
}
