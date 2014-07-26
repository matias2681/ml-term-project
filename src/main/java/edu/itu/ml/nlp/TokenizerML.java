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

public class TokenizerML {

	private final static Logger LOG = Logger.getLogger(TokenizerML.class.getName());
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
			ioe.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (final IOException e) {
				} // oh well!
			}
		}
	}
	
	public String[] tokenize(String sentence) {
		return tokenizer.tokenize(sentence);
	}
}
