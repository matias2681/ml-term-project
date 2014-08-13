package edu.itu.ml.exporter;

import java.util.ArrayList;
import java.util.List;

import edu.itu.ml.core.Review;
import edu.itu.ml.core.ReviewProcessor;
import edu.itu.ml.review.ReviewReader;
import edu.itu.ml.review.SnapReviewReader;

public class Parser {
	
	private CSVExporter exporter = new CSVExporter();
	private ReviewReader reviewReader = new SnapReviewReader();
	private ReviewProcessor reviewProcessor = new ReviewProcessor();
	
	public void run() {
		List<Review> reviews = new ArrayList<Review>();
		String currentProduct = "";
		
		while(reviewReader.hasNext()) {
			Review review = reviewReader.getNext();
			if (currentProduct.isEmpty()) {
				currentProduct = review.getProductId();
			}
			if (!review.compareProduct(currentProduct)) {
				reviews = reviewProcessor.processUsefulReviews(reviews);
				exporter.write(reviews);
				currentProduct = "";
				reviews.clear();
			}
			reviews.add(review);
		}
	}
	
}
