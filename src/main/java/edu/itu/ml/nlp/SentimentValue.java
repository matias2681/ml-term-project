package edu.itu.ml.nlp;

public class SentimentValue {

	private double positive;
	private double negative;
	private double objetive;
	
	public SentimentValue(final double positive, final double negative) {
		this.positive = positive;
		this.negative = negative;
		this.objetive = 1 - (this.positive + this.negative);
	}
	
	public SentimentValue add(SentimentValue aSentiment) {
		this.positive += aSentiment.positive;
		this.negative += aSentiment.negative;
		this.objetive += aSentiment.objetive;
		return this;
	}

	public SentimentValue div(double divValue) {
		this.positive /= divValue;
		this.negative /= divValue;
		this.objetive /= divValue;
		return this;
	}
	
}
