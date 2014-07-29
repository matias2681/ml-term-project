package edu.itu.ml.review;

import java.util.ArrayList;
import java.util.Map;


import edu.itu.ml.core.Review;

public interface ReviewIndexer {
	/*
	 * call other indexer to read 
	 * search with query info
	 * find what is interested
	 * read the value
	 * return to request
	 * 
	 * */
	
	public boolean index(); // return success or fail.
	
	public ArrayList<Review> query(String field, String querystr);

    public Map<String, Integer> getProductIdList();
}
