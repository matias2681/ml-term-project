package edu.itu.ml.classifier;

import java.util.logging.Logger;

import org.apache.mahout.classifier.evaluation.Auc;
import org.apache.mahout.classifier.sgd.L1;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;

/**
 * This class execute the SGD algorithm with
 *
 */
public class ReviewClassificationWithTestFileMain {

	public static final int NUM_CATEGORIES = 2;
	private final static Logger LOG = Logger.getLogger(ReviewClassificationWithTestFileMain.class.getName());

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		for (int run = 0; run < 50; run++) {
			OnlineLogisticRegression lr = new OnlineLogisticRegression(
					NUM_CATEGORIES, Feature.FEATURES, new L1()).learningRate(1)
					.alpha(1).lambda(0.000001).stepOffset(10000)
					.decayExponent(0.2);
			LOG.info("Training with SGD");
			for (int pass = 0; pass < 20; pass++) {
				LOG.info("Loading train sample");
				FeaturesParser fpTraining = new FeaturesParser("train.csv");
				for (Feature observation : fpTraining) {
					lr.train(observation.getTarget(), observation.asVector());
				}
			}
			
			LOG.info("Measuring AUC");
			FeaturesParser fpTest = new FeaturesParser("test.csv");
			Auc eval = new Auc(0.5);
			LOG.info("Loading test sample");
			for (Feature testCall : fpTest) {
				eval.add(testCall.getTarget(),
						lr.classifyScalar(testCall.asVector()));
			}
			System.out.printf("%.4f, %.4f\n", lr.currentLearningRate(),
					eval.auc());
		}
	}

}
