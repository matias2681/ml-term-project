package edu.itu.ml.exporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.itu.ml.core.ReviewBaseTest;

public class ParserTest extends ReviewBaseTest{

	private Parser parser = new Parser();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		try {
			parser.run();
		} catch(Exception e) {
			Assert.fail();
		}
	}

}
