package edu.itu.ml.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReviewTest extends ReviewBaseTest{

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateSimpleReview() {
		Review review = new Review.ReviewBuilder().productId(this.productId)
				.producTitle(this.producTitle).productPrice(this.productPrice)
				.userId(this.userId).profileName(this.profileName)
				.helpfulness(this.helpfulness).score(this.score).time(this.time)
				.summary(this.summary).text(this.text).build();
		Assert.assertNotNull(review);
	}
	
	@Test
	public void testExtractFeatureReview() {
		Review review = new Review.ReviewBuilder().productId(this.productId)
				.producTitle(this.producTitle).productPrice(this.productPrice)
				.userId(this.userId).profileName(this.profileName)
				.helpfulness(this.helpfulness).score(this.score).time(this.time)
				.summary(this.summary).text(this.text).build();
		System.out.println(review.extractFeatures());
	}

}
