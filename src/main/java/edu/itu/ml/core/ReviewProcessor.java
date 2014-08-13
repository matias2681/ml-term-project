package edu.itu.ml.core;

import java.util.Collections;
import java.util.List;

public class ReviewProcessor {
	
	public List<Review> processUsefulReviews(List<Review> reviews) {
		Collections.sort(reviews, new UpVoteReviewComparator());
		Review reviewWithHighestUpVote = reviews.get(0);
		final int highestUpVote = reviewWithHighestUpVote.getUpVote();
		for(Review review : reviews) {
			if (review.getUpVote() == highestUpVote) {
				review.markAsUseful();
			}
		}
		return reviews;
	}
	
}
