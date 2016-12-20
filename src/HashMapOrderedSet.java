import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
* The HashMapOrderedSet class sorts the returned HashMap entries from WordCount class and returns the
* top N frequencies.
* 
* It also returns reverse HashMap containing top N frequencies as Key and respective word-list as Value
* @author  Priya Dhandev 
*/
public class HashMapOrderedSet {
	private HashMap<Integer, ArrayList<String>> reverseMap;
	private ArrayList<Integer> topfreqlist; 
	private ArrayList<String> allWords;
	int totalwords;
	
	/**
	 * @param frequencyMap The Map returned from WordCount of ParsedURL content
	 * @param size The number of top frequencies desired
	 * 
	 */
	public HashMapOrderedSet(HashMap<String, Integer> frequencyMap, int size) {
		this.getTopNfreq(size, frequencyMap.values());
		this.invertMap(frequencyMap);
	}

	/**
	 * 
	 * @param size The number of top frequencies desired
	 * @param freqcoll The collection of Map's Values (i.e. Frequency of words)
	 */
	private void getTopNfreq(int size, Collection<Integer> freqcoll) {
		this.topfreqlist = new ArrayList<Integer>();
		Iterator<Integer> freqiter = freqcoll.iterator();
		int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE, listsize = 0;
		
		while (freqiter.hasNext()) {
			Integer elem = freqiter.next();
			if (!this.topfreqlist.contains(elem)) {
				if (listsize < size) {
					this.topfreqlist.add(elem);
					listsize++;
	
					if (elem < min) {
						min = elem;
					}
					if (elem > min) {
						max = elem;
					}
				}
				else if (listsize == size){
					if (elem > max) {
						this.topfreqlist.add(elem);
						max = elem;
						this.topfreqlist.remove((Integer)min);
						min = getMin();
					}
				}
				else {
					System.err.println("Algorithm error");
				}
			}
		}
	}

	/**
	 * Inverts the HashMap and saves it in the global variable reverseMap
	 * 
	 * @param frequencyMap The Map returned from WordCount of ParsedURL content
	 */
	private void invertMap(HashMap<String, Integer> frequencyMap) {
		this.reverseMap = new HashMap<Integer, ArrayList<String>>();
		this.allWords = new ArrayList<String>();
		this.totalwords = 0;
		Iterator<Entry<String, Integer>> freqmapiter = frequencyMap.entrySet().iterator();
		while (freqmapiter.hasNext()) {
			 Entry<String, Integer> elem = freqmapiter.next();
			 int freq = elem.getValue();
			 this.totalwords += freq;
			 if (!this.topfreqlist.contains(freq)) {
				 continue;
			 }
			 
			 String word = elem.getKey();
			 this.allWords.add(word);
			 ArrayList<String> al;
			 if (!this.reverseMap.containsKey(freq)) {
				 al = new ArrayList<String>();
			 }
			 else {
				 al = this.reverseMap.get(freq);
			 }
			 al.add(word);
			 this.reverseMap.put(freq, al);
		}
	}
	
	/**
	 *  Helper method for invertMap
	 * @return minimum element in the Map
	 */
	private int getMin() {
		int min = Integer.MAX_VALUE;
		Iterator<Integer> iter = this.topfreqlist.iterator();
		while (iter.hasNext()) {
			int elem = iter.next();
			if (elem < min) {
				min = elem;
			}
		}
		return min;
	}

	public HashMap<Integer, ArrayList<String>> getReverseMap() {
		return reverseMap;
	}

	public ArrayList<Integer> getTopfreqlist() {
		return topfreqlist;
	}

	public ArrayList<String> getAllWords() {
		return allWords;
	}
	
}