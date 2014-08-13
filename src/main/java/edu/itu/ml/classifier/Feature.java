package edu.itu.ml.classifier;

import java.util.Iterator;
import java.util.Map;

import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.vectorizer.encoders.ConstantValueEncoder;
import org.apache.mahout.vectorizer.encoders.FeatureVectorEncoder;
import org.apache.mahout.vectorizer.encoders.StaticWordValueEncoder;

import com.google.common.collect.Maps;

public class Feature {

	public static final int FEATURES = 100;
	private static final ConstantValueEncoder interceptEncoder = new ConstantValueEncoder(
			"intercept");
	private static final FeatureVectorEncoder featureEncoder = new StaticWordValueEncoder(
			"feature");

	private RandomAccessSparseVector vector;
	private Map<String, String> fields = Maps.newLinkedHashMap();

	public Feature(Iterable<String> fieldNames, Iterable<String> values) {
		vector = new RandomAccessSparseVector(FEATURES);
		Iterator<String> value = values.iterator();
		interceptEncoder.addToVector("1", vector);
		for (String name : fieldNames) {
			String fieldValue = value.next();
			fields.put(name, fieldValue);

			if (name.equals("age")) {
				double v = Double.parseDouble(fieldValue);
				featureEncoder.addToVector(name, Math.log(v), vector);

			} else if (name.equals("normUserRating")
					|| name.equals("numSentences") || name.equals("numWords")
					|| name.equals("wordsPerSentence")) {
				double v = Double.parseDouble(fieldValue);
				featureEncoder.addToVector(name, Math.log(v + 1), vector);

			} else if (name.equals("numSentimental+")
					|| name.equals("numSentimental-")
					|| name.equals("sentimentRating+")
					|| name.equals("sentimentRating-")
					|| name.equals("density+") || name.equals("density-")) {
				double v = Double.parseDouble(fieldValue);
				featureEncoder.addToVector(name, Math.log(v + 2), vector);

			} else if (name.equals("usefulness")
					|| name.equals("sentimentRatingo")
					|| name.equals("numSentimentalo")
					|| name.equals("densityo")) {
				// ignore these for vectorizing
			} else {
				throw new IllegalArgumentException(String.format(
						"Bad field name: %s", name));
			}
		}
	}

	public Vector asVector() {
		return vector;
	}

	public int getTarget() {
		return fields.get("usefulness").equals("unuseful") ? 0 : 1;
	}

}
