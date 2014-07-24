package edu.itu.ml;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.itu.ml.core.ReviewTest;
import edu.itu.ml.nlp.POSTaggerTest;
import edu.itu.ml.nlp.SentenceDetectorMLTest;
import edu.itu.ml.nlp.SentimentDictionaryTest;

@RunWith(Suite.class)
@SuiteClasses({ ReviewTest.class, POSTaggerTest.class, SentenceDetectorMLTest.class, SentimentDictionaryTest.class})
public class AllTests {

}