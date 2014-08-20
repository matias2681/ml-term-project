package edu.itu.ml.exporter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import edu.itu.ml.Constants;
import edu.itu.ml.core.FeatureItem;
import edu.itu.ml.core.Review;

/**
 * This class export the features from the reviews in <code>CSV</code> format
 *
 */
public class CSVExporter {

	private CSVWriter writer;

	/**
	 * Constructor
	 */
	public CSVExporter() {
		try {
			writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(
					Constants.FEATURE_FILE_AND_PATH), "UTF-8"), Constants.SEPARATOR_SYMBOL);
			writer.writeNext(FeatureItem.columns());
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(Constants.ERROR_TO_WRITE_CSV, e);
		}
	}

	/**
	 * Write the parameter in the file with the specified format.
	 * @param featureItem a feature extracted
	 */
	public void write(FeatureItem featureItem) {
		try {
			writer.writeNext(featureItem.toCSV());
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(Constants.ERROR_WHILE_WRITING_CSV, e);
		}
	}

	/**
	 * Handy method to use with a list of {@link Review}.
	 * This write the list in a file.
	 * @param reviews the {@link Review} 
	 */
	public void write(List<Review> reviews) {
		for (Review review : reviews) {
			write(review.extractFeatures());
		}
	}
	
}
