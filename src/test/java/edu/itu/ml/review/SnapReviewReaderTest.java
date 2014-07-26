package edu.itu.ml.review;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.itu.ml.core.Review;

public class SnapReviewReaderTest {
	
	ReviewReader reviewReader; 
	
	@Before
	public void setup() throws Exception {
		reviewReader = new SnapReviewReader();
	}

	@Test
	public void testReadReviewsWithOriginalFormat() {
		Review review;
		int numberReviewsRead = 0;
		while(reviewReader.hasNext()) {
			numberReviewsRead++;
			review = reviewReader.getNext();
			Assert.assertNotNull(review);
		}
		Assert.assertEquals(237, numberReviewsRead);
	}
}
