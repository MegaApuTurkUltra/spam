/**
 * 
 */
package matu.dustbin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author MegaApuTurkUltra
 *
 */
public class Markov implements Serializable {	
	private static final long serialVersionUID = 6296869615152848088L;

	static class IntWrapper implements Serializable {
		private static final long serialVersionUID = -5153626196618729546L;
		int value;
		public IntWrapper() {
			value = 0;
		}
	}
	protected HashMap<String, HashMap<String, IntWrapper>> chainDef;
	
	public Markov() {
		chainDef = new HashMap<>();
	}
	
	public void train(String[] words) {
		for(int i = 0; i < words.length - 1; i++) {
			String word = words[i];
			String nextWord = words[i + 1];
			HashMap<String, IntWrapper> paths = chainDef.get(word);
			if(paths == null) {
				paths = new HashMap<>();
				chainDef.put(word, paths);
			}
			
			IntWrapper nextWordCount = paths.get(nextWord);
			if(nextWordCount == null) {
				nextWordCount = new IntWrapper();
				paths.put(nextWord, nextWordCount);
			}
			nextWordCount.value++;
		}
	}
	
	public String generate() {
		return generate(100);
	}
	
	public String generate(int maxlength) {
		StringBuilder sentence = new StringBuilder();
		
		Object[] keys = chainDef.keySet().toArray();
		Random rand = new Random();
		String nextKey = keys[rand.nextInt(keys.length)].toString();
		HashMap<String, IntWrapper> next = chainDef.get(nextKey);
		do {
			sentence.append(nextKey);
			sentence.append(' ');
			
			int totalCount = 0;
			for(Map.Entry<String, IntWrapper> entry: next.entrySet()) {
				totalCount += entry.getValue().value;
			}
			
			int nextRandom = rand.nextInt(totalCount);
			int counter = 0;
			nextKey = null;
			for(Map.Entry<String, IntWrapper> entry: next.entrySet()) {
				counter += entry.getValue().value;
				if(counter >= nextRandom) {
					nextKey = entry.getKey();
					break;
				}
			}
		} while((next = chainDef.get(nextKey)) != null && sentence.length() < maxlength);
		
		return sentence.toString();
	}
}
