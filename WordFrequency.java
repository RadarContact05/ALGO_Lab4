import java.util.*;

public class WordFrequency {
    public static class wordCount {
        String word;
        int count;

        public wordCount(String word, int count) {
            this.word = word;
            this.count = count;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinearProbingHashMap<String, Integer> map = new LinearProbingHashMap<>();

        while(scanner.hasNext()) {
            String content = scanner.next();
            String word = content.toLowerCase().replaceAll("[^a-z]", "");

            if (!word.equals("")) {
                if (map.contains(word)) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word,1);
                }
            }
        }
        scanner.close();

        List<wordCount> wordList = new ArrayList<>();
        for (String word : map.keys()) {
            int count = map.get(word);
            wordList.add(new wordCount(word, count));
        }

        Collections.sort(wordList, new Comparator<wordCount>() {
            public int compare(wordCount a, wordCount b) {
                return Integer.compare(b.count, a.count);
            }
        });

        for (wordCount wc : wordList) {
            System.out.println(wc.word + " " + wc.count);
        }
    }
}