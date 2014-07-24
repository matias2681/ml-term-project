package edu.itu.ml.review;

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
//import java.util.regex.Pattern;


import edu.itu.ml.Constants;
import edu.itu.ml.core.Review;

public class SnapReviewReader extends ReviewReader {
	
	BufferedReader reader;
	FileInputStream fis;
	
	SnapReviewReader() throws IOException{
		// read all file into buffer
		// parse to Review class
		
		URL snapFilePath = this.getClass().getClassLoader().getResource(Constants.FILE_TO_SNAP_REVIEW_DATA);

		this.reader = new BufferedReader(new FileReader(snapFilePath.getPath()), Constants.MAX_REVIEW_BUFFER_SIZE);
		
		//this.fis = new FileInputStream(snapFilePath.getPath());
	}
			
	SnapReviewReader(URL snapFilePath) throws IOException{
	}

	@Override
	public Review getNext() throws IOException {
		// TODO Auto-generated method stub

		String productId = null;
		String producTitle = null;;
		String productPrice = null;;
		String userId = null;;
		String profileName = null;;
		String helpfulness = null;;
		String score = null;;
		String time = null;;      
		String summary = null;;
		String text = null;;

		String line = null;

		while ((line = this.reader.readLine()) != null){
			if (! line.equals("")) {
				if (                line.matches("product/productId: (.*)")){
					productId    = line.substring("product/productId: ".length());
				} else if (          line.matches("product/title: (.*)")){
					producTitle  = line.substring("product/title: ".length());
				} else if (          line.matches("product/price: (.*)")){
					productPrice = line.substring("product/price: ".length());
				} else if (          line.matches("review/userId: (.*)")){
					userId       = line.substring("review/UserId: ".length());
				} else if (          line.matches("review/profileName: (.*)")){
					profileName  = line.substring("review/profileName: ".length());
				} else if (          line.matches("review/helpfulness: (.*)")){
					helpfulness  = line.substring("review/helpfulness: ".length());
				} else if (          line.matches("review/score: (.*)")){
					score        = line.substring("review/score: ".length());
				} else if (          line.matches("review/time: (.*)")){
					time         = line.substring("review/time: ".length());
				} else if (          line.matches("review/summary: (.*)")){
					summary      = line.substring("review/summary: ".length());
				} else if (	         line.matches("review/text: (.*)")){
					text         = line.substring("review/text: ".length());
				} else{
					return null; //throw new Error("review data not complete");
				}
			} else {
				break;
			}
		}
		Review review = new Review.ReviewBuilder().productId(productId)
				.producTitle(producTitle).productPrice(productPrice)
				.userId(userId).profileName(profileName)
				.helpfulness(helpfulness).score(score).time(time)
				.summary(summary).text(text).build();
		/*
		System.out.println(productId);
		System.out.println(producTitle);
		System.out.println(productPrice);
		System.out.println(userId);
		System.out.println(profileName);
		System.out.println(helpfulness);
		System.out.println(score);
		System.out.println(time);    
		System.out.println(summary);
		System.out.println(text);
		*/
		return review;
	}

	@SuppressWarnings("unused")
	private Review oldGetNext() throws IOException {
		// TODO Auto-generated method stub

		byte buffer[] = new byte[2048];
		int n;
		while ((n = fis.read(buffer)) != -1){
			for (int i = 0; i < n-1; i++) {
				if(buffer[i] == '\n' && buffer[i+1] == '\n'){
					break;
				}
			}
		}
		
		System.out.println(buffer);
		
		return null;

	}

}
