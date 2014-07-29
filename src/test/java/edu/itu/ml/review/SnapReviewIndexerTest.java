package edu.itu.ml.review;

import java.util.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.itu.ml.core.Review;

public class SnapReviewIndexerTest {
	
	ReviewIndexer reviewIndexer;
	boolean debug = true;
	
	@Before
	public void setup() throws Exception {
		//reviewIndexer = new SnapReviewIndexer();
		reviewIndexer = new SnapReviewIndexer(debug);
		this.reviewIndexer.index();
	}

	@Test
	public void testIndex() {
		// Please remind index will remove all parsed data and re-run index.
		// For large file, kept doing this will be time wasting.
		Assert.assertTrue(this.reviewIndexer.index());
	}

	@Test
	public void testGetProductIdList() {
		Map<String, Integer> pidList = this.reviewIndexer.getProductIdList();
		Assert.assertTrue((pidList.size() > 0));
	}

	@Test
	public void testQueryText() {
		ArrayList<Review> reviewList = this.reviewIndexer.query("text", "goo?");
		Assert.assertTrue((reviewList.size() > 0));
	}

	@Test
	public void testQueryProductId() {
		ArrayList<Review> reviewList = this.reviewIndexer.query("productId", "B0007XKGJ0");
		Assert.assertTrue((reviewList.size() > 0));
	}

	@Test
	public void testQueryCustomerId() {
		ArrayList<Review> reviewList = this.reviewIndexer.query("userId", "A32DXU3I6SYA3Z");
		Assert.assertTrue((reviewList.size() > 0));
	}
	
	@Test
	public void testQueryId() {
		ArrayList<Review> reviewList = this.reviewIndexer.query("id", "1");
		Assert.assertTrue((reviewList.size() > 0));
	}
}
