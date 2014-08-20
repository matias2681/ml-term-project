package edu.itu.ml.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import edu.itu.ml.Constants;

/**
 * Tokenizer from OpenNLP. It loads an english tokenizer model. For more
 * information check {@link http://opennlp.sourceforge.net/models-1.5/}
 * 
 */
public class TokenizerML {

	private final static Logger LOG = Logger.getLogger(TokenizerML.class
			.getName());
	private Tokenizer tokenizer = null;

	public TokenizerML() {
		InputStream modelIn = null;
		try {
			LOG.info("Loading the Tokenizer model");
			URL urlPath = this.getClass().getClassLoader()
					.getResource(Constants.FILE_TOKENIZER_MODEL);
			modelIn = new FileInputStream(urlPath.getPath());
			final TokenizerModel tokenModel = new TokenizerModel(modelIn);
			modelIn.close();
			tokenizer = new TokenizerME(tokenModel);
			LOG.info("Tokenizer model loaded");
		} catch (final IOException ioe) {
			throw new RuntimeException(Constants.ERROR_LODING_TOKENIZER_MODEL, ioe);
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (final IOException e) {
					throw new RuntimeException(Constants.ERROR_CLOSING_TOKENIZER_MODEL, e);
				} 
			}
		}
	}

	public String[] tokenize(String sentence) {
		return tokenizer.tokenize(sentence);
	}
}
