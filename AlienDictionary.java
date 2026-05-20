


/**
 * Builds a directed graph from adjacent words to infer character order, then performs Kahn’s BFS topological sort.
 * Detects invalid prefix cases and cycles to ensure a valid alien dictionary order.
 * Time: O(C + E), Space: O(C + E), where C = total characters, E = ordering constraints.
 */
public class AlienDictionary {
        public String alienOrder(String[] words) {

        // Step 1: Initialize adjacency list and in-degree map
        // adjList: character → list of characters it points to
        // uniqueCharacterFreqMap: character → number of incoming edges (in-degree)
        Map<Character, List<Character>> adjList = new HashMap<>();
        Map<Character, Integer> uniqueCharacterFreqMap = new HashMap<>();

        // Initialize nodes in the graph for all unique characters
        for (String word : words) {
            for (char c : word.toCharArray()) {
                adjList.putIfAbsent(c, new ArrayList<>());
                uniqueCharacterFreqMap.putIfAbsent(c, 0);
            }
        }

        // Step 2: Build graph (adjList) and in-degree map (uniqueCharacterFreqMap)
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];

            // Edge case: ["abc", "ab"] → invalid because word2 is a prefix of word1
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }

            // Compare characters of adjacent words to find ordering constraint
            int length = Math.min(word1.length(), word2.length());
            for (int j = 0; j < length; j++) {
                char from = word1.charAt(j);
                char to = word2.charAt(j);
                if (from != to) {
                    // Add edge: from → to
                    adjList.get(from).add(to);
                    // Increment in-degree of 'to'
                    uniqueCharacterFreqMap.put(to, uniqueCharacterFreqMap.get(to) + 1);
                    break; // Only consider the first different character
                }
            }
        }

        // Step 3: Perform BFS (Kahn's algorithm for topological sort)
        Queue<Character> q = new LinkedList<>();

        // Start with characters that have 0 in-degree (no dependencies)
        for (char c : uniqueCharacterFreqMap.keySet()) {
            if (uniqueCharacterFreqMap.get(c).equals(0)) {
                q.add(c);
            }
        }

        StringBuilder sb = new StringBuilder();

        // Process nodes with in-degree 0
        while (!q.isEmpty()) {
            char currentChar = q.poll();
            sb.append(currentChar);

            // Reduce in-degree of neighbors and add them to queue if they reach 0
            for (Character neighbor : adjList.get(currentChar)) {
                uniqueCharacterFreqMap.put(neighbor, uniqueCharacterFreqMap.get(neighbor) - 1);
                if (uniqueCharacterFreqMap.get(neighbor).equals(0)) {
                    q.add(neighbor);
                }
            }
        }

        // If result length matches total unique characters, it's valid
        if (sb.length() == uniqueCharacterFreqMap.size())
            return sb.toString();

        // Otherwise, there's a cycle → invalid order
        return "";
    }
    
}
