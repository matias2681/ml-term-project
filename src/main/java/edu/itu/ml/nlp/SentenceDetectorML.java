package edu.itu.ml.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import edu.itu.ml.Constants;

public class SentenceDetectorML {

	private final static Logger LOG = Logger.getLogger(SentenceDetectorML.class.getName());
	private SentenceDetector sentenceDetector = null;
	
	public SentenceDetectorML() {
		InputStream modelIn = null;
		try {
			LOG.info("Loading the Sentence Detector model");
			URL urlPath = this.getClass().getClassLoader().getResource(Constants.FILE_SENTENCE_DETECTOR_MODEL);
			modelIn = new FileInputStream(urlPath.getPath());
			final SentenceModel sentenceModel = new SentenceModel(modelIn);
			modelIn.close();
			sentenceDetector = new SentenceDetectorME(sentenceModel);
			LOG.info("Sentence Detector model loaded");
			
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
