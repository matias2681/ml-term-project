package edu.itu.ml.review;

import java.io.File;
import java.io.IOException;
import java.net.URL;
//import java.util.regex.Pattern;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import edu.itu.ml.Constants;
import edu.itu.ml.core.Review;

public class SnapReviewReader implements ReviewReader {

	private LineIterator lineIterator; 

	public SnapReviewReader() {
		URL snapFilePath = this.getClass().getClassLoader()
				.getResource(Constants.FILE_TO_SNAP_REVIEW_DATA);
		try {
			lineIterator = FileUtils.lineIterator(new File(snapFilePath.getPath()), Constants.ENCODING);
		} catch (IOException e) {
			throw new RuntimeException(Constants.ERROR_WHILE_OPEN_REVIEW_FILE, e);
		}
	}

	public Review getNext() {
		String productId = null;
		String producTitle = null;
		String productPrice = null;
		String userId = null;
		String profileName = null;
		String helpfulness = null;
		String score = null;
		String time = null;
		String summary = null;
		String text = null;
		String line = null;
		Review review = null;

		while ((line = this.lineIterator.nextLine()) != null) {
			if (!line.equals("")) {
				if (null == productId && line.matches("product/productId: (.*)")) {
					productId = line.substring("product/productId: ".length());
				} else if (null == producTitle && line.matches("product/title: (.*)")) {
					producTitle = line.substring("product/title: ".length());
				} else if (null == productPrice && line.matches("product/price: (.*)")) {
					productPrice = line.substring("product/price: ".length());
				} else if (null == userId && line.matches("review/userId: (.*)")) {
					userId = line.substring("review/UserId: ".length());
				} else if (null == profileName && line.matches("review/profileName: (.*)")) {
					profileName = line.substring("review/profileName: "
							.length());
				} else if (null == helpfulness && line.matches("review/helpfulness: (.*)")) {
					helpfulness = line.substring("review/helpfulness: "
							.length());
				} else if (null == score && line.matches("review/score: (.*)")) {
					score = line.substring("review/score: ".length());
				} else if (null == time && line.matches("review/time: (.*)")) {
					time = line.substring("review/time: ".length());
				} else if (null == summary && line.matches("review/summary: (.*)")) {
					summary = line.substring("review/summary: ".length());
				} else if (line.matches("review/text: (.*)")) {
					text = line.substring("review/text: ".length());
				} else {
					return null; // throw new Error("review data not complete");
				}
			} else {
				break;
			}
		}
		review = new Review.ReviewBuilder().productId(productId)
				.producTitle(producTitle).productPrice(productPrice)
				.userId(userId).profileName(profileName)
				.helpfulness(helpfulness).score(score).time(time)
				.summary(summary).text(text).build();
		return review;
	}

	public boolean hasNext() {
		boolean value =  lineIterator.hasNext();
		if (!value) {
			lineIterator.close();
		}
		return value;
	}

}
