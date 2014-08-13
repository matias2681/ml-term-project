package edu.itu.ml.lemmatizer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.itu.ml.nlp.Lemmatizer;

public class LemmatizerExample {

	private Lemmatizer lemmatizer;

	@Before
	public void setUp() throws Exception {
		lemmatizer = new Lemmatizer();
	}

	@Test
	public void test() {
		String lemmatizedWord = lemmatizer.lemmatize("went");
		Assert.assertTrue("go".equals(lemmatizedWord));
	}

}
