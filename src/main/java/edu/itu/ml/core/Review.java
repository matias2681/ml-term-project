package edu.itu.ml.core;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Instant;

import edu.itu.ml.Constants;
import edu.itu.ml.nlp.TextAnalysis;

public class Review {
	
	private final String productId;
	private final String producTitle;
	private final double productPrice;
	private final String userId;
	private final String profileName;
	private final double helpfulness;
	private final double score;
	private final DateTime time;
	private final String summary;
	private final String text;
	private final int upvotes;
	private boolean useful;

	public static class ReviewBuilder {
		private String productId;
		private String producTitle;
		private double productPrice;
		private String userId;
		private String profileName;
		private double helpfulness;
		private double score;
		private DateTime time;
		private String summary;
		private String text;
		private int upvotes;
		
		public ReviewBuilder productId(String aProductId) {
			this.productId = aProductId;
			return this;
		}
		
		public ReviewBuilder producTitle(String aProductTitle) {
			this.producTitle = aProductTitle;
			return this;
		}
		
		public ReviewBuilder productPrice(String price) {
			if (Constants.UNKNOWN_PRICE.equals(price)) {
				this.productPrice = Double.parseDouble("0");
			} else {
				this.productPrice = Double.parseDouble(price);
			}
			return this;
		}
		
		public ReviewBuilder userId(String aUserId) {
			this.userId = aUserId;
			return this;
		}
		
		public ReviewBuilder profileName(String aProfileName) {
			this.profileName = aProfileName;
			return this;
		}
		
		public ReviewBuilder helpfulness(String helpfulnessValue) {
			String[] nums = helpfulnessValue.split("/");
			this.upvotes = Integer.parseInt(nums[0]);
			if (nums[1]!="0") {
				this.helpfulness = Double.parseDouble(nums[0])/Double.parseDouble(nums[1]);
			} else {
				this.helpfulness = 0d;
			}
			return this;
		}
		
		public ReviewBuilder score(String scoreValue) {
			this.score = Double.parseDouble(scoreValue);
			return this;
		}
		
		public ReviewBuilder time(String time) {
			this.time = new DateTime(Long.parseLong(time) * 1000L);
			return this;
		}
		
		public ReviewBuilder summary(String aSummary) {
			this.summary = aSummary;
			return this;
		}
		
		public ReviewBuilder text(String aText) {
			this.text = aText;
			return this;
		}
		
		public Review build() {
			return new Review(this);
		}
	}
	
	public Review(ReviewBuilder reviewBuilder) {
		this.productId = reviewBuilder.productId;
		this.producTitle = reviewBuilder.producTitle;
		this.productPrice = reviewBuilder.productPrice;
		this.userId = reviewBuilder.userId;
		this.profileName = reviewBuilder.profileName;
		this.helpfulness = reviewBuilder.helpfulness;
		this.score = reviewBuilder.score;
		this.time = reviewBuilder.time;
		this.summary = reviewBuilder.summary;
		this.text = reviewBuilder.text;
		this.upvotes = reviewBuilder.upvotes;
		this.useful = false;
	}
	
	public FeatureItem extractFeatures() {
		int days = Days.daysBetween(this.time, Instant.now()).getDays();
		TextAnalysis textAnalysis = TextAnalysis.getInstance();
		textAnalysis.analyze(this);
		
		FeatureItem item = new FeatureItem.FeatureItemBuilder().useful(useful)
				.age(days)
				.normUserRating(this.score)
				.numSentences(textAnalysis.numSentences())
				.numWords(textAnalysis.numWords())
				.numSentimental(textAnalysis.getSentiment())
				.build();
		return item;
	}
	
	

	public String getProductId(){
		return this.productId;
	}

	public Double getProductPrice() {
	  return this.productPrice;
	}
	
	public String getUserId() {
	  return this.userId;
	}
	
	public String getProfileName() {
	  return this.profileName;
	}
	
	public Double getHelpfulness() {
	  return this.helpfulness;
	}
	
	public Double getScore() {
	  return this.score;
	}
	
	public DateTime getTime() {
	  return this.time;
	}
	
	public String getText() {
		return this.text;
	}

	public String getSummary() {
		return this.summary;
	}

	public boolean compareProduct(String currentProduct) {
		return this.productId.equals(currentProduct);
	}

	public int getUpVote() {
		return this.upvotes;
	}

	public void markAsUseful() {
		this.useful = true;
	}
	
	
}
