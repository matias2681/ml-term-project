package edu.itu.ml.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import edu.itu.ml.Constants;

public class POSTagger {

	private InputStream modelIn = null;
	POSTaggerME tagger;

	public POSTagger() {
		try {
			URL urlPath = this.getClass().getClassLoader().getResource("en-pos-perceptron.bin");
			modelIn = new FileInputStream(urlPath.getPath());
			POSModel model = new POSModel(modelIn);
			tagger = new POSTaggerME(model);
		} catch (IOException e) {
			throw new RuntimeException(Constants.ERROR_LOADING_MODEL_OPENNLP, e);
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public String[] tag(String[] sentence) {
		return tagger.tag(sentence);
	}



}