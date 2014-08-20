package edu.itu.ml.nlp;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * This class load a Lemmatizer from the framework Stanford Core NLP.
 */
public class Lemmatizer {

	private StanfordCoreNLP lemmatizerStanford;

	/**
	 * Constructor
	 */
	public Lemmatizer() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		lemmatizerStanford = new StanfordCoreNLP(props);
	}

	/**
	 * This method extract the lemma from a given word.
	 * 
	 * @param word
	 *            word to lemmatize.
	 * @return the lemma of the word.
	 */
	public String lemmatize(String word) {
		Annotation doc = new Annotation(word);
		lemmatizerStanford.annotate(doc);
		String lemmatizedWord = new String();
		List<CoreMap> sentences = doc.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				lemmatizedWord = token.get(LemmaAnnotation.class);
			}
		}
		return lemmatizedWord;
	}

}
