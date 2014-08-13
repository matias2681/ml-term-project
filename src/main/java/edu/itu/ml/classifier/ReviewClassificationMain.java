package edu.itu.ml.classifier;

import java.util.Collections;
import java.util.List;

import org.apache.mahout.classifier.evaluation.Auc;
import org.apache.mahout.classifier.sgd.L1;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;

import com.google.common.collect.Lists;

public class ReviewClassificationMain {

	public static final int NUM_CATEGORIES = 2;

	public static void main(String[] args) throws Exception {
		List<Feature> features = Lists.newArrayList(new FeaturesParser(
				"features-no-nan-1.csv"));

		double heldOutPercentage = 0.10;

		for (int run = 0; run < 20; run++) {
			Collections.shuffle(features);
			int cutoff = (int) (heldOutPercentage * features.size());
			List<Feature> test = features.subList(0, cutoff);
			List<Feature> train = features.subList(cutoff, features.size());

			OnlineLogisticRegression lr = new OnlineLogisticRegression(
					NUM_CATEGORIES, Feature.FEATURES, new L1())
					.learningRate(1).alpha(1).lambda(0.000001)
					.stepOffset(10000).decayExponent(0.2);
			for (int pass = 0; pass < 20; pass++) {
				for (Feature observation : train) {
					lr.train(observation.getTarget(), observation.asVector());
				}
				if (pass % 5 == 0) {
					Auc eval = new Auc(0.5);
					for (Feature testCall : test) {
						eval.add(testCall.getTarget(),
								lr.classifyScalar(testCall.asVector()));
					}
					System.out.printf("%d, %.4f, %.4f\n", pass,
							lr.currentLearningRate(), eval.auc());
				}
			}

		}
	}
	
}
