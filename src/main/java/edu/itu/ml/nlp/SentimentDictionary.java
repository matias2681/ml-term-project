package edu.itu.ml.nlp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.jfree.util.Log;
import org.joda.time.Interval;
import org.joda.time.Period;

import edu.itu.ml.Constants;

public class SentimentDictionary {

	private final static Logger LOG = Logger.getLogger(SentimentDictionary.class.getName());
    private Map<String, SentimentValue> dic_Senti;
    private Map<String, String> dic_NlpToSenti; // tag pos mapping from NLP format to SentiNetWord format
    
    public SentimentDictionary() {
    	LOG.info("Loading Sentiment Dictionary");
    	long begin = System.currentTimeMillis(); 
    	// Generate a map from openNLP pos format to SentiWordNet pos format.
    	create_openNLP_to_SentiWordNet_map() ;
        
    	// This is our main dictionary representation
        this.dic_Senti = new HashMap<String, SentimentValue>();

        // From String to list of doubles.
        HashMap<String, HashMap<Integer, SentimentValue>> tempDictionary = new HashMap<String, HashMap<Integer, SentimentValue>>();

        BufferedReader csv = null;

        try {
            URL urlPath = this.getClass().getClassLoader().getResource(Constants.FILE_TO_SENTI_WORD_NET);
            csv = new BufferedReader(new FileReader(urlPath.getPath()));
            int lineNumber = 0;

            String line;
            while ((line = csv.readLine()) != null) {
                lineNumber++;

                // If it's a comment, skip this line.
                if (!line.trim().startsWith("#")) {
                    // We use tab separation
                    String[] data = line.split("\t");
                    String wordTypeMarker = data[0];

                    // Example line:
                    // POS ID PosS NegS SynsetTerm#sensenumber Desc
                    // a 00009618 0.5 0.25 spartan#4 austere#3 ascetical#2
                    // ascetic#2 practicing great self-denial;...etc

                    // Is it a valid line? Otherwise, through exception.
                    if (data.length != 6) {
                        throw new IllegalArgumentException(
                                "Incorrect tabulation format in file, line: "
                                        + lineNumber);
                    }

                    // Calculate synset score as score = PosS - NegS
                    SentimentValue sentiValue = new SentimentValue(Double.parseDouble(data[2]),
                                Double.parseDouble(data[3]));
                    //Double synsetScore = Double.parseDouble(data[2])
                    //        - Double.parseDouble(data[3]);

                    // Get all Synset terms
                    String[] synTermsSplit = data[4].split(" ");

                    // Go through all terms of current synset.
                    for (String synTermSplit : synTermsSplit) {
                        // Get synterm and synterm rank
                        String[] synTermAndRank = synTermSplit.split("#");
                        String synTerm = synTermAndRank[0] + "#"
                                + wordTypeMarker;

                        int synTermRank = Integer.parseInt(synTermAndRank[1]);
                        // What we get here is a map of the type:
                        // term -> {score of synset#1, score of synset#2...}

                        // Add map to term if it doesn"t have one
                        if (!tempDictionary.containsKey(synTerm)) {
                            tempDictionary.put(synTerm,
                                    new HashMap<Integer, SentimentValue>());
                        }

                        // Add synset link to synterm
                        tempDictionary.get(synTerm).put(synTermRank,
                                sentiValue);
                                //synsetScore);
                    }
                }
            }

            // Go through all the terms.
            for (Map.Entry<String, HashMap<Integer, SentimentValue>> entry : tempDictionary
                    .entrySet()) {
                String word = entry.getKey();
                Map<Integer, SentimentValue> synSetScoreMap = entry.getValue();

                // Calculate weighted average. Weigh the synsets according to
                // their rank.
                // Score= 1/2*first + 1/3*second + 1/4*third ..... etc.
                // Sum = 1/1 + 1/2 + 1/3 ...
                SentimentValue score = new SentimentValue(0, 0);;
                double sum = 0.0;
                for (Map.Entry<Integer, SentimentValue> setScore : synSetScoreMap
                        .entrySet()) {
                    score.add((SentimentValue) setScore.getValue().div((double) setScore.getKey()));
                    sum += 1.0 / (double) setScore.getKey();
                }
                score.div(sum);

                this.dic_Senti.put(word, score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	long end = System.currentTimeMillis();
        	Period period = new Period(begin, end);
        	Log.info("Loading the Sentiment Dictionary took: " + period.getSeconds() + " seconds");
            if (csv != null) {
                try {
                    csv.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     *  OpenNLP:
     *  (http://paula.petcu.tm.ro/init/default/post/opennlp-part-of-speech-tags)
     *   
     *   SentiWordNet
     *   (http://stackoverflow.com/questions/10223314/using-sentiwordnet-3-0)
     *   n    NOUN
     *   v    VERB
     *   a    ADJECTIVE
     *   s    ADJECTIVE SATELLITE (not found in data set)
     *   r    ADVERB
     */

    private void create_openNLP_to_SentiWordNet_map() {
    	this.dic_NlpToSenti = new HashMap<String, String>();
    	// all openNLP posTags
                                              // Description
        //this.dic_NlpToSenti.put("CC",  "");   // Coordinating conjunction
        //this.dic_NlpToSenti.put("CD",  "");   // Cardinal number
        //this.dic_NlpToSenti.put("DT",  "");   // Determiner
        //this.dic_NlpToSenti.put("EX",  "");   // Existential there
        //this.dic_NlpToSenti.put("FW",  "");   // Foreign word
        //this.dic_NlpToSenti.put("IN",  "");   // Preposition or subordinating conjunction
        this.dic_NlpToSenti.put("JJ",  "a");  // Adjective
        this.dic_NlpToSenti.put("JJR", "a");  // Adjective, comparative
        this.dic_NlpToSenti.put("JJS", "a");  // Adjective, superlative
        //this.dic_NlpToSenti.put("LS",  "");   // List item marker
        //this.dic_NlpToSenti.put("MD",  "");   // Modal
        this.dic_NlpToSenti.put("NN",  "n");  // Noun, singular or mass
        this.dic_NlpToSenti.put("NNS", "n");  // Noun, plural
        this.dic_NlpToSenti.put("NNP", "n");  // Proper noun, singular
        this.dic_NlpToSenti.put("NNPS","n");  // Proper noun, plural
        //this.dic_NlpToSenti.put("PDT", "");   // Predeterminer
        //this.dic_NlpToSenti.put("POS", "");   // Possessive ending
        //this.dic_NlpToSenti.put("PRP", "");   // Personal pronoun
        //this.dic_NlpToSenti.put("PRP$","");   // Possessive pronoun
        this.dic_NlpToSenti.put("RB",  "r");  // Adverb
        this.dic_NlpToSenti.put("RBR", "r");  // Adverb, comparative
        this.dic_NlpToSenti.put("RBS", "r");  // Adverb, superlative
        //this.dic_NlpToSenti.put("RP",  "");   // Particle
        //this.dic_NlpToSenti.put("SYM", "");   // Symbol
        //this.dic_NlpToSenti.put("TO",  "");   // to
        //this.dic_NlpToSenti.put("UH",  "");   // Interjection
        this.dic_NlpToSenti.put("VB",  "v");  // Verb, base form
        this.dic_NlpToSenti.put("VBD", "v");  // Verb, past tense
        this.dic_NlpToSenti.put("VBG", "v");  // Verb, gerund or present participle
        this.dic_NlpToSenti.put("VBN", "v");  // Verb, past participle
        this.dic_NlpToSenti.put("VBP", "v");  // Verb, non-3rd person singular present
        this.dic_NlpToSenti.put("VBZ", "v");  // Verb, 3rd person singular present
        //this.dic_NlpToSenti.put("WDT", "");   // Wh-determiner
        //this.dic_NlpToSenti.put("WP ", "");   // Wh-pronoun
        //this.dic_NlpToSenti.put("WP$", "");   // Possessive wh-pronoun
        this.dic_NlpToSenti.put("WRB", "r");   // Wh-adverb
    }
    
    public SentimentValue sentiment(String word, String pos) {
        String sentiWordKey = word + "#" + pos;
        if (!this.dic_Senti.containsKey(sentiWordKey)) {
            return new SentimentValue(0, 0);
        } else {
            return this.dic_Senti.get(sentiWordKey);
        }
    }

    public SentimentValue extract(String word, String pos_OpenNLP) {
        // if NLP tag pos is not in our map.
        if (!this.dic_NlpToSenti.containsKey(pos_OpenNLP)) {
            return new SentimentValue(0, 0);
        } else {
            return this.sentiment(word, this.dic_NlpToSenti.get(pos_OpenNLP));
        }
    }
    
}
