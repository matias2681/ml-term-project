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
		//this.objetive += aSentiment.objetive;
		this.objetive = 1 - (this.positive + this.negative);
		return this;
	}

	public SentimentValue div(double divValue) {
		this.positive /= divValue;
		this.negative /= divValue;
		//this.objetive /= divValue;
		this.objetive = 1 - (this.positive + this.negative);
		return this;
	}

	public String tostring() {
		return String.format("+ %f, - %f, o %f", this.positive, this.negative, this.objetive);
	}
	
	public boolean equals(SentimentValue target) {
		return (this.positive == target.positive && this.negative == target.negative);
	}
}
