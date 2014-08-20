package edu.itu.ml.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import edu.itu.ml.Constants;

/**
 * This class load the model for a POSTagger from OpenNLP in english. For more
 * information check {@link http://opennlp.sourceforge.net/models-1.5/}
 * 
 */
public class POSTagger {

	private final static Logger LOG = Logger.getLogger(POSTagger.class
			.getName());
	private InputStream modelIn = null;
	POSTaggerME tagger;

	/**
	 * Constructor
	 */
	public POSTagger() {
		try {
			LOG.info("Loading the POS-Tagger model");
			URL urlPath = this.getClass().getClassLoader()
					.getResource(Constants.FILE_POSTAGGER_MODEL_PERCEPTRON);
			modelIn = new FileInputStream(urlPath.getPath());
			POSModel model = new POSModel(modelIn);
			tagger = new POSTaggerME(model);
			LOG.info("POS-Tagger model loaded");
		} catch (IOException e) {
			throw new RuntimeException(Constants.ERROR_LOADING_POSTAGGER_MODEL,
					e);
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
					throw new RuntimeException(
							Constants.ERROR_CLOSSING_POSTAGGER_MODEL, e);
				}
			}
		}
	}

	public String[] tag(String[] sentence) {
		return tagger.tag(sentence);
	}

}