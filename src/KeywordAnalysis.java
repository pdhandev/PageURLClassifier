import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The KeywordAnalysis class decides which words or phrases are keywords defining the webpage
 * 
 * @author  Priya Dhandev 
 */
public class KeywordAnalysis {
	static int mostFrequent = 10;				// the number of top frequencies
	static double mindensity = 1.0;				// minimum keyword density required to qualify for 
	HashMapOrderedSet topNfreq;					// HashMap of top N frequencies and their words' returned by HashMapOrderedSet class
	ArrayList<String> keywords;					// list of keywords to be returned

	/**
	 * 
	 * @param frequencyMap The HashMap prepared by WordCount for Body of the given webpage
	 * @param titleMap The HashMap prepared by WordCount for title of the given webpage
	 * @param bodytext The body text returned by ParsingURL class getBody method
	 */
			
	public KeywordAnalysis(HashMap<String, Integer> frequencyMap, HashMap<String, Integer> titleMap, String bodytext) {
		this.topNfreq = new HashMapOrderedSet(frequencyMap, KeywordAnalysis.mostFrequent);
		this.getSingleKeywords();
		this.getTitleKeywords(titleMap, frequencyMap);
		this.getKeyPhrases(bodytext, this.topNfreq.getAllWords());
	}

	/**
	 * Decides which word in title should be a keyword after checking it's frequency in the body text
	 * 
	 * @param titleMap The HashMap prepared by WordCount for title of the given webpage
	 * @param frequencyMap The HashMap prepared by WordCount for Body of the given webpage
	 */
	private void getTitleKeywords(HashMap<String, Integer> titleMap, HashMap<String, Integer> frequencyMap) {
		Iterator<String> titleiter = titleMap.keySet().iterator();
		while (titleiter.hasNext()) {
			String word = titleiter.next();
			if (frequencyMap.containsKey(word) && !this.keywords.contains(word)) {
				int freq = frequencyMap.get(word);
				if (freq > 1)
					this.keywords.add(word);
			}
		}
	}

	/**
	 * For every two word combination checks whether it is a keyword phrase or not using helper method
	 * checkPhraseKeyword
	 * 
	 * @param text The body text returned by ParsingURL class getBody method
	 * @param allWords Top N frequency words returned by HashMapOrderedSet class
	 */
	private void getKeyPhrases(String text, ArrayList<String> allWords) {
		int size = allWords.size();
		for (int i = 0; i < size; i++) {
			String s1 = allWords.get(i);
			for(int j = i + 1; j < size; j++) {
				String s2 = allWords.get(j);
				String phrase = s1 + " " + s2;
				this.checkPhraseKeyword(phrase, text, s1, s2);
				phrase = s2 + " " + s1;
				this.checkPhraseKeyword(phrase, text, s1, s2);
			}
		}
	}
	
	/**
	 * Calculates frequency of phrase in body text and decides if the ohrase should be a keyword using
	 * helper method isKeyword
	 * 
	 * @param phrase combination of s1 and s2
	 * @param text The body text returned by ParsingURL class getBody method
	 * @param s1 first word parameter
	 * @param s2 second word parameter
	 */
	private void checkPhraseKeyword(String phrase, String text, String s1, String s2) {
		Pattern p1 = Pattern.compile(phrase);
		Matcher m = p1.matcher(text);
		int freq = 0;
		while(m.find()) {
			freq++;
		}
		if (this.isKeyword(freq)) {
			this.keywords.add(phrase);
			this.keywords.remove(s1);
			this.keywords.remove(s2);
		}
	}
	/**
	 * For every word checks whether it is a keyword or not using helper method * isKeyword
	 * 
	 * @param text The body text returned by ParsingURL class getBody method
	 * @param allWords Top N frequency words returned by HashMapOrderedSet class
	 */
	private void getSingleKeywords() {
		this.keywords = new ArrayList<String>();
		Iterator<Integer> freqiter = this.topNfreq.getTopfreqlist().iterator();
		while (freqiter.hasNext()) {
			int freq = freqiter.next();
			if(this.isKeyword(freq)) {
				this.keywords.addAll(this.topNfreq.getReverseMap().get(freq));
			}
		}
	}
/**
 * Calculates the density = frequency/#totalWords*100 and compares it with mindensity to decide
 * 
 * @param freq The frequency of a word to be checked for being a keyword
 * @return true if it is a keyWord else false
 */
	private boolean isKeyword(int freq) {
		double density = freq/(double)this.topNfreq.totalwords * 100;
		if (density > mindensity) {
			return true;
		}
		return false;
	}	
}