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
	private double wordsPerSentence;

	public FeatureItem(FeatureItemBuilder featureItemBuilder) {
		this.age = featureItemBuilder.age;
		this.normUserRating = featureItemBuilder.normUserRating
				/ Constants.MAX_NUM_STARS;
		this.numSentences = featureItemBuilder.numSentences;
		this.numWords = featureItemBuilder.numWords;
		this.numSentimental = featureItemBuilder.numSentimental;
		this.sentimentRating = featureItemBuilder.numSentimental
				.div(this.normUserRating);
		this.density = featureItemBuilder.numSentimental.div(this.numWords);
		this.wordsPerSentence = Double.valueOf(featureItemBuilder.numWords)
				/ Double.valueOf(featureItemBuilder.numSentences);
	}

	public static class FeatureItemBuilder {
		private long age;
		private double normUserRating;
		private long numSentences;
		private long numWords;
		private SentimentValue numSentimental;

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

		public FeatureItem build() {
			return new FeatureItem(this);
		}
	}

	@Override
	public String toString() {
		return "FeatureItem [age=" + age + ", normUserRating=" + normUserRating
				+ ", numSentences=" + numSentences + ", numWords=" + numWords
				+ ", numSentimental=" + numSentimental + ", sentimentRating="
				+ sentimentRating + ", density=" + density
				+ ", wordsPerSentence=" + wordsPerSentence + "]";
	}

	public String[] toCSV() {
		String[] numSentimentalValues = numSentimental.toCSV();
		String[] sentimentRatingValues = sentimentRating.toCSV();
		String[] densityValues = density.toCSV();
		return new String[] { String.valueOf(age),
				String.valueOf(normUserRating), String.valueOf(numSentences),
				String.valueOf(numWords),
				numSentimentalValues[Constants.POSITIVE],
				numSentimentalValues[Constants.NEGATIVE],
				numSentimentalValues[Constants.OBJECTIVE],
				sentimentRatingValues[Constants.POSITIVE],
				sentimentRatingValues[Constants.NEGATIVE],
				sentimentRatingValues[Constants.OBJECTIVE],
				densityValues[Constants.POSITIVE],
				densityValues[Constants.NEGATIVE],
				densityValues[Constants.OBJECTIVE],
				String.valueOf(wordsPerSentence) };
	}

	public static String[] columns() {
		return new String[] { "age", "normUserRating", "numSentences",
				"numWords", "numSentimental+", "numSentimental-",
				"numSentimentalo", "sentimentRating+", "sentimentRating-",
				"sentimentRatingo", "density+", "density-", "densityo",
				"wordsPerSentence" };
	}

}
