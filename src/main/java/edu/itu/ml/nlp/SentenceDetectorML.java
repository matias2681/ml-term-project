package edu.itu.ml.nlp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import edu.itu.ml.Constants;

public class SentenceDetectorML {

	private SentenceDetector sentenceDetector = null;
	
	public SentenceDetectorML() {
		InputStream modelIn = null;
		try {
			URL urlPath = this.getClass().getClassLoader().getResource(Constants.FILE_SENTENCE_DETECTOR_MODEL);
			modelIn = getClass().getResourceAsStream(urlPath.getPath());
			final SentenceModel sentenceModel = new SentenceModel(modelIn);
			modelIn.close();
			
			sentenceDetector = new SentenceDetectorME(sentenceModel);
			
		} catch (final IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (final IOException e) {} // oh well!
			}
		}
	}
	
	public String[] sentences(String text) {
		return this.sentenceDetector.sentDetect(text);
	}
}
