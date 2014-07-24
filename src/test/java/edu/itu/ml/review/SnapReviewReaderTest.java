package edu.itu.ml.review;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.itu.ml.core.Review;

public class SnapReviewReaderTest {
	
	SnapReviewReader snapReviewReader; 
	
	@Before
	public void setup() throws Exception {
		snapReviewReader = new SnapReviewReader();
	}

	@Test
	public void testOriginalFormat() throws IOException {
		Review review1 = snapReviewReader.getNext();
		Review review2 = snapReviewReader.getNext();
	}
}
