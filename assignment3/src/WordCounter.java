import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class WordCounter {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); //count execution time

        // Hashmaps for 6, 7 ,8 lettered words
        HashMap<String, Integer> wordsSixMap = new HashMap<>();
        HashMap<String, Integer> wordsSevenMap = new HashMap<>();
        HashMap<String, Integer> wordsEightMap = new HashMap<>();

        Path dirPath = Paths.get(System.getProperty("user.home"), "Desktop", "data");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
            for (Path filePath : stream) {
                String fileName = filePath.getFileName().toString();

                //reading file
                String fileContent = new String(Files.readAllBytes(filePath));

                // Regular Expression: trim words, white space, case sensitivity gone by making all words lower case.
                String[] words = fileContent.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

                // count the words
                for (String word : words) {
                    if (word.length() == 6) {
                        wordsSixMap.put(word, wordsSixMap.getOrDefault(word, 0) + 1);
                    } else if (word.length() == 7) {
                        wordsSevenMap.put(word, wordsSevenMap.getOrDefault(word, 0) + 1);
                    } else if (word.length() == 8) {
                        wordsEightMap.put(word, wordsEightMap.getOrDefault(word, 0) + 1);
                    }
                }

                String mostFrequent6 = findMostFrequentWord(wordsSixMap);
                String mostFrequent7 = findMostFrequentWord(wordsSevenMap);
                String mostFrequent8 = findMostFrequentWord(wordsEightMap);

                // print
                System.out.println("File Name: " + fileName);
                System.out.println("============================");
                System.out.println("6 Letter word: " + mostFrequent6);
                System.out.println("7 Letter word: " + mostFrequent7);
                System.out.println("8 Letter word: " + mostFrequent8);
                System.out.println();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        double totalTime = (endTime - startTime) / 1000.0;
        System.out.println("Total time taken: " + totalTime + " seconds");
    }

    private static String findMostFrequentWord(HashMap<String, Integer> wordFrequencyMap) {
        // Find the most frequent word in the HashMap
        return wordFrequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }
}

