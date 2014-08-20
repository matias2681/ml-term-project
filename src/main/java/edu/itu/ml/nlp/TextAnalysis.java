package edu.itu.ml.nlp;

import java.util.ArrayList;
import java.util.List;

import edu.itu.ml.core.Review;

/**
 * This class is in charge of all the analysis related to the reviews. It's a
 * standalone class to improve the performance. Here is calculated the
 * sentimental analysis of the review, number of sentences, tokenization and
 * other stuff related.
 * 
 */
public class TextAnalysis {

	private String text;
	private POSTagger tagger = new POSTagger();
	private SentenceDetectorML sentenceDetector = new SentenceDetectorML();
	private TokenizerML tokenizer = new TokenizerML();
	private String[] sentences;
	private SentimentDictionary dictionary = new SentimentDictionary();
	private Lemmatizer lemmatizer = new Lemmatizer();
	private static TextAnalysis instance = new TextAnalysis();

	private TextAnalysis() {
	}

	public static TextAnalysis getInstance() {
		return instance;
	}

	public void analyze(Review review) {
		this.text = review.getText();
	}

	/**
	 * @return extract the sentimental value of the review
	 */
	public SentimentValue getSentiment() {
		SentimentValue total = new SentimentValue(0, 0);
		double num = 0d;
		for (String sentence : sentences) {
			String[] sentenceSplit = this.convertSentenceTo(sentence);
			String[] tags = tagger.tag(sentenceSplit);
			for (int i = 0; i < sentenceSplit.length; i++) {
				String lemmatizedWord = lemmatizer.lemmatize(sentenceSplit[i]);
				SentimentValue value = dictionary.extract(lemmatizedWord,
						tags[i]);
				if (!value.isEmpty()) {
					total.add(value);
					num++;
				}
			}
		}
		return total.div(num);
	}

	private String[] convertSentenceTo(String sentence) {
		List<String> result = new ArrayList<String>();
		for (String word : tokenizer.tokenize(sentence)) {
			result.add(word);
		}
		return result.toArray(new String[result.size()]);
	}

	/**
	 * @return the number of sentences in the review.
	 */
	public long numSentences() {
		this.sentences = sentenceDetector.sentences(this.text);
		return this.sentences.length;
	}

	/**
	 * @return the number of words in the review.
	 */
	public long numWords() {
		long count = 0;
		for (String sentence : sentences) {
			count += tokenizer.tokenize(sentence).length;
		}
		return count;
	}

}
