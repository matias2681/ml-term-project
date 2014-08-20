package edu.itu.ml.nlp;

/**
 * This class implement a the structure of a value for a sentiment.
 */
public class SentimentValue {

	private double positive;
	private double negative;
	private double objetive;

	/**
	 * Constructor
	 * 
	 * @param positive
	 *            the positive value
	 * @param negative
	 *            the negative value
	 */
	public SentimentValue(final double positive, final double negative) {
		this.positive = positive;
		this.negative = negative;
		this.objetive = 1 - (this.positive + this.negative);
	}

	/**
	 * Add the implicit sentiment with the parameter.
	 * 
	 * @param aSentiment
	 *            the {@link SentimentValue} to add
	 * @return the new {@link SentimentValue} with aSentiment value added.
	 */
	public SentimentValue add(SentimentValue aSentiment) {
		this.positive += aSentiment.positive;
		this.negative += aSentiment.negative;
		this.objetive = 1 - (this.positive + this.negative);
		return this;
	}

	/**
	 * Divide the implicit {@link SentimentValue}
	 * @param divValue the {@link SentimentValue} to divide the implicit
	 * @return The division of the implicit {@link SentimentValue} and divValue
	 */
	public SentimentValue div(double divValue) {
		this.positive /= divValue;
		this.negative /= divValue;
		this.objetive = 1 - (this.positive + this.negative);
		return this;
	}

	public String toString() {
		return String.format("+ %f, - %f, o %f", this.positive, this.negative,
				this.objetive);
	}

	/**
	 * @return The string representation for <code>CSV</code> format
	 */
	public String[] toCSV() {
		return new String[] { String.valueOf(this.positive),
				String.valueOf(this.negative), String.valueOf(this.objetive) };
	}

	public boolean equals(SentimentValue target) {
		return (this.positive == target.positive && this.negative == target.negative);
	}

	public boolean isEmpty() {
		return this.positive == 0d && this.negative == 0d;
	}
}
