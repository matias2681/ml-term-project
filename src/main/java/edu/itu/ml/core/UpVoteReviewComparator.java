package edu.itu.ml.core;

import java.util.Comparator;

/**
 * Comparator for the {@link Review} that compare based on the upvote property
 * 
 */
public class UpVoteReviewComparator implements Comparator<Review> {

	@Override
	public int compare(Review r1, Review r2) {
		return r2.getUpVote() - r1.getUpVote();
	}

}
