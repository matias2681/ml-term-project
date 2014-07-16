package edu.itu.ml.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReviewTest {

	private final String productId = "B000HDFH6W";
	private final String producTitle = "Digital Image Flow DGK Color Tools Premium White Balance Card Set with Premium Lanyard Set of Three Cards";
	private final String productPrice = "23.98";
	private final String userId = "A2YEZ8Z3BW2SKG";
	private final String profileName = "eric melzer";
	private final String helpfulness = "8/11";
	private final String score = "5.0";
	private final String time = "1191542400";
	private final String summary = "Works great - simple yet effective.";
	private final String text = "I got this because I wanted a good quality gray card that "
			+ "wasn't made of paper and would last a while. I don't really need the white"
			+ " or black cards, or the deluxe lanyard (it really is nice quality though), "
			+ "and i basically just threw the card in my bag and left the rest at home."
			+ " Kind of pricey for a piece of plastic, but if you are in the field "
			+ "shooting in JPG mode and need to do a custom white balance, it's worth it"
			+ " to me, even if it fixes just one shot. The card works great. I've used it"
			+ " several times already and it is a simple but effective way to adjust white"
			+ " balance in strange lighting situations.";
	
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

}
