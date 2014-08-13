package edu.itu.ml.classifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.io.Resources;

import edu.itu.ml.Constants;

public class FeaturesParser implements Iterable<Feature> {

	private final Splitter onSemi = Splitter.on(Constants.SEPARATOR_SYMBOL)
			.trimResults(CharMatcher.anyOf("\" ;"));
	private String resourceName;

	public FeaturesParser(String resourceName) throws IOException {
		this.resourceName = resourceName;
	}

	@Override
	public Iterator<Feature> iterator() {
		try {
			return new AbstractIterator<Feature>() {
				BufferedReader input = new BufferedReader(
						new InputStreamReader(Resources.getResource(
								resourceName).openStream()));
				Iterable<String> fieldNames = onSemi.split(input.readLine());

				@Override
				protected Feature computeNext() {
					try {
						String line = input.readLine();
						if (line == null) {
							return endOfData();
						}

						return new Feature(fieldNames, onSemi.split(line));
					} catch (IOException e) {
						throw new RuntimeException("Error reading data", e);
					}
				}
			};
		} catch (IOException e) {
			throw new RuntimeException("Error reading data", e);
		}
	}
}
