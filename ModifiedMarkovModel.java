import java.util.*;

/**
 * Modify StringBuilder to work with words
 */
class ModifiedStringBuilder {
	StringBuilder sb;
	int count;

	ModifiedStringBuilder() {
		sb = new StringBuilder();
		count = 0;
	}

	int insert(String s) {
		sb.append(s);
		sb.append(" ");
		count++;
		return count;
	}

	String removeFirst() {
		if (sb.length() == 0) return null;
		int index = sb.indexOf(" ");
		String result = sb.substring(0, index);
		sb.delete(0, index);
		sb.deleteCharAt(0);
		count--;
		return result.strip();
	}

	@Override
	public String toString() {
		return sb.toString().strip();
	}
}


/**
 * This is the main class for your Markov Model.
 *
 * Any such empty string in the original text should be ignored.
 */
public class ModifiedMarkovModel {
	/**
	 * Modified HashMap supports the following functions:
	 *     - Total count
	 *     - Insert
	 *     - Geting the Nth word
	 * The underlying data structure is Java's HashMap
	 */
	class ModifiedHashMap {
		HashMap<String, Integer> map;
		int count;

		/**
		 * Constructor for ModifiedHashMap
		 * @param c First item to be added into the ModifiedHashMap
		 */
		ModifiedHashMap(String c) {
			map = new HashMap<>();
			map.put(c, 1);
			count = 1;
		}

		/**
		 * Insert String into hashmap. Increment the count if String is already present, else add it into the HashMap
		 * Increment the total count.
		 * @param c String to be inserted
		 */
		void insert(String c) {
			map.put(c, map.getOrDefault(c, 0) + 1);
			count++;
		}

		/**
		 * Return the total count
		 * @return the number of items added to the HashMap.
		 */
		int getCount() {
			return count;
		}

		/**
		 * Return the Nth element, if all the string are sorted in order.
		 *
		 * If there is  3 "I'm" and 4 "world", the 5th element to be returned is "world". Likewise, the 3rd element
		 * will be "I'm"
		 * @param n the Nth string to find
		 * @return Nth string
		 */
		String getNext(int n) {
			List<String> lst = new ArrayList<>();
			map.forEach((k, v) -> {for (int i = 0; i < v; i++) lst.add(k);});
			lst.sort(Comparator.naturalOrder());
			return lst.get(n);
		}
	}


	HashMap<String, ModifiedHashMap> table;
	int order;

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate empty String
	public static final String NOSTRING = "";

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of String to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public ModifiedMarkovModel(int order, long seed) {
		// Initialize your class here
		table = new HashMap<>();
		this.order = order;

		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
		if (text == null) return;

		/* Split the input into a String array */
		String[] words = text.split(" ");

		ModifiedStringBuilder msb = new ModifiedStringBuilder();
		/* adding inputs into the hashmap */
		for (int i = 0; i < words.length - 1; i++) {
			int count;
			if (words[i].compareTo("") != 0) {
				count = msb.insert(words[i]);
			} else {
				count = msb.count;
			}

			if (count == order && words[i + 1].compareTo("") != 0) {
				String temp = msb.toString();
				msb.removeFirst();
				if (table.containsKey(temp)) { // if string is already in hashmap
					table.get(temp).insert(words[i + 1]);
				} else {
					table.put(temp, new ModifiedHashMap(words[i + 1])); // if string is not in hashmap
				}
			}
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		if (kgram == null || countWords(kgram) != order) return -1; // error checking

		if (table.containsKey(kgram))
			return table.get(kgram).getCount();
		else
			return 0;
	}


	/**
	 * Generates the next String from the Markov Model.
	 * Return NOSTRING if the kgram is not in the table, or if there is no
	 * valid string following the kgram.
	 */
	public String nextString(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		int total = getFrequency(kgram);

		if (total <= 0) return NOSTRING;

		int index = generator.nextInt(total);
		return table.get(kgram).getNext(index);
	}


	/**
	 * Count the number of words in a string.
	 * Assume that words are seperated by " ".
	 * @param s string to count number of words.
	 * @return number of words.
	 */
	static int countWords(String s) {
		if (s == null) return 0;
		s = s.strip();
		if (s.length() == 0) return 0;
		int count = 1;
		boolean spaceBefore = false;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				spaceBefore = true;
			} else {
				if (spaceBefore) {
					count++;
					spaceBefore = false;
				}
			}
		}
		return count;
	}
}
