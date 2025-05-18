import java.util.*;

public class WordFrequency {
    public static class wordCount {
        String word;
        int count;

        // constructor for wordCount
        public wordCount(String word, int count) {
            this.word = word;
            this.count = count;
        }
    }
    public static void main(String[] args) {
        // reads from standard input
        Scanner scanner = new Scanner(System.in);

        // mapping each word to its current count
        LinearProbingHashMap<String, Integer> map = new LinearProbingHashMap<>();

        // read input until no more token
        while(scanner.hasNext()) {
            String content = scanner.next();

            // Normalize
            String word = content.toLowerCase().replaceAll(".,", "");

        // if not empty, update count
            if (!word.equals("")) {
                if (map.contains(word)) {
                    // increment existing count
                    map.put(word, map.get(word) + 1);
                } else {
                    // insert first occurrence with count = 1
                    map.put(word,1);
                }
            }
        }
        scanner.close();

        // Tranfer map entries to a list of WordCount for sorting
        List<wordCount> wordList = new ArrayList<>();
        for (String word : map.keys()) {
            int count = map.get(word);
            wordList.add(new wordCount(word, count));
        }

        // Sort wordList by count descending
        Collections.sort(wordList, new Comparator<wordCount>() {
            public int compare(wordCount a, wordCount b) {
                // compare b to a for descedning order 
                return Integer.compare(b.count, a.count);
            }
        });

        for (wordCount wc : wordList) {
            System.out.println(wc.word + " " + wc.count);
        }
    }
}