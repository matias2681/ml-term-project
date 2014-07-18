package edu.itu.ml.core;

import edu.itu.ml.Constants;
import edu.itu.ml.nlp.SentimentValue;

public class FeatureItem {


	private long age;
	private double normUserRating;
	private long numSentences;
	private long numWords;
	private SentimentValue numSentimental;
	private SentimentValue sentimentRating;
	private SentimentValue density;
	private long wordsPerSentence;

	public FeatureItem(FeatureItemBuilder featureItemBuilder) {
		this.age = featureItemBuilder.age;
		this.normUserRating = featureItemBuilder.normUserRating/Constants.MAX_NUM_STARS;
		this.numSentences = featureItemBuilder.numSentences;
		this.numWords = featureItemBuilder.numWords;
		this.numSentimental = featureItemBuilder.numSentimental;
		this.sentimentRating = featureItemBuilder.sentimentRating.div(this.normUserRating);
		this.density = featureItemBuilder.density.div(this.numWords);
		this.wordsPerSentence = featureItemBuilder.wordsPerSentence;
	}
	
	public static class FeatureItemBuilder {
		private long age;
		private double normUserRating;
		private long numSentences;
		private long numWords;
		private SentimentValue numSentimental;
		private SentimentValue sentimentRating;
		private SentimentValue density;
		private long wordsPerSentence;
		
		public FeatureItemBuilder age(long age) {
			this.age = age;
			return this;
		}
		
		public FeatureItemBuilder normUserRating(double normUserRating) {
			this.normUserRating = normUserRating;
			return this;
		}
		
		public FeatureItemBuilder numSentences(long numSentences) {
			this.numSentences = numSentences;
			return this;
		}
		
		public FeatureItemBuilder numWords(long numWords) {
			this.numWords = numWords;
			return this;
		}
		
		public FeatureItemBuilder numSentimental(SentimentValue numSentimental) {
			this.numSentimental = numSentimental;
			return this;
		}
		
		public FeatureItemBuilder wordsPerSentence(long wordsPerSentence) {
			this.wordsPerSentence = wordsPerSentence;
			return this;
		}
		
		public FeatureItem build() {
			return new FeatureItem(this);
		}
	}
	
}
