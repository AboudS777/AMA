package ama.post;

import java.util.Comparator;

/**
 * Created by Stephane on 2017-03-29.
 */
public class SubmissionPostComparator implements Comparator<SubmissionPost>{

    @Override
    public int compare(SubmissionPost o1, SubmissionPost o2) {
        // -1 is less than, 0 is equal, 1 is greater than
        if (o1.getAmountOfLikes() > o2.getAmountOfLikes()) {
            return -1;
        } else if (o1.getAmountOfLikes() == o2.getAmountOfLikes()) {
            return 0;
        } else {
            return 1;
        }
    }
}
