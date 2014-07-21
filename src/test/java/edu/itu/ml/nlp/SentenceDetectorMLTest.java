package edu.itu.ml.nlp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.itu.ml.core.ReviewBaseTest;

public class SentenceDetectorMLTest extends ReviewBaseTest{

	private SentenceDetectorML sentenceDetectorML = new SentenceDetectorML();
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testSimpleSentenceDetector() {
		String[] sentences = sentenceDetectorML.sentences(this.text);
		for (String sentence : sentences) {
			Assert.assertNotNull(sentence);
		}
	}

}
