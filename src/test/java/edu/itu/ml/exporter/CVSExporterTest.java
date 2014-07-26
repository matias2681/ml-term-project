package edu.itu.ml.exporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.itu.ml.core.Review;
import edu.itu.ml.core.ReviewBaseTest;
import edu.itu.ml.review.ReviewReader;
import edu.itu.ml.review.SnapReviewReader;

public class CVSExporterTest extends ReviewBaseTest {

	private CSVExporter exporter;
	private ReviewReader reviewReader;
	
	@Before
	public void setUp() throws Exception {
		exporter = new CSVExporter();
		reviewReader = new SnapReviewReader();
	}

	@Test
	public void testExportSimpleReview() {
		Review review = new Review.ReviewBuilder().productId(this.productId)
				.producTitle(this.producTitle).productPrice(this.productPrice)
				.userId(this.userId).profileName(this.profileName)
				.helpfulness(this.helpfulness).score(this.score).time(this.time)
				.summary(this.summary).text(this.text).build();
		exporter.write(review.extractFeatures());
	}
	
	@Test
	public void testExportWholeReviewFileDev() {
		Review review;
		while(reviewReader.hasNext()) {
			review = reviewReader.getNext();
			Assert.assertNotNull(review);
			exporter.write(review.extractFeatures());
		}
	}

}
