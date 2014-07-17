package edu.itu.ml.nlp;

public class SentimentValue {

	private double positive;
	private double negative;
	private double objetive;
	
	public SentimentValue(final double positive, final double negative, final double objetive) {
		this.positive = positive;
		this.negative = negative;
		this.objetive = objetive;
	}
	
	public SentimentValue add(SentimentValue aSentiment) {
		this.positive += aSentiment.positive;
		this.negative += aSentiment.negative;
		this.objetive += aSentiment.objetive;
		return this;
	}
	
}
