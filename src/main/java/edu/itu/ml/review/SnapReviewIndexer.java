package edu.itu.ml.review;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;

import edu.itu.ml.Constants;
import edu.itu.ml.core.Review;

// this class is used to parse SNAP format of reviews.
public class SnapReviewIndexer implements ReviewIndexer{
    
    Directory index;
    
    StandardAnalyzer analyzer;
    
    LineIterator lineIterator;
    
    Map<String, Integer> productIdList;
    
    boolean debug = false;
    
    SnapReviewIndexer() {
        try {
	        // 0. Specify the analyzer for tokenizing text.
	        //    The same analyzer should be used for indexing and searching
	        this.analyzer = new StandardAnalyzer(Version.LUCENE_46);
	
	        // 1. create the index
	        this.index = FSDirectory.open(new File(Constants.INDEX_FOLDER));
	        
	        this.productIdList = new HashMap<String, Integer>();
		
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    SnapReviewIndexer(boolean debug) {
    	this();
    	this.debug = debug;
    }

    @SuppressWarnings("resource")
	public boolean index() {
        // TODO Auto-generated method stub

    	// Config index writer
    	IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46, this.analyzer);
        IndexWriter writer = null;
        
        // Open input data file.
		URL snapFilePath = this.getClass().getClassLoader()
				.getResource(Constants.FILE_TO_SNAP_REVIEW_DATA);
	
    	try {
    		writer = new IndexWriter(this.index, config);
        
	        // Remove all previous index to avoid duplicate items
	        writer.deleteAll();

			this.lineIterator = FileUtils.lineIterator(new File(snapFilePath.getPath()), Constants.ENCODING);
		
			String productId = null;
			String producTitle = null;
			String productPrice = null;
			String userId = null;
			String profileName = null;
			String helpfulness = null;
			String score = null;
			String time = null;
			String summary = null;
			String text = null;
			String line = null;
	
	        int numberReviewsRead = 0;

			while (this.lineIterator.hasNext()) {
				line = this.lineIterator.nextLine();
				if (!line.equals("")) {
					if (line.matches("product/productId: (.*)")) {
						productId = line.substring("product/productId: ".length());
					} else if (line.matches("product/title: (.*)")) {
						producTitle = line.substring("product/title: ".length());
					} else if (line.matches("product/price: (.*)")) {
						productPrice = line.substring("product/price: ".length());
					} else if (line.matches("review/userId: (.*)")) {
						userId = line.substring("review/UserId: ".length());
					} else if (line.matches("review/profileName: (.*)")) {
						profileName = line.substring("review/profileName: "
								.length());
					} else if (line.matches("review/helpfulness: (.*)")) {
						helpfulness = line.substring("review/helpfulness: "
								.length());
					} else if (line.matches("review/score: (.*)")) {
						score = line.substring("review/score: ".length());
					} else if (line.matches("review/time: (.*)")) {
						time = line.substring("review/time: ".length());
					} else if (line.matches("review/summary: (.*)")) {
						summary = line.substring("review/summary: ".length());
					} else if (line.matches("review/text: (.*)")) {
						text = line.substring("review/text: ".length());
					} else {
						return false; // throw new Error("review data not complete");
					}
				} else {
					// Add to lucene index
					Document doc = new Document();
					doc.add(new StringField("id", String.valueOf(numberReviewsRead), Field.Store.YES));
					doc.add(new TextField("productId", productId, Field.Store.YES));
					doc.add(new TextField("producTitle", producTitle, Field.Store.YES));
					doc.add(new TextField("productPrice", productPrice, Field.Store.YES));
					doc.add(new TextField("userId", userId, Field.Store.YES));
					doc.add(new TextField("profileName", profileName, Field.Store.YES));
					doc.add(new TextField("helpfulness", helpfulness, Field.Store.YES));
					doc.add(new TextField("score", score, Field.Store.YES));
					doc.add(new TextField("time", time, Field.Store.YES));
					doc.add(new TextField("summary", summary, Field.Store.YES));
					doc.add(new TextField("text", text, Field.Store.YES));
					writer.addDocument(doc);
					
					// Add to productId list
					// TODO do we really need this?
					if (!this.productIdList.containsKey(productId)) {
						this.productIdList.put(productId, 1);
					} else {
						this.productIdList.put(productId, this.productIdList.get(productId) + 1);
					}
					
					// increase id.
					numberReviewsRead++;
				}
			}
			
	        writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
	        try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			e1.printStackTrace();
			return false;
		}
        return true;
    }
	

    public ArrayList<Review> query(String field, String querystr) {
        // TODO Auto-generated method stub

    	ArrayList<Review> reviewList = new ArrayList<Review>();
    	try {
	        // query
	        //Query query = new QueryParser(Version.LUCENE_46, field, analyzer).parse(querystr);
	        Query query = new QueryParser(Version.LUCENE_46, field, analyzer).parse(querystr);
	
	        // search
	        int hitsPerPage = 100;
	        
	        IndexReader reader = DirectoryReader.open(this.index);
	        IndexSearcher searcher = new IndexSearcher(reader);
	        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);

	        if (this.debug) System.out.println("We got total: " + reader.getDocCount("id"));
	        
	        searcher.search(query, collector);
	        ScoreDoc[] hits = collector.topDocs().scoreDocs;
	        
	        // 4. display results
	        if (this.debug) System.out.println("Found " + hits.length + " hits.");
	        
	        for(int i=0;i<hits.length;++i) {
	        	int docId = hits[i].doc;
	        	Document d = searcher.doc(docId);
	        	reviewList.add(new Review.ReviewBuilder()
	        						.productId(d.get("productId"))
						        	.producTitle(d.get("producTitle"))
						        	.productPrice(d.get("productPrice"))
						        	.userId(d.get("userId"))
						        	.profileName(d.get("profileName"))
						        	.helpfulness(d.get("helpfulness"))
						        	.score(d.get("score"))
						        	.time(d.get("time"))
						        	.summary(d.get("summary"))
						        	.text(d.get("text"))
						        	.build());
	        	if (this.debug) System.out.println(String.format("%03d", Integer.valueOf(d.get("id"))) + ". " + d.get("productId") + "\t" + d.get("userId") + "\t" + d.get("time") + "\t" + d.get("summary") + "\t" + d.get("text"));
	        }
	
	        // reader can only be closed when there
	        // is no need to access the documents any more.
	        reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        return reviewList;
    }

    public Map<String, Integer> getProductIdList() {
    	return this.productIdList;
    }
}
