package edu.itu.ml.review;

import edu.itu.ml.core.Review;

/**
 * To read reviews is necessary to implement this interface. It basically allows
 * to iterate over the different reviews.
 */
public interface ReviewReader {

	/**
	 * @return the next {@link Review}
	 */
	Review getNext();

	/**
	 * @return <code>true</code> if there are more {@link Review} objects.
	 *         Otherwise <code>false</code>
	 */
	boolean hasNext();

}
