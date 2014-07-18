package edu.itu.ml.nlp;

import edu.itu.ml.core.Review;

public class TextAnalysis {

	private String text;
	private String summary;
	private POSTagger tagger = new POSTagger();
	private SentenceDetectorML sentenceDetector = new SentenceDetectorML();
	private TokenizerML tokenizer = new TokenizerML();
	private String[] sentences;
	private SentimentDictionary dictionary = new SentimentDictionary();
	
	public TextAnalysis(Review review) {
		this.text = review.getText();
		this.summary = review.getSummary();
	}

	public SentimentValue getSentiment() {
		
		return null;
	}
	
	public long numSentences() {
		this.sentences = sentenceDetector.sentences(this.text);
		return this.sentences.length;
	}
	
	//TODO here we should count just the words, no numbers nor punctuation symbols.
	public long numWords() {
		long count = 0;
		for(String sentence : sentences) {
			count += tokenizer.tokenize(sentence).length;
		}
		return count;
	}
	
}
