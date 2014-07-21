package edu.itu.ml.nlp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SentimentDictionaryTest {

	String [] sentence = new String[]{"Most", "large", "cities", "in", "the", "US", "had",
            "morning", "and", "afternoon", "newspapers", "."};
	
	private SentimentDictionary sentimentDictionary;
	
	@Before
	public void setup() throws Exception {
		sentimentDictionary = new SentimentDictionary();
	}

	@Test
	public void testOriginalFormat() {
		//System.out.println("good#a " + sentimentDictionary.sentiment("good", "a").tostring());
	    //System.out.println("bad#a "  + sentimentDictionary.sentiment("bad", "a").tostring());
	    //System.out.println("blue#a " + sentimentDictionary.sentiment("blue", "a").tostring());
	    //System.out.println("blue#n " + sentimentDictionary.sentiment("blue", "n").tostring());
		
		Assert.assertNotNull(sentimentDictionary.sentiment("good", "a"));

		Assert.assertNotNull(sentimentDictionary.sentiment("not_exist_value", "a"));
	}

	@Test
	public void testOpenNLPFormat() {
	    //System.out.println("good#RB " + sentimentDictionary.extract("good", "RB").tostring());
	    //System.out.println("good#RBS " + sentimentDictionary.extract("good", "RBS").tostring());
	    //System.out.println("good#RBR " + sentimentDictionary.extract("good", "RBR").tostring());
		
	    Assert.assertNotNull(sentimentDictionary.extract("good", "RB"));
	    
	    Assert.assertTrue(sentimentDictionary.extract("good", "RB").equals(sentimentDictionary.extract("good", "RBS")));
	    Assert.assertFalse(sentimentDictionary.extract("good", "RB").equals(sentimentDictionary.extract("not_exist_value", "RBS")));
	}
	
	@Test
	public void testResultNotExistValue() {
	    System.out.println(sentimentDictionary.extract("not_exist_value", "RB"));
	}

}
