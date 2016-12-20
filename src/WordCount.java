import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * The WordCount class performs operation on the content returned by ParsingURL in the form of String and returns a HashMap
 * of <word,frequency> after ignoring some stop words mentioned in a Set(leaveWords) made by stopWordSet String array 
 *
 * @author  Priya Dhandev 
 */

public class WordCount{
	private String pageText;
	private static String[] stopWordSet = {"a","am", "ain't", "above","about","again","after", "all","an","are","at","as","at","any","and","be","because","been","before","being","below","between",
			"both","but","by","cannot","could","couldn't","did","didn't","do","doesn't","does","doing","down","during","each","few","for","from","had","has","have","he","her","his",
			"him","i","how","if","in","it","is","is't","into","its","it's","me","might","most","more","must","mustn't","my","no","not","not","of","on","off","or","only","our","other","out",
			"over","own","same","she","should","shouldn't","shant","so","some","such","than","then","that","that's","the","their","theirs","them","then","there","these","they","this",
			"those","through","to","too","under","until","up","very","was","wasn't","we","were","will","won't","what","when","where","which","while","who","whom","why",
			"with","would","you","your","with"};
	
	private static HashSet<String> leaveWords  = new HashSet<String>(Arrays.asList(stopWordSet));
	
	/**
	 * 
	 * @param text The full URL page text from ParsingURL
	 */
	protected WordCount(String text){
		this.pageText = text;
	}
	
	/**
	 * @return The HashMap of word, frequency of word in the webPage.
	 */
	public HashMap<String,Integer> wordCount(){		
		HashMap<String,Integer> wordMap =  new HashMap<String,Integer>();
		String token;
		int tempWordFrequency;
		
		Scanner scan = null;
		try{
			scan = new Scanner(pageText);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		while(scan.hasNext()){
			token = scan.next();
			if(!leaveWords.contains(token)){
				tempWordFrequency = wordMap.getOrDefault(token, 0);
				wordMap.put(token, tempWordFrequency + 1);
			}
		}
		
		return wordMap;
	}
}
