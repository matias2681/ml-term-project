package edu.itu.ml;

public interface Constants {

	String ERROR_LOADING_MODEL_OPENNLP = "Error when loading the NLP model";
	String FILE_POSTAGGER_MODEL_PERCEPTRON = "en-pos-perceptron.bin";
	String FILE_TO_SENTI_WORD_NET = "SentiWordNet_3.0.0_20130122.txt";
	double MAX_NUM_STARS = 5.0;
	String FILE_SENTENCE_DETECTOR_MODEL = "en-sent.bin";
	String FILE_TOKENIZER_MODEL = "en-token.bin";
	String FILE_TO_SNAP_REVIEW_DATA = "test_Arts.txt";
	int MAX_REVIEW_BUFFER_SIZE = 8196;
	String UNKNOWN_PRICE = "unknown";
	String ERROR_READING_REVIEWS_FILE = "There was an error while reading the reviews file";
	String ENCODING = "UTF-8";
	String FEATURE_FILE_AND_PATH = "features.cvs";
	String ERROR_TO_WRITE_CSV = "There was an error while creating the csv file with all the features.";
	String ERROR_WHILE_WRITING_CSV = "There was an error while writing the csv file with all the features.";
	int POSITIVE = 0;
	int NEGATIVE = 1;
	int OBJECTIVE = 2;
	String ERROR_WHILE_OPEN_REVIEW_FILE = "There was an error while opening the reviews file";

}