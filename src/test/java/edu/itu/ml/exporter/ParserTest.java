package edu.itu.ml.exporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.itu.ml.core.ReviewBaseTest;

public class ParserTest extends ReviewBaseTest{

	private Parser parser;
	
	@Before
	public void setUp() throws Exception {
	}

	public void test() {
		parser = new Parser();
		try {
			parser.run();
		} catch(Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testParserWithLimit() {
		parser = new Parser(5000);
		try {
			parser.run();
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
