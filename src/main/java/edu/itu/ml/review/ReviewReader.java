package edu.itu.ml.review;

import java.io.IOException;

import edu.itu.ml.core.Review;

//class to read reviews.
public abstract class ReviewReader {
	/*
	 * read lucence index
	 * search with query info
	 * find what is interested
	 * read the value
	 * return to request
	 * 
	 * */
	//abstract boolean hasNext();
	abstract Review  getNext() throws IOException;
	
}
