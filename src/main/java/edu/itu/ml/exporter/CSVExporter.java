package edu.itu.ml.exporter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import edu.itu.ml.Constants;
import edu.itu.ml.core.FeatureItem;
import edu.itu.ml.core.Review;

public class CSVExporter {

	private CSVWriter writer;

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

	public void write(FeatureItem featureItem) {
		try {
			writer.writeNext(featureItem.toCSV());
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(Constants.ERROR_WHILE_WRITING_CSV, e);
		}
	}

	public void write(List<Review> reviews) {
		for (Review review : reviews) {
			write(review.extractFeatures());
		}
	}
	
}
