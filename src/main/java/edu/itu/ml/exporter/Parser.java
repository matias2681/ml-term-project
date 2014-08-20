package edu.itu.ml.exporter;

import java.util.ArrayList;
import java.util.List;

import edu.itu.ml.core.Review;
import edu.itu.ml.core.ReviewProcessor;
import edu.itu.ml.review.ReviewReader;
import edu.itu.ml.review.SnapReviewReader;

/**
 * This class parse all the reviews to process.
 *
 */
public class Parser {
	
	private CSVExporter exporter = new CSVExporter();
	private ReviewReader reviewReader = new SnapReviewReader();
	private ReviewProcessor reviewProcessor = new ReviewProcessor();
	private int limit = Integer.MAX_VALUE;
	
	public Parser() {
	}
	
	public Parser(int limit) {
		this.limit = limit;
	}
	
	public void run() {
		List<Review> reviews = new ArrayList<Review>();
		String currentProduct = "";
		int count = 0;
		while(reviewReader.hasNext() && count<limit) {
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
			count++;
		}
	}
	
	public static void main(String[] args) {
		Parser parser;
		if (args.length == 0) {
			parser = new Parser();
		} else {
			parser = new Parser(Integer.valueOf(args[0]));
		}
		parser.run();
	}
	
}
