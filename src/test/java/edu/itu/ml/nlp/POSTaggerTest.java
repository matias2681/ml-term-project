package edu.itu.ml.nlp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class POSTaggerTest {

	String [] sentence = new String[]{"Most", "large", "cities", "in", "the", "US", "had",
            "morning", "and", "afternoon", "newspapers", "."};
	
	private POSTagger tagger;
	
	@Before
	public void setUp() throws Exception {
		tagger = new POSTagger();
	}

	@Test
	public void testPOSTaggerWithSimpleSentence() {
		String[] tags = tagger.tag(sentence);
		Assert.assertNotNull(tags);
	}

}
