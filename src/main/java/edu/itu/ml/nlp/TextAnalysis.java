package edu.itu.ml.nlp;

import java.util.ArrayList;
import java.util.List;

import edu.itu.ml.core.Review;

public class TextAnalysis {

	private String text;
	//TODO add sentiment features to summary later
	//private String summary;
	private POSTagger tagger = new POSTagger();
	private SentenceDetectorML sentenceDetector = new SentenceDetectorML();
	private TokenizerML tokenizer = new TokenizerML();
	private String[] sentences;
	private SentimentDictionary dictionary = new SentimentDictionary();
	private static TextAnalysis instance = new TextAnalysis();
	
	private TextAnalysis() {
	}
	
	public static TextAnalysis getInstance() {
		return instance;
	}
	
	public void analyze(Review review) {
		this.text = review.getText();
	}

	public SentimentValue getSentiment() {
		SentimentValue total = new SentimentValue(0, 0);
		double num = 0d;
		for(String sentence: sentences) {
			String[] sentenceSplit = this.convertSentenceTo(sentence);
			String[] tags = tagger.tag(sentenceSplit);
			for (int i = 0; i < sentenceSplit.length; i++) {
				//TODO here we need to use a lemmatizer.
				//Add opennlp version 1.6.0 it has a lemmatizer. 
				SentimentValue value = dictionary.extract(sentenceSplit[i], tags[i]);
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
		for(String word : tokenizer.tokenize(sentence)) {
			result.add(word);
		}
		return result.toArray(new String[result.size()]);
	}

	public long numSentences() {
		this.sentences = sentenceDetector.sentences(this.text);
		return this.sentences.length;
	}
	
	//TODO here we should count just the words, no numbers nor punctuation symbols.
	// However is a good upper bound.
	public long numWords() {
		long count = 0;
		for(String sentence : sentences) {
			count += tokenizer.tokenize(sentence).length;
		}
		return count;
	}
	
}
