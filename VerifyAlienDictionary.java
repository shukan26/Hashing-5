


/**
 * Verifies alien dictionary order by mapping characters to ranks and comparing adjacent words lexicographically.
 * Returns false if any adjacent pair violates custom order; otherwise true.
 * Time: O(N * L), Space: O(1) extra (fixed alphabet mapping).
 */
public class VerifyAlienDictionary {

    Map<Character, Integer> map;
    public boolean isAlienSorted(String[] words, String order) {
        map = new HashMap<>();
        for (int i = 0; i < order.length(); i++) {
            map.put(order.charAt(i), i);
        }
        for (int i = 0; i < words.length - 1; i++) {
            if (!compare(words[i], words[i+1])) return false;
        }
        return true;
    }

    private boolean compare(String word1, String word2) {
        int l1 = word1.length(), l2 = word2.length();
        for (int i = 0, j = 0; i < l1 && j < l2; i++, j++) {
            if (word1.charAt(i) != word2.charAt(j)) {
                if (map.get(word1.charAt(i)) > map.get(word2.charAt(j))) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        if (l1 > l2) return false;
        return true;
    }
    
}
